(ns atea.core
  (:require [integrant.core :as ig]
            [atea.module.app]
            [atea.module.router]
            [atea.module.moment])
  (:require-macros [atea.utils :refer [read-config]]))

(defonce system (atom nil))

(def config (atom (read-config "config-app.edn")))

(defn start []
  (reset! system (ig/init @config)))

(defn stop []
  (when @system
    (ig/halt! @system)
    (reset! system nil)))

(defn ^:export init []
  (start))
