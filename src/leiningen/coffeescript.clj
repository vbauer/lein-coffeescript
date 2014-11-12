(ns ^{:author "Vladislav Bauer"}
  leiningen.coffeescript
  (:require [leiningen.help :as help]
            [lein-coffeescript.core :as core]))


; External API: Task

(defn coffeescript
  "Invoke the CoffeeScript compiler.

  Configure :coffeescript configuration parameter in the file project.clj using following options:
    :sources     - List of glob patterns to define input sources.
    :excludes    - List of glob patterns to prevent processing of some files.
    :map         - Generate source maps alongside the compiled JavaScript files.
    :bare        - Compile the JavaScript without the top-level function safety wrapper.
    :join        - Before compiling, concatenate all scripts together in the order they were passed.
    :output      - Write out all compiled JavaScript files into the specified directory.

  Usage:
    lein coffeescript"

  [project & args]
  (if (= args ["help"])
    (println (help/help-for core/DEF_COFFEE_SCRIPT_CMD))
    (core/coffeescript project args)))
