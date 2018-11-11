(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.t-core
  (:require [lein-coffeescript.core :as cs]
            [me.raynes.fs :as fs]
            [clojure.test :as t]
            [clojure.java.io :as io]))


; Configurations

(def ^:private DEF_ROOT "example")
(def ^:private DEF_OUTPUT "test-out")
(def ^:private DEF_GENERATED ["test.js" ["test.map" "test.js.map"]])
(def ^:private DEF_BUNDLE_IN "bundle.coffee")
(def ^:private DEF_BUNDLE_OUT ["bundle.js" ["bundle.map" "bundle.js.map"]])

(defn- res [f] (io/file DEF_ROOT f))
(defn- out [f] (io/file DEF_OUTPUT f))

(defn- cs [cfg] {:coffeescript cfg})
(defn- conf []
  {:sources (res (io/file "resources" "*.coffee"))
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
      (cs/coffeescript cfg)
      (files-exist? files))
    (catch Exception _ false)
    (finally (clear-output))))


; Tests

(t/deftest testing
  (t/is (not (check-process (cs nil) DEF_GENERATED)))
  (t/is (not (check-process (cs []) DEF_GENERATED)))
  (t/is (not (check-process (cs [{}]) DEF_GENERATED)))
  (t/is (check-process (cs (conf)) DEF_GENERATED))
  (t/is (check-process (cs [(conf)]) DEF_GENERATED))
  (t/is (check-process (cs [(conf) (conf)]) DEF_GENERATED))
  (t/is (check-process (cs [(assoc (conf) :join DEF_BUNDLE_IN)]) DEF_BUNDLE_OUT)))
