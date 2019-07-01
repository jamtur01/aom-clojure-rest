(defproject tornado-api-jaeger "0.1.0-SNAPSHOT"
      :description "Example Clojure REST service for AoM and MwP"
      :url "https://artofmonitoring.com"
      :dependencies [[org.clojure/clojure "1.9.0"]
                     [compojure "1.6.1"]
                     [ring/ring-json "0.1.2"]
                     [ring/ring-jetty-adapter "1.3.1"]
                     [ring-logger-timbre "0.7.5"]
                     [com.taoensso/timbre "4.10.0"]
                     [c3p0/c3p0 "0.9.1.2"]
                     [org.clojure/java.jdbc "0.7.8"]
                     [mysql/mysql-connector-java "8.0.13"]
                     [com.taoensso/carmine "2.19.1"]
                     [cheshire "5.8.1"]
                     [uswitch/opencensus-clojure "0.2.84"]]
      :plugins [[lein-ring "0.7.3"]]
      :main tornado-api.handler
      :ring {:handler tornado-api.handler/app}
      :profiles {
                 :dev {:dependencies [[ring-mock "0.1.3"]]}
                 :uberjar {:aot [tornado-api.handler]}})
