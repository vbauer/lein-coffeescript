(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.t-core
  (:use [midje.sweet :only [fact]]
        [midje.util :only [testable-privates]])
  (:require [lein-coffeescript.core]
            [me.raynes.fs :as fs]))


; Configurations

(testable-privates
  lein-coffeescript.core
    coffeescript
    file-path)

(def ^:private DEF_ROOT "example")
(def ^:private DEF_OUTPUT "test-out")
(def ^:private DEF_GENERATED ["test.js" "test.js.map"])

(defn- res [f] (file-path DEF_ROOT f))
(defn- out [f] (file-path DEF_OUTPUT f))

(defn- cs [cfg] {:coffeescript cfg})
(defn- conf []
  {:sources (res (file-path "resources" "*.coffee"))
   :output DEF_OUTPUT
   :map true
   :bare false
   :debug true})


; Helper functions

(defn- file-exists? [path]
  (and
   (fs/exists? path)
   (fs/file? path)
   (> (fs/size path) 0)))

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
  (check-process (cs (conf)) DEF_GENERATED) => true
  (check-process (cs [(conf)]) DEF_GENERATED) => true
  (check-process (cs [(conf) (conf)]) DEF_GENERATED) => true)
