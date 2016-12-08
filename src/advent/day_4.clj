(ns advent.day-4)

(use '[clojure.string :only (split replace)])

(defn split-descriptor
  [descriptor]
  (rest (re-find #"(\D+)-(\d+)\[(.*)\]" descriptor)))

(defn parse-parts
  [descriptor]
  (let [[crypt-name sector-id checksum] (split-descriptor descriptor)]
    {:name (replace crypt-name "-" ""),
     :sector sector-id,
     :checksum checksum}))

(defn parse-rooms
  [input]
  (map parse-parts (split input #"\n")))
(defn clean-vec
  [input]
  (map first input))

(defn calculate-checksum
  [input]
  (clean-vec (sort-by key > (group-by second (frequencies input)))))


(defn solve
  [input]
  (parse-rooms input))
