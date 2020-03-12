(ns file-server.views-test
  (:require [clojure.java.io :refer [file]]
            [clojure.test :refer [deftest testing is]]
            [file-server.utils.io :refer [get-dir-without-root]]
            [file-server.views :as views]))

(defn- get-link
  [f]
  [:li [:a {:href (str "/" (get-dir-without-root (.getPath f)))} (.getName f)]])

(deftest generate-link-test
  (testing "The function `file-server.views/generate-link`"
    (let [f (file "src/file_server/server.clj")]
      (is (= (views/generate-link f true)
             (get-link f))))
    (let [f (file "src/file_server/server/handler.clj")]
      (is (= (views/generate-link f true)
             (get-link f))))))

(deftest generate-view-test
  (testing "The function `file-server.views/generate-views`"
    (is (= (views/generate-views "doc" false)
           [:ul '([:li [:a {:href "/intro.md"} "intro.md"]])]))
    (is (= [:ul (sort-by #(-> % second last)
                         (second (views/generate-views "src/file_server/server" false)))]
           [:ul '([:li [:a {:href "/file_server/server/handler.clj"} "handler.clj"]]
                  [:li [:a {:href "/file_server/server/routes.clj"} "routes.clj"]])]))))
