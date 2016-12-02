(ns advent.core
  (:gen-class))

(require '[advent.common :as common])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [day-no (first args)]
    (println (common/apply-solution day-no (common/read-input day-no)))))