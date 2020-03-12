(ns file-server.utils.io
  (:require [clojure.string :refer [join split]]))

(defn dir-not-empty?
  [dir]
  (pos? (count (.list dir))))

(defn sub-dir?
  [dir]
  (> (count (split dir #"/")) 1))

(defn get-dir-without-root
  "Gets the dir path as a string without the root directory
   For e.g. If you provide `dir` as \"a/b/c\", this function will output \"b/c\""
  [dir]
  (->> (split dir #"\/")
       rest
       (join "/")))

(defn get-free-port
  []
  (let [socket (java.net.ServerSocket. 0)]
    (.close socket)
    (.getLocalPort socket)))