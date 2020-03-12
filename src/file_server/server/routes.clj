(ns file-server.server.routes
  (:require [clojure.java.io :refer [file]]
            [clojure.string :refer [replace]]
            [hiccup.core :refer [html]]
            [compojure.core :refer [GET]]
            [file-server.utils.io :as io-utils]
            [file-server.views :refer [generate-views]]))

(defn generate-routes-for-dir
  "Generates compojure routes for the `dir` and all of it's files and sub-directories"
  ([dir] (generate-routes-for-dir dir false))
  ([dir sub-dir?]
   (loop [d (->> (file dir)
                 .list
                 (map #(file (str dir "/" %)))
                 (filter #(.isDirectory %)))
          result [(GET (str "/"
                            (cond-> dir
                              (= dir ".") (replace #"\." "")
                              (or (io-utils/sub-dir? dir)
                                  (false? sub-dir?)) io-utils/get-dir-without-root))
                    [] (html (generate-views dir sub-dir?)))]]
     (if (empty? d)
       result
       (recur (rest d)
              (cond-> result
                (io-utils/dir-not-empty? (first d)) (into (generate-routes-for-dir (.getPath (first d)) true))))))))
