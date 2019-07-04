(ns tornado-apiy.handler
      (:gen-class)
      (:import com.mchange.v2.c3p0.ComboPooledDataSource)
      (:use compojure.core)
      (:use cheshire.core)
      (:use ring.util.response)
      (:require [compojure.handler :as handler]
                [opencensus-clojure.ring.middleware :refer [wrap-tracing]]
                [opencensus-clojure.reporting.jaeger] 
                [taoensso.carmine :as car :refer (wcar)]
                [clojure.string :as str]
                [ring.middleware.json :as middleware]
                [clojure.java.jdbc :as sql]
                [compojure.route :as route]
                [taoensso.timbre :as timbre]
                [taoensso.timbre.appenders.core :as appenders]
                [ring.adapter.jetty :refer :all]
                [ring.logger.timbre :as logger.timbre]))

(timbre/merge-config!
  {:level :info
   :appenders {:spit (appenders/spit-appender {:fname "/var/log/tornado-apiy.log"})}})

(def server1-conn {:pool {} :spec {:host "tornado-redis" :port 6379 :password "tornadoapi" }})

(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(let [db-host "tornado-db"
      db-port 3306
      db-name "items"]

  (def db-config {:classname "com.mysql.jdbc.Driver"
                  :subprotocol "mysql"
                  :subname (str "//" db-host ":" db-port "/" db-name)
                  :user "tornado"
                  :password "strongpassword"}))

(defn get-all-items []
  (response
    (sql/query db-config ["select * from items"])))

(defn get-item [id]
  (let [item (response (first (sql/query db-config ["select * from items where id = ?" id])))]
    (cond
      (empty? (item :body)) {:status 404}
      :else item)))

(defn add-newprice [body]
  (let [id (str (body "id"))
       item ((get-item id) :body)
       newprice (* (item :price) 0.8)]
       (wcar* (car/ping)
          (car/set id newprice))
       (get-item id)))

(defroutes app-routes
      (context "/api" [] (defroutes api-routes
        (GET  "/" [] (get-all-items))
        (POST "/" {body :body} (add-newprice body))))
      (route/not-found "Not Found"))

(def app
      (-> (handler/api app-routes)
          (middleware/wrap-json-body)
          (middleware/wrap-json-response)
          (wrap-tracing (fn [req] (-> req :uri (str/replace #"/" "🦄"))))))

(defn -main []
    (opencensus-clojure.reporting.jaeger/report "http://jaeger:14268/api/traces" "tornado-api")
    (run-jetty (logger.timbre/wrap-with-logger app {:printer :no-color}) {:port (Integer/valueOf (or (System/getenv "port") "80"))}))
