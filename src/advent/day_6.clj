(ns advent.day-6
  (:require [clojure.string :as s]))

(defn parse-input
  [input]
  (s/split-lines input))

(defn most-frequent
  [input]
  (let [counted (frequencies input)
        sorted (sort-by second counted)]
    (first (last sorted))))

(defn least-frequent
  [input]
  (let [counted (frequencies input)
        sorted (sort-by second counted)]
    (first (first sorted))))

(defn transpose
  [input]
  (apply mapv vector input))

(defn solve
  [input]
  (let [parsed-input (parse-input input)
        transposed-input (transpose parsed-input)
        first-t (first transposed-input)]

    [(apply str (map most-frequent transposed-input))
     (apply str (map least-frequent transposed-input))]))
