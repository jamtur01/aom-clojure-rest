(defproject tornado-api-prometheus "0.1.0-SNAPSHOT"
      :description "Example Clojure REST service for AoM and MwP"
      :url "http://artofmonitoring.com"
      :dependencies [[org.clojure/clojure "1.8.0"]
                     [compojure "1.1.1"]
                     [ring/ring-json "0.1.2"]
                     [ring/ring-jetty-adapter "1.3.1"]
                     [ring-logger-timbre "0.7.5"]
                     [com.taoensso/timbre "4.2.1"]
                     [c3p0/c3p0 "0.9.1.2"]
                     [org.clojure/java.jdbc "0.4.2"]
                     [mysql/mysql-connector-java "5.1.38"]
                     [com.taoensso/carmine "2.12.2"]
                     [cheshire "4.0.3"]
                     [iapetos "0.1.8"]
                     [io.prometheus/simpleclient_hotspot "0.4.0"]]
      :plugins [[lein-ring "0.7.3"]]
      :main tornado-api.handler
      :ring {:handler tornado-api.handler/app}
      :profiles {
                 :dev {:dependencies [[ring-mock "0.1.3"]]}
                 :uberjar {:aot :all}})
