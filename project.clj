(defproject file-server "1.0.0"
  :description "Serves a file or directory as static assets on the web server"
  :url "https://github.com/punit-naik/file-server"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [org.martinklepsch/clj-http-lite "0.4.3"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-mock "0.4.0"]
                 [ring/ring-jetty-adapter "1.8.0"]]
  :main ^:skip-aot file-server.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
