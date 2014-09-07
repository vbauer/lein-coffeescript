lein-coffeescript
=================

[lein-coffeescript](https://github.com/vbauer/lein-coffeescript) is a Leiningen plugin that allows to use CoffeeScript compiler.


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

To enable lein-coffeescript for your project, put the following in the :plugins vector of your project.clj file:

![latest-version](https://clojars.org/lein-coffeescript/latest-version.svg)

[![Build Status](https://travis-ci.org/vbauer/lein-coffeescript.svg?branch=master)](https://travis-ci.org/vbauer/lein-coffeescript)
[![Dependencies Status](http://jarkeeper.com/vbauer/lein-coffeescript/status.png)](http://jarkeeper.com/vbauer/lein-coffeescript)

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


Configuration
=============

To enable this plugin in compile stage, use the following hook:
```clojure
:hooks [lein-coffeescript.plugin]
```


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
