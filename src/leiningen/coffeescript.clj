(ns ^{:author "Vladislav Bauer"}
  leiningen.coffeescript
  (:require [leiningen.help :as help]
            [lein-coffeescript.core :as core]))


; External API: Task

(defn coffeescript
  "Invoke the CoffeeScript compiler"
  [project & args]
  (if (= args ["help"])
    (println (help/help-for core/DEF_COFFEE_SCRIPT_CMD))
    (core/coffeescript project args)))
