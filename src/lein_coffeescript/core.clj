(ns ^{:author "Vladislav Bauer"}
  lein-coffeescript.core
  (:import [java.io File])
  (:require [leiningen.npm :as npm]
            [leiningen.npm.process :as process]
            [leiningen.core.main :as main]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]
            [clojure.string :as string]))


; Internal API: Common

(defn- error [ex]
  (println
   (string/join
    "\r\n"
    [(str "An error has occurred: " (.getMessage ex))
     "Something is wrong:"
     " - installation: npm install coffee-script -g"
     " - configuration: https://github.com/vbauer/lein-coffeescript"])))

(defn- clean-path [p]
  (if (not (nil? p))
    (if (.startsWith (System/getProperty "os.name") "Windows")
      (string/replace p "/" "\\")
      (string/replace p "\\" "/"))))

(defn- join-files [files output]
  (let [js (reduce str (map slurp files))]
    (spit output js)
    output))

(defn- to-coll [e] (if (nil? e) [] (if (sequential? e) e [e])))
(defn- scan-files [patterns] (set (mapcat fs/glob (map clean-path patterns))))
(defn- abs-path [f] (.getAbsolutePath f))


; Internal API: Configuration

(def ^:public DEF_COFFEE_SCRIPT_CMD "coffee")
(def ^:private DEF_COFFEE_SCRIPT_DIR
  (io/file "node_modules" "coffee-script" "bin"))


(defn- configs [project] (to-coll (get project :coffeescript)))
(defn- config-files [conf k]
  (scan-files (to-coll (get conf k))))

(defn- conf-sources [conf] (config-files conf :sources))
(defn- conf-excludes [conf] (config-files conf :excludes))
(defn- conf-map [conf] (get conf :map false))
(defn- conf-bare [conf] (get conf :bare false))
(defn- conf-join [conf] (get conf :join))
(defn- conf-output [conf] (get conf :output))
(defn- conf-debug [conf] (get conf :debug false))
(defn- conf-watch [conf] (get conf :watch false))


; Internal API: Runner configuration

(defn- source-list [conf]
  (let [src (conf-sources conf)
        ex (conf-excludes conf)
        join (conf-join conf)
        sources (remove (fn [s] (some #(.compareTo % s) ex)) src)]
    (if (empty? sources)
      (throw (RuntimeException.
              "Source list is empty. Check parameters :sources & :excludes"))
      (map abs-path
           (if join
             (do
               (let [output (conf-output conf)]
                 (if output (fs/mkdirs output))
                 [(join-files sources (fs/file output join))]))
             sources)))))

(defn- param-map [conf] (if (conf-map conf) ["--map"]))
(defn- param-bare [conf] (if (conf-bare conf) ["--bare"]))
(defn- param-output [conf] (if-let [dir (conf-output conf)] ["--output" dir]))
(defn- param-compile [conf] (concat ["--compile"] (source-list conf)))
(defn- param-watch [conf] (if (conf-watch conf) ["--watch"]))


; Internal API: Runner

(defn- coffeescript-cmd []
  (let [local (io/file DEF_COFFEE_SCRIPT_DIR DEF_COFFEE_SCRIPT_CMD)]
    (if (.exists (fs/file local)) local DEF_COFFEE_SCRIPT_CMD)))

(defn- coffeescript-params [conf]
  (concat
   (param-map conf)
   (param-bare conf)
   (param-watch conf)
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
    (do
      (npm/environmental-consistency project)
      (compile-coffeescript project conf))
    (catch Throwable t
      (if (conf-debug conf)
        (.printStackTrace t))
      (error t)
      (main/abort))))


; External API: Runner

(defn coffeescript [project & args]
  (let [confs (configs project)]
    (doseq [conf confs]
      (process-config project conf))))
