(defproject lein-coffeescript "0.1.1-SNAPSHOT"
  :description "A Leiningen plugin for running CoffeeScript compiler"
  :url "https://github.com/vbauer/lein-coffeescript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-glob "1.0.0" :exclusions [org.clojure/clojure]]
                 [lein-npm "0.4.0" :exclusions [org.clojure/clojure]]]

  :plugins [[jonase/eastwood "0.1.4" :exclusions [org.clojure/clojure]]
            [lein-release "1.0.5" :exclusions [org.clojure/clojure]]
            [lein-kibit "0.0.8" :exclusions [org.clojure/clojure]]
            [lein-bikeshed "0.1.7" :exclusions [org.clojure/clojure]]
            [lein-ancient "0.5.5"]]

  :eval-in-leiningen true
  :pedantic? :abort

  :local-repo-classpath true
  :lein-release {:deploy-via :clojars
                 :scm :git})
