(ns advent.day-5
  (:require [clojure.string :as s]))
(require 'digest)

(defn is-indicator?
  [md5]
  (s/starts-with? md5 "00000"))

(defn relevant-chars
  [indicator]
  [(nth indicator 5) (nth indicator 6)])

(defn find-password
  [seen [pos letter]]
  (let [int-pos (read-string (str pos))]
  (if (> (count seen) 7)
    (reduced (map second (sort seen)))
    (if (and (some #{int-pos} (range 8))
             (not (contains? seen int-pos)))
      (assoc seen int-pos letter)
      seen))))

(defn get-passwords
  [input length]
  (let [md5-inputs (map (partial str input) (range))
        md5s (map digest/md5 md5-inputs)
        indicators (filter is-indicator? md5s)
        char-pairs (map relevant-chars indicators)
        naive-password-letters (map first char-pairs)
        cooler-password-letters (reduce find-password {} char-pairs)]

    [(apply str (take length naive-password-letters))
     (apply str (take length cooler-password-letters))]))

(defn solve
  [input]
  (let [[naive-password cooler-password] (get-passwords input 8)]
    ["Naive Password: " naive-password
     "Cooler Password: " cooler-password]))
