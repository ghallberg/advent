(ns advent.advent
  (:gen-class))

(require '[advent.common :as common])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [day-no (first args)]
    (try (println (common/format-output (common/apply-solution day-no)))
         (catch IllegalArgumentException e (println (.getMessage e))))))
