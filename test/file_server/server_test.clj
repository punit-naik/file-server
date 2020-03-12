(ns file-server.server-test
  (:require [clj-http.lite.client :as client]
            [clojure.java.io :refer [file delete-file make-parents]]
            [clojure.test :refer [deftest testing is use-fixtures]]
            [file-server.server :refer [start]]
            [file-server.utils.io :refer [get-dir-without-root get-free-port]]))

(defonce root-dir (str (java.util.UUID/randomUUID)))
(defonce middle-dir (str (java.util.UUID/randomUUID)))
(defonce file-path (str root-dir "/" middle-dir "/1.txt"))
(defonce file-name (.getName (file file-path)))
(defonce file-contents "test data")
(defonce free-port (get-free-port))

(defn temp-file-fixture [my-tests-runner]
  (make-parents file-path)
  (spit file-path file-contents)
  (my-tests-runner)
  (delete-file file-path)
  (delete-file (str root-dir "/" middle-dir))
  (delete-file root-dir))

(use-fixtures :once temp-file-fixture)

(deftest start-test
  (testing "Testing if the function `file-server.server/start` properly spins up the webserver or not"
    (start free-port root-dir)
    (is (= (select-keys (client/get (str "http://localhost:"
                                         free-port "/"
                                         (get-dir-without-root file-path)))
                        [:status :body])
           {:status  200
            :body    file-contents}))
    (is (= (select-keys (client/get (str "http://localhost:" free-port))
                        [:status :body])
           {:status  200
            :body    (str "<ul><li><a href=\"/" middle-dir "\">" middle-dir "</a></li></ul>")}))
    (is (= (select-keys (client/get (str "http://localhost:"
                                         free-port "/"
                                         middle-dir))
                        [:status :body])
           {:status  200
            :body    (str "<ul><li><a href=\"/" middle-dir "/" file-name "\">" file-name "</a></li></ul>")}))))