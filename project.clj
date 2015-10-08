(defproject lein-coffeescript "0.1.10-SNAPSHOT"
  :description "A Leiningen plugin for running CoffeeScript compiler"
  :url "https://github.com/vbauer/lein-coffeescript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[lein-npm "0.6.1" :exclusions [org.clojure/clojure]]
                 [me.raynes/fs "1.4.6" :exclusions [org.clojure/clojure]]]

  :profiles {

    :dev {:dependencies [[midje "1.6.3" :exclusions [org.clojure/clojure joda-time]]
                         [joda-time "2.2"]]
          ; Don't use the latest version: https://github.com/marick/lein-midje/issues/47
          :plugins [[lein-midje "3.1.1"]]}

    :prod {:plugins [[lein-release "1.0.6" :exclusions [org.clojure/clojure]]]
           :global-vars {*warn-on-reflection* true}
           :scm {:name "git"
                 :url "https://github.com/vbauer/lein-coffeescript"}
           :lein-release {:deploy-via :clojars
                          :scm :git}}
  }

  :pedantic? :abort
  :eval-in-leiningen true
  :local-repo-classpath true)
