# Tornado API - RESTful Clojure Applications

A sample Clojure application built for the [Art of
Monitoring](https://artofmonitoring.com) and
[Monitoring With Prometheus](https://prometheusbook.com).

## Applications

The StatsD enabled app for The Art of Monitoring is on the `master` branch and available as [a Docker image](https://hub.docker.com/r/jamtur01/tornado-api/).

The Prometheus client enabled app for Monitoring With Prometheus is on the `prometheus` branch and available as [a Docker image](https://hub.docker.com/r/jamtur01/tornado-api-prometheus/).

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2016 James Turnbull

Original Copyright &copy; 2012 Michael Jakl
