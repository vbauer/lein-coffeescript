(defproject example "0.1.0-SNAPSHOT"
  :description "Simple example of using lein-coffeescript"
  :url "https://github.com/vbauer/lein-coffeescript"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}


  ; List of plugins
  :plugins [[lein-coffeescript "0.1.9-SNAPSHOT"]]

  ; List of hooks
  ; It's used for running lein-coffeescript during compile phase
  :hooks [lein-coffeescript.plugin]

  ; lein-coffeescript configuration
  :coffeescript {:sources "resources/*.coffee"
                 :excludes ["123"]
                 :map true
                 ;:join "app.js"
                 :output "target/js"
                 :bare false
                 :debug false
                 :watch true
                 })
