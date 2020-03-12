(ns file-server.views
  (:require [clojure.java.io :refer [file]]
            [file-server.utils.io :as io-utils]))

(defn generate-link
  "Takes a java.io.File (`f` can be a file or a directory) and generates an HTML link for it
   `f` must be already served by the server for the link to work
   Optionally, you can provide the root `dir` to it from the href to generate the link properly"
  [f sub-dir?]
  [:li [:a {:href (str "/" (cond-> (.getPath f)
                             (or (io-utils/sub-dir? (.getPath f))
                                 (false? sub-dir?)) io-utils/get-dir-without-root))} (.getName f)]])

(defn generate-views
  "Generates HTML views recursively using the `serving-dir`
   to display links for every file and directory inside the `serving-dir`"
  [serving-dir sub-dir?]
  (loop [file-list (->> (file serving-dir)
                        .list
                        (map #(file (str serving-dir "/" %))))
         result []]
    (if (empty? file-list)
      [:ul (apply list result)]
      (recur (rest file-list)
             (conj result (generate-link (first file-list) sub-dir?))))))
