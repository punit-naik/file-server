(ns file-server.server.handler
  (:require [compojure.core :refer [routes]]
            [file-server.server.routes :refer [generate-routes-for-dir]]
            [ring.middleware.file :refer [wrap-file]]))

(defn dir-routes
  [root-dir]
  (apply routes (generate-routes-for-dir root-dir)))

(defn ignore-trailing-slash
  "Modifies the request uri before calling the handler.
  Removes a single trailing slash from the end of the uri if present.

  Useful for handling optional trailing slashes until Compojure's route matching syntax supports regex.
  Adapted from http://stackoverflow.com/questions/8380468/compojure-regex-for-matching-a-trailing-slash"
  [handler]
  (fn [request]
    (let [uri (:uri request)]
      (handler (assoc request :uri (if (and (not= "/" uri)
                                            (.endsWith uri "/"))
                                     (subs uri 0 (dec (count uri)))
                                     uri))))))

(defn app
  "Handler for the server, accepts just one argument `serving-dir`, which is the directory path to be served"
  [serving-dir]
  (-> (dir-routes serving-dir)
      ignore-trailing-slash
      (wrap-file serving-dir)))