(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.core
  (:require [leiningen.npm :as npm]
            [leiningen.npm.process :as process]
            [leiningen.core.main :as main]
            [org.satta.glob :as glob]
            [clojure.java.io :as io]
            [clojure.string :as string]))


; Internal API: Common

(defn- error [ex]
  (println
   (string/join "\r\n"
                (str "An error has occurred: " (.getMessage ex))
                "Something is wrong:"
                " - installation: npm install coffee-script -g"
                " - configuration: https://github.com/vbauer/lein-coffeescript")))

(defn- to-coll [e]
  (if (nil? e) [] (if (sequential? e) e [e])))

(defn- scan-files [patterns]
  (set (mapcat glob/glob patterns)))


; Internal API: Configuration

(def ^:public DEF_COFFEE_SCRIPT_CMD "coffee")
(def ^:private DEF_COFFEE_SCRIPT_DIR "node_modules/coffee-script/bin/")


(defn- configs [project] (to-coll (get project :coffeescript)))
(defn- config-files [conf k] (scan-files (to-coll (get conf k))))

(defn- conf-include-files [conf] (config-files conf :includes))
(defn- conf-exclude-files [conf] (config-files conf :excludes))
(defn- conf-map [conf] (get conf :map false))
(defn- conf-bare [conf] (get conf :bare false))
(defn- conf-join [conf] (get conf :join))
(defn- conf-output [conf] (get conf :output))
(defn- conf-debug [conf] (get conf :debug))


; Internal API: Runner configuration

(defn- source-list [conf]
  (let [includes (conf-include-files conf)
        excludes (conf-exclude-files conf)
        sources (remove (fn [s] (some #(.compareTo % s) excludes)) includes)]
    (map #(.getAbsolutePath %) sources)))

(defn- param-map [conf] (if (conf-map conf) ["--map"]))
(defn- param-bare [conf] (if (conf-bare conf) ["--bare"]))
(defn- param-join [conf] (if-let [file (conf-join conf)] ["--join" file]))
(defn- param-output [conf] (if-let [dir (conf-output conf)] ["--output" dir]))
(defn- param-compile [conf] (concat ["--compile"] (source-list conf)))

; Internal API: Runner

(defn- coffeescript-cmd []
  (let [local (str DEF_COFFEE_SCRIPT_DIR DEF_COFFEE_SCRIPT_CMD)]
    (if (.exists (io/file local)) local DEF_COFFEE_SCRIPT_CMD)))

(defn- coffeescript-params [conf]
  (concat
   (param-map conf)
   (param-bare conf)
   (param-join conf)
   (param-output conf)
   (param-compile conf)))

(defn- compile-coffeescript [project conf]
  (let [root (:root project)
        cmd (coffeescript-cmd)
        params (coffeescript-params conf)
        args (concat [cmd] params)]
    (if (conf-debug conf)
      (println (string/join " " args)))
    (process/exec root args)))

(defn- process-config [project conf]
  (try
    (npm/environmental-consistency project)
    (compile-coffeescript project conf)
    (catch Throwable t
      (if (conf-debug conf)
        (.printStackTrace t)
        (error t))
      (main/abort))))


; External API: Runner

(defn coffeescript [project & args]
  (let [confs (configs project)]
    (doseq [conf confs]
      (process-config project conf))))
