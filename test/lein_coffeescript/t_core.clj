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
(def ^:private DEF_GENERATED ["test.js" ["test.map" "test.js.map"]])
(def ^:private DEF_BUNDLE_IN "bundle.coffee")
(def ^:private DEF_BUNDLE_OUT ["bundle.js" ["bundle.map" "bundle.js.map"]])

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
  (let [f (out path)]
    (and
     (fs/exists? f)
     (fs/file? f)
     (> (fs/size f) 0))))

(defn- files-exist? [files]
  (every?
   #(if (sequential? %)
      (some file-exists? %)
      (file-exists? %))
   files))

(defn- clear-output[]
  (fs/delete-dir DEF_OUTPUT))

(defn- check-process [cfg files]
  (try
    (do
      (clear-output)
      (coffeescript cfg)
      (files-exist? files))
    (catch Exception _ false)
    (finally (clear-output))))


; Tests

(fact "Check CoffeeScript processor"
  (check-process (cs nil) DEF_GENERATED) => false
  (check-process (cs []) DEF_GENERATED) => false
  (check-process (cs [{}]) DEF_GENERATED) => false
  (check-process (cs (conf)) DEF_GENERATED) => true
  (check-process (cs [(conf)]) DEF_GENERATED) => true
  (check-process (cs [(conf) (conf)]) DEF_GENERATED) => true
  (check-process (cs [(assoc (conf) :join DEF_BUNDLE_IN)]) DEF_BUNDLE_OUT) => true)
