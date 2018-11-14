lein-coffeescript
=================

**[CoffeeScript](http://coffeescript.org) is a little language that compiles into JavaScript.** Underneath that awkward Java-esque patina, JavaScript has always had a gorgeous heart. CoffeeScript is an attempt to expose the good parts of JavaScript in a simple way.

The golden rule of CoffeeScript is: *"It's just JavaScript"*. The code compiles one-to-one into the equivalent JS, and there is no interpretation at runtime. You can use any existing JavaScript library seamlessly from CoffeeScript (and vice-versa). The compiled output is readable and pretty-printed, will work in every JavaScript runtime, and tends to run as fast or faster than the equivalent handwritten JavaScript.

[lein-coffeescript](https://github.com/vbauer/lein-coffeescript) is a Leiningen plugin that allows to use CoffeeScript compiler.

[![Build Status](https://travis-ci.org/vbauer/lein-coffeescript.svg?branch=master)](https://travis-ci.org/vbauer/lein-coffeescript)
[![Clojars Project](https://img.shields.io/clojars/v/lein-coffeescript.svg)](https://clojars.org/lein-coffeescript)


Pre-requirements
================

Install [NodeJS](http://nodejs.org/) and [NPM](https://github.com/npm/npm) (package manager for Node) to install CoffeeScript:

* On Ubuntu: `sudo apt-get install nodejs`
* On Mac OS X: `brew install node`


Installation
============

Install [CoffeeScript](https://www.npmjs.org/package/coffee-script) to use lein-coffeescript plugin. It could be done in few ways:

* Use NPM to install CoffeeScript globally: `npm install coffee-script -g`
* You can also install CoffeeScript in the current directory: `npm install coffee-script`
* Use [lein-npm](https://github.com/bodil/lein-npm) plugin: `lein npm install`
* Use just Leiningen: `lein deps`

Setup
-----

To enable lein-coffeescript for your project, put the following in the :plugins vector of your project.clj file:

```clojure
; Use latest version instead of "X.X.X"
:plugins [[lein-coffeescript "X.X.X"]]
```


Configuration
=============

To configure lein-coffeescript, put the :coffeescript parameter in the file project.clj. It could be a single configuration (simple map) or a collection of configurations (for multiple configuration).

```clojure
; single configuration
:coffeescript {:sources "src/*.cs"}

; multiple configurations
:coffeescript [{:sources "src/*.cs"
                :bare false
                :map true}
               {:sources ["src/*.coffee" "resources/*.cs"]
                :bare true}]
```


Configuration parameters
------------------------

<dl>

  <dt>:sources</dt>
  <dd>List of input CoffeeScript sources. It is possible to use a single source or a vector of sources. To configure this parameter, you could also use a <a href="http://en.wikipedia.org/wiki/Glob_(programming)">Glob Patterns</a>.</dd>

  <dt>:excludes</dt>
  <dd>List of glob patterns to prevent processing of some files. It is also possible to use both variants: single pattern and collection of patterns.</dd>

  <dt>:map</dt>
  <dd>Generate source maps alongside the compiled JavaScript files. Adds <code>sourceMappingURL</code> directives to the JavaScript as well. Default value: false.</dd>

  <dt>:bare</dt>
  <dd>Compile the JavaScript without the top-level function safety wrapper. Default value: false</dd>

  <dt>:join</dt>
  <dd>Before compiling, concatenate all scripts together in the order they were passed, and write them into the specified file. Useful for building large projects. It is not defined by default. Example: `:join "bundle.coffee"`.</dd>

  <dt>:output</dt>
  <dd>Write out all compiled JavaScript files into the specified directory. It is not defined by default. Source directory will be used of generated JS files.</dd>

  <dt>:watch</dt>
  <dd>Watch files for changes, rerunning the specified command when any file is updated. Default value: false</dd>

</dl>


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
:coffeescript {:sources "resources/*.coffee"
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


Usage
=====

To compile CoffeeScript files using configuration from project.clj, you should use: `lein coffeescript`

To show help: `lein help coffeescript`


Unit testing
============

To run unit tests:

```bash
lein test
```


Thanks to
=========

CoffeeScript author [Jeremy Ashkenas](https://github.com/jashkenas) and other developers who worked on this great project.


Might also like
===============

* [lein-typescript](https://github.com/vbauer/lein-typescript) - a Leiningen plugin for running TypeScript compiler.
* [lein-jslint](https://github.com/vbauer/lein-jslint) - a Leiningen plugin for running javascript code through JSLint.
* [lein-jshint](https://github.com/vbauer/lein-jshint) - a Leiningen plugin for running javascript code through JSHint.
* [lein-plantuml](https://github.com/vbauer/lein-plantuml) - a Leiningen plugin for generating UML diagrams using PlantUML.
* [lein-asciidoctor](https://github.com/asciidoctor/asciidoctor-lein-plugin) - A Leiningen plugin for generating documentation using Asciidoctor.
* [jabberjay](https://github.com/vbauer/jabberjay) - a simple framework for creating Jabber bots.
* [coderwall-clj](https://github.com/vbauer/coderwall-clj) - a tiny CoderWall client for Clojure.


License
=======

Copyright © 2014 Vladislav Bauer

Distributed under the Eclipse Public License, the same as Clojure.
