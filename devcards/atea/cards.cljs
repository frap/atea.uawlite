(ns ^:figwheel-hooks atea.cards
  (:require [devcards.core]
            [atea.cards.my-first-devcard]))

(enable-console-print!)

(defn render []
  (devcards.core/start-devcard-ui!))

(defn ^:after-load render-on-relaod []
  (render))

(render)
