(ns file-server.server
  (:require [clojure.java.io :refer [file]]
            [file-server.server.handler :refer [app]]
            [file-server.utils.io :refer [get-free-port]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(defn start
  "Takes first argument `port` to start the server on
   And a second argument `serving-dir` which is the directory while will be served
   If `port` is not specified, it will start the server on a random free port using `file-server.utils.io/get-free-port`
   If `serving-dir` is not specified, it will start serving the current directory
   NOTE: Both the arguments are optional"
  ([] (start (get-free-port)))
  ([port] (start port "."))
  ([port serving-dir]
   (if (.exists (file serving-dir))
     (do (run-jetty (app serving-dir)
                    {:join? false
                     :port (cond-> port
                             (string? port) Integer/parseInt)})
         (println "Server started on port" port
                  "and serving files under" (if (= serving-dir ".")
                                              "current directory"
                                              serving-dir)))
     (throw (Exception. (str "`" serving-dir "` does not exist"))))))

(defn -main [& args]
  (apply start args))
