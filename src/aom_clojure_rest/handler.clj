(ns aom-clojure-rest.handler
      (:import com.mchange.v2.c3p0.ComboPooledDataSource)
      (:use compojure.core)
      (:use cheshire.core)
      (:use ring.util.response)
      (:require [compojure.handler :as handler]
                [ring.middleware.json :as middleware]
                [clojure.java.jdbc :as sql]
                [compojure.route :as route]))

(def db-config
      {:classname "org.h2.Driver"
       :subprotocol "h2"
       :subname "mem:items"
       :user ""
       :password ""})

(defn pool
      [config]
      (let [cpds (doto (ComboPooledDataSource.)
                   (.setDriverClass (:classname config))
                   (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
                   (.setUser (:user config))
                   (.setPassword (:password config))
                   (.setMaxPoolSize 6)
                   (.setMinPoolSize 1)
                   (.setInitialPoolSize 1))]
        {:datasource cpds}))

    (def pooled-db (delay (pool db-config)))

    (defn db-connection [] @pooled-db)

(sql/with-connection (db-connection)
      (sql/create-table :items [:id "varchar(256)" "primary key"]
                               [:title "varchar(1024)"]
                               [:text :varchar]))

    (defn uuid [] (str (java.util.UUID/randomUUID)))

    (defn get-all-items []
      (response
        (sql/with-connection (db-connection)
          (sql/with-query-results results
            ["select * from items"]
            (into [] results)))))

    (defn get-item [id]
      (sql/with-connection (db-connection)
        (sql/with-query-results results
          ["select * from items where id = ?" id]
          (cond
            (empty? results) {:status 404}
            :else (response (first results))))))

    (defn create-new-item [item]
      (let [id (uuid)]
        (sql/with-connection (db-connection)
          (let [item (assoc item "id" id)]
            (sql/insert-record :items item)))
        (get-item id)))

    (defn update-item [id item]
        (sql/with-connection (db-connection)
          (let [item (assoc item "id" id)]
            (sql/update-values :items ["id=?" id] item)))
        (get-item id))

    (defn delete-item [id]
      (sql/with-connection (db-connection)
        (sql/delete-rows :items ["id=?" id]))
      {:status 204})

(defroutes app-routes
      (context "/api" [] (defroutes api-routes
        (GET  "/" [] (get-all-items))
        (POST "/" {body :body} (create-new-item body))
        (context "/:id" [id] (defroutes api-routes
          (GET    "/" [] (get-item id))
          (PUT    "/" {body :body} (update-item id body))
          (DELETE "/" [] (delete-item id))))))
      (route/not-found "Not Found"))

(def app
      (-> (handler/api app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))
