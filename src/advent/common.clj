(ns advent.common)

(require '[advent.day-1 :as day-1])
(use '[clojure.string :only [trim]])

(defn read-input
  "Reads input for given day"
  [day-no]
  (trim (slurp (str "input-files/day-" day-no ".txt"))))

(defn apply-solution
  "Applies solution to input"
  [day-no input]
  (when (= day-no "1")
    (day-1/solve input)))

(defn format-item
  [item]
  (apply str "\n" item))

(defn format-output
  "Formats output to be (somewhat) readable"
  [input]
  (map format-item input))


