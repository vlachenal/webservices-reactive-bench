[![Build Status](https://travis-ci.org/vlachenal/webservices-reactive-bench.svg?branch=master)](https://travis-ci.org/vlachenal/webservices-reactive-bench) [![Maintainability](https://api.codeclimate.com/v1/badges/c03d97f0e050d5598ad2/maintainability)](https://codeclimate.com/github/vlachenal/webservices-reactive-bench/maintainability)

# webservices-reactive-bench

 Project to test Spring Webflux

## Synopsis

This is the reactive implementation of [webservices-bench project](https://github.com/vlachenal/webservices-bench) RESTful API.

I did not integrate reactive implementation in the main project because reactive implementation changes the service architecture ... and it does not work together (new endpoints had not been taken into accounts).

You can find the client part in [webservices-bench project](https://github.com/vlachenal/webservices-bench-client/tree/reactive) on "reactive" branch.

## Conclusions

### Advantages

Waiting for test result but it is certainly (by design) more performant than blocking implementation.

### Disadvantages

Reactive programming to be taken into account in the service architecture. Changing from blocking implementation to reactive could be painfull.

spring-jdbc does not have reactive part: I implements class which extends JdbcTemplate and which manage flux but I am not sure it is really reactive (depends on JDBC implementations).

~~For now, WebClient can not be used if project is not a reactive web server.~~
