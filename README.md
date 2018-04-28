[![Build Status](https://travis-ci.org/vlachenal/webservices-reactive-bench.svg?branch=master)](https://travis-ci.org/vlachenal/webservices-reactive-bench)

# webservices-reactive-bench

 Project to test Spring Webflux

## Synopsis

This is the reactive implementation of [webservices-bench project](https://github.com/vlachenal/webservices-bench) RESTful API.

I did not integrate reactive implementation in the main project because reactive implementation changes the service architecture ... and it does not work together (new endpoints had not been taken into accounts).

## Conclusions

### Advantages

Waiting for test result but it is certainly (by design) more performant than blocking implementation.

### Disadvantages

Reactive programming to be taken into account in the service architecture. Changing from blocking implementation and reactive could be painfull.

spring-jdbc does not have reactive part: I implements class which extends JdbcTemplate and which manage flux but I am not sure it is really reactive (depends on JDBC implementations).

For now, WebClient can not be used if project is not a reactive web server.
