(defproject advent "0.1.0-SNAPSHOT"
  :description "Solutions to advent of code 2016 by @ghallberg"
  :url "http://github.com/ghallberg/advent"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [digest "1.4.5"]]
  :main ^:skip-aot advent.advent
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
