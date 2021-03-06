;; Copyright © 2019, Red Elvis.
(ns atea.uaw
  (:require
   [aero.core :as aero]
   [com.stuartsierra.component :refer [system-map system-using]]
   [duct.component.hikaricp :refer [hikaricp]]
   [gas.web-server :refer [new-web-server]]
   [gas.db :refer  [new-hrdb new-walldb new-localdb]]
   )
  )


(defn config
  "Read EDN config, with the given profile. See Aero docs at
  https://github.com/juxt/aero for details."
  [profile]
  (aero/read-config (io/resource "config.edn") {:profile profile}))


(defn new-system-map
  "Create the system. See https://github.com/stuartsierra/component"
  [config]
  (system-map
                                        ;   :web-server (new-web-server (:web-server config))
   ;;  :selmer      (new-selmer (:selmer config))
   :db-hr      (hikaricp       (:uccxdb-hr config))
   :db-rt      (hikaricp       (:uccxdb-rt config))
   ;;  :uccx-stats  (new-localdb)
   ))

(defn new-dependency-map
  "Declare the dependency relationships between components. See
  https://github.com/stuartsierra/component"
  []
                                        ;{:web-server {:db :db-hr}}
  {}
  )

(defn new-system
  "Construct a new system, configured with the given profile"
  [profile]
  (let [config (config profile)]
    (-> (new-system-map config)
        (system-using (new-dependency-map)))))
