(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.plugin
  (:require [leiningen.compile]
            [lein-npm.plugin :as npm]
            [robert.hooke :as hooke]
            [lein-coffeescript.core :as core]))


; Internal API: Configuration

(def ^:private DEF_COFFEESCRIPT_DEP "coffee-script")
(def ^:private DEF_COFFEESCRIPT_VER ">=1.9.3")


; Internal API: Middlewares

(defn- coffeescript? [dep]
  (= (str (first dep)) DEF_COFFEESCRIPT_DEP))

(defn- find-coffeescript-deps [deps]
  (keep-indexed #(when (coffeescript? %2) %1) deps))

(defn- ensure-coffeescript [deps version]
  (let [coffeescript-matches (find-coffeescript-deps deps)
        new-dep [DEF_COFFEESCRIPT_DEP (or version DEF_COFFEESCRIPT_VER)]]
    (if (empty? coffeescript-matches)
      (conj deps new-dep) deps)))


; External API: Middlewares

(defn middleware [project]
  (let [version (get-in project [:coffeescript :version])]
    (update-in project [:node-dependencies]
               #(vec (ensure-coffeescript % version)))))


; External API: Hooks

(defn compile-hook [task project & args]
  (let [res (apply task project args)]
    (core/coffeescript project args)
    res))

(defn activate []
  (npm/hooks)
  (hooke/add-hook #'leiningen.compile/compile #'compile-hook))
