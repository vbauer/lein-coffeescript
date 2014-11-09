(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.t-core
  (:use [midje.sweet :only [fact]]
        [midje.util :only [testable-privates]])
  (:require [lein-coffeescript.core]
            [me.raynes.fs :as fs]))


; Configurations

(testable-privates
  lein-coffeescript.core
    coffeescript)

(def ^:private DEF_OUTPUT "example/test-out")
(def ^:private DEF_CONFIG
  {:coffeescript
   {:sources "example/resources/*.coffee"
    :output DEF_OUTPUT
    :map true
    :bare false
    :debug true}})


; Helper functions

(defn- clear-output[]
  (fs/delete-dir DEF_OUTPUT))

(defn- check-process [cfg]
  (try
    (do
      (clear-output)
      (coffeescript cfg))
    (finally (clear-output))))

; Tests

(fact "Check CoffeeScript processor"
  (nil? (check-process DEF_CONFIG)) => true)
