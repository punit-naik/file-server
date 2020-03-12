(ns file-server.server.handler
  (:require [compojure.core :refer [routes]]
            [file-server.server.routes :refer [generate-routes-for-dir]]
            [ring.middleware.file :refer [wrap-file]]))

(defn dir-routes
  [root-dir]
  (apply routes (generate-routes-for-dir root-dir)))

(defn app
  "Handler for the server, accepts just one argument `serving-dir`, which is the directory path to be served"
  [serving-dir]
  (-> (dir-routes serving-dir)
      (wrap-file serving-dir)))