(ns ^:figwheel-hooks atea.uccx
  (:require
   [goog.dom :as gdom]
   [com.stuartsierra.component :as component]
;;   [atea.uaw :refer [new-system]]
   [reagent.core :as reagent :refer [atom]]))

(println "This text is printed from src/atea/uccx.cljs. Go ahead and edit it and see reloading in action.")

(defn multiply [a b] (/ a b))


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hola Pelotudos!"}))

(defn get-app-element []
  (gdom/getElement "app"))

(defn hello-world []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this in src/atea/uccx.cljs and watch it change!"]])

(defn mount [el]
  (reagent/render-component [hello-world] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(defn new-system []
   {:dev 'start'})

(defn start []
  (let [system (new-system :dev)]
    (component/start system)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
