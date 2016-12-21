(ns advent.day-5)
(require 'digest)
(use '[clojure.string :as s])

(defn is-indicator?
  [md5]
  (starts-with? md5 "00000"))

(defn get-letter
  [indicator]
  (nth indicator 5))

(defn get-password
  [input length]
  (let [md5-inputs (map (partial str input) (range))
        md5s (map digest/md5 md5-inputs)
        indicators (filter is-indicator? md5s)
        password-letters (map get-letter indicators)]

    (apply str (take length password-letters))))

(defn solve
  [input]
  ["Password: " (get-password input 8)])
