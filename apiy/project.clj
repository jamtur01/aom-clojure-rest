(defproject tornado-apiy-jaeger "0.1.0-SNAPSHOT"
      :description "Example Clojure REST service for AoM and MwP"
      :url "https://artofmonitoring.com"
      :dependencies [[org.clojure/clojure "1.9.0"]
                     [compojure "1.6.1"]
                     [ring/ring-json "0.1.2"]
                     [ring/ring-jetty-adapter "1.3.1"]
                     [ring-logger-timbre "0.7.5"]
                     [com.taoensso/timbre "4.10.0"]
                     [c3p0/c3p0 "0.9.1.2"]
                     [org.clojure/java.jdbc "0.4.2"]
                     [mysql/mysql-connector-java "8.0.13"]
                     [com.taoensso/carmine "2.19.1"]
                     [cheshire "5.8.1"]
                     [uswitch/opencensus-clojure "0.2.84"]
                      [com.fzakaria/slf4j-timbre "0.3.13"]
                     [org.slf4j/log4j-over-slf4j "1.7.14"]
                     [org.slf4j/jul-to-slf4j "1.7.14"]
                     [org.slf4j/jcl-over-slf4j "1.7.14"]
                     [io.opencensus/opencensus-exporter-trace-logging "0.19.2"]
                     [io.opencensus/opencensus-exporter-trace-jaeger "0.19.2"]]
      :plugins [[lein-ring "0.7.3"]]
      :main tornado-apiy.handler
      :ring {:handler tornado-apiy.handler/app}
      :profiles {
                 :dev {:dependencies [[ring-mock "0.1.3"]]}
                 :uberjar {:aot [tornado-apiy.handler]}})
