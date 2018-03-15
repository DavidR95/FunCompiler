[![Build Status](https://travis-ci.org/DavidR95/FunCompiler.svg?branch=master)](https://travis-ci.org/DavidR95/FunCompiler)

# FunCompiler
### Abstract
The goal of this project is to create an application that provides concrete, visual representations of the abstract, conceptual notions characterised by compilation theory. The application, named the FunCompiler, is to be distributed as an additional educational resource during the delivery of the University of Glasgow's computer science course, Programming Languages. The FunCompiler enables its users to animate the compilation of any program written in the Fun language, including building visualisations of the key data structures involved and augmenting each step of the animation with additional, explanatory details. We also consider the claims that have been made in regards to the effectiveness of animations used to teach computational algorithms and investigate whether the FunCompiler can be used to support or discredit these theories.

## Getting started

To get started with the app, navigate to the top-level directory (containing docker-compose.yml) and simply run:

```
$ docker-compose -f docker-compose.production.yml up
```

You should now be able to find the web app at [localhost:8000](http://localhost:8000).
