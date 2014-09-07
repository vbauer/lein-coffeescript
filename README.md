lein-coffeescript
=================

**[CoffeeScript](http://coffeescript.org) is a little language that compiles into JavaScript.** Underneath that awkward Java-esque patina, JavaScript has always had a gorgeous heart. CoffeeScript is an attempt to expose the good parts of JavaScript in a simple way.

The golden rule of CoffeeScript is: *"It's just JavaScript"*. The code compiles one-to-one into the equivalent JS, and there is no interpretation at runtime. You can use any existing JavaScript library seamlessly from CoffeeScript (and vice-versa). The compiled output is readable and pretty-printed, will work in every JavaScript runtime, and tends to run as fast or faster than the equivalent handwritten JavaScript.

[lein-coffeescript](https://github.com/vbauer/lein-coffeescript) is a Leiningen plugin that allows to use CoffeeScript compiler.

[![Build Status](https://travis-ci.org/vbauer/lein-coffeescript.svg?branch=master)](https://travis-ci.org/vbauer/lein-coffeescript)
[![Dependencies Status](http://jarkeeper.com/vbauer/lein-coffeescript/status.png)](http://jarkeeper.com/vbauer/lein-coffeescript)


Pre-requirements
================

Install [NodeJS](http://nodejs.org/) and [NPM](https://github.com/npm/npm) (package manager for Node) to install CoffeeScript:

On Ubuntu:
```
sudo apt-get install nodejs
```
On Mac OS X:
```
brew install node
```


Installation
============

Install [CoffeeScript](https://www.npmjs.org/package/coffee-script) to use lein-coffeescript plugin. It could be done in few ways:

- Use NPM to install CoffeeScript globally:
```
npm install lein-coffeescript -g
```
- You can also install CoffeeScript in the current directory:
```
npm install lein-coffeescript
```
- Use [lein-npm](https://github.com/bodil/lein-npm) plugin:
```
lein npm install
```
- Use just Leiningen:
```
lein deps
```

Setup
-----

To enable lein-coffeescript for your project, put the following in the :plugins vector of your project.clj file:

![latest-version](https://clojars.org/lein-coffeescript/latest-version.svg)


Configuration
=============

To configure lein-coffeescript, put the :coffeescript parameter in the file project.clj. It could be a single configuration (simple map) or a collection of configurations (for multiple configuration).

```clojure
; single configuration
:coffeescript {:includes "src/*.cs"}

; multiple configurations
:coffeescript [{:includes "src/*.cs"
                :bare: false
                :map true}
               {:includes ["src/*.coffee" "resources/*.cs"]}
                :bare: true}]
```


Configuration parameters
------------------------

*TODO*


Hooks
-----

To enable this plugin in the compile stage, use the following hook:
```clojure
:hooks [lein-coffeescript.plugin]
```

Examples
========

Detailed example
----------------

```clojure
:coffeescript {:includes "resources/*.coffee"
               :excludes ["resources/tests.coffee" "resources/bad.coffee"]
               :map true
               :join "app.js"
               :output "target/js"
               :bare false
               :debug true}
```


Example project
---------------

Just clone the current repository and try to play with [example project](https://github.com/vbauer/lein-coffeescript/tree/master/example) for better understanding how to use lein-coffeescript.


Might also like
===============

* [lein-jslint](https://github.com/vbauer/lein-jslint) - a Leiningen plugin for running javascript code through JSLint.
* [lein-jshint](https://github.com/vbauer/lein-jshint) - a Leiningen plugin for running javascript code through JSHint.
* [lein-plantuml](https://github.com/vbauer/lein-plantuml) - a Leiningen plugin for generating UML diagrams using PlantUML.
* [lein-asciidoctor](https://github.com/asciidoctor/asciidoctor-lein-plugin) - A Leiningen plugin for generating documentation using Asciidoctor.


License
=======

Copyright © 2014 Vladislav Bauer

Distributed under the Eclipse Public License, the same as Clojure.
