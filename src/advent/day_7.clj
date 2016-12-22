(ns advent.day-6
  (:require [clojure.string :as s]))

(defn parse-input
  [input]
  input)

(defn abba?
  [string]
  string)

(defn tls?
  [outside hypernet]
  (and (any? abba? outside) (not (any? abba? hypernets))))

(defn solve
  [input]
  (let [address-tuples (parse-input input)]
    (count (filter tls? address-tuples))))
