(ns file-server.utils.io-test
  (:require [clojure.java.io :refer [file]]
            [clojure.test :refer [deftest testing is]]
            [file-server.utils.io :as io]))

(deftest dir-not-empty?-test
  (testing "The `file-server.utils.io/dir-not-empty?` function"
    (is (io/dir-not-empty? (file "test")))
    (is (io/dir-not-empty? (file "src")))))

(deftest sub-dir?-test
  (testing "The `file-server.utils.io/sub-dir?` function"
    (is (not (io/sub-dir? "test")))
    (is (io/sub-dir? "test/file_server"))))

(deftest get-dir-without-root-test
  (testing "The `file-server.utils.io/get-dir-without-root` function"
    (is (= (io/get-dir-without-root "test/file_server") "file_server"))
    (is (= (io/get-dir-without-root "src/file_server") "file_server"))))

(deftest get-free-port-test
  (testing "The `file-server.utils.io/get-free-port` function"
    (is (int? (io/get-free-port)))))