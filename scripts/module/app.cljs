(ns atea.module.app
  (:require [integrant.core :as ig]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [atea.module.router :as router]
            [atea.views :as views]))

;; Initial DB
(def initial-db {::title nil})

;; Subscriptions
(defmulti reg-sub identity)
(defmethod reg-sub ::title [k]
  (re-frame/reg-sub
   k #(case (::router/active-panel %)
        :home-panel "Home"
        :about-panel "About"
        "")))

;; Events
(defmulti reg-event identity)
(defmethod reg-event ::init [k]
  (re-frame/reg-event-db
   k [re-frame/trim-v]
   (fn-traced
    [db _]
    (merge db initial-db))))
(defmethod reg-event ::halt [k]
  (re-frame/reg-event-db
   k [re-frame/trim-v]
   (fn-traced
    [db _]
    (->> db
         (filter #(not= (namespace (key %)) (namespace ::x)))
         (into {})))))
(defmethod reg-event ::set-title [k]
  (re-frame/reg-event-db
   k [re-frame/trim-v]
   (fn-traced
    [db [title]]
    (assoc db ::title title))))

;; Init
(defmethod ig/init-key :atea.module/app
  [k {:keys [:mount-point-id]}]
  (js/console.log (str "Initialising " k))
  (let [subs (->> reg-sub methods (map key))
        events (->> reg-event methods (map key))
        container (.getElementById js/document mount-point-id)]
    (->> subs (map reg-sub) doall)
    (->> events (map reg-event) doall)
    (re-frame/dispatch-sync [::init])
    (when container (reagent/render [views/app-container] container))
    {:subs subs :events events :container container}))

;; Halt
(defmethod ig/halt-key! :atea.module/app
  [k {:keys [:subs :events :container]}]
  (js/console.log (str "Halting " k))
  (reagent/unmount-component-at-node container)
  (re-frame/dispatch-sync [::halt])
  (->> subs (map re-frame/clear-sub) doall)
  (->> events (map re-frame/clear-event) doall))
