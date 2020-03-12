(ns file-server.server.handler-test
  (:require [clojure.test :refer [deftest testing is use-fixtures]]
            [file-server.server.handler :refer [app]]
            [file-server.server-test :as server-test]
            [file-server.utils.io :refer [get-dir-without-root]]
            [ring.mock.request :as mock]))

(use-fixtures :once server-test/temp-file-fixture)

(deftest start-test
  (testing "Testing if the handler `file-server.server.handler/app` is working or not or not"
    (let [my-handler (app server-test/root-dir)]
      (is (= (-> (mock/request :get (str "/" (get-dir-without-root server-test/file-path)))
                 my-handler
                 (select-keys [:status :body])
                 (update :body slurp))
             {:status  200
              :body    server-test/file-contents}))
      (is (= (select-keys (my-handler (mock/request :get "/"))
                          [:status :body])
             {:status  200
              :body    (str "<ul><li><a href=\"/" server-test/middle-dir "\">" server-test/middle-dir "</a></li></ul>")}))
      (is (= (select-keys (my-handler (mock/request :get (str "/" server-test/middle-dir)))
                          [:status :body])
             {:status  200
              :body    (str "<ul><li><a href=\"/" server-test/middle-dir "/" server-test/file-name "\">" server-test/file-name "</a></li></ul>")})))))
