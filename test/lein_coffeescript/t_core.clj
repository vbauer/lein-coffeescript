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

(def ^:private DEF_ROOT "example/")
(def ^:private DEF_OUTPUT "test-out/")

(defn- res [f] (str DEF_ROOT f))
(defn- out [f] (str DEF_OUTPUT f))

(def ^:private DEF_CONFIG
  {:coffeescript
   {:sources (res "resources/*.coffee")
    :output DEF_OUTPUT
    :map true
    :bare false
    :debug true}})


; Helper functions

(defn- file-exists? [& parts]
  (let [path (apply str parts)]
    (and
     (fs/exists? path)
     (fs/file? path)
     (> (fs/size path) 0))))

(defn- clear-output[]
  (fs/delete-dir DEF_OUTPUT))

(defn- check-process [cfg files]
  (try
    (do
      (clear-output)
      (coffeescript cfg)
      (every? #(file-exists? (out %)) files))
    (finally (clear-output))))


; Tests

(fact "Check CoffeeScript processor"
  (check-process DEF_CONFIG ["test.js" "test.map"]) => true)
