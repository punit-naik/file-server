(ns file-server.server.routes-test
  (:require [clojure.test :refer [deftest testing is]]
            [compojure.core :refer [GET]]
            [file-server.server.routes :as sr]
            [file-server.server.handler :refer [dir-routes]]
            [file-server.views :refer [generate-views]]
            [hiccup.core :refer [html]]
            [ring.mock.request :as mock]
            [ring.util.response :refer [response]]))

(deftest generate-routes-for-dir-test
  (testing "Testing if the fn `file-server.server.routes/generate-routes-for-dir` is working or not or not"
    (let [dir "doc"
          actual (sr/generate-routes-for-dir dir)
          actual-handler (dir-routes dir)
          expected [(GET "/" [] (response (html (generate-views dir false))))]]
      (is (= (count actual) (count expected)))
      (is (= (select-keys (actual-handler (mock/request :get "/")) [:status :body])
             (assoc {} :status 200 :body (html (generate-views dir false))))))))
