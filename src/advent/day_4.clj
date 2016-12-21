(ns advent.day-4
  (:require [clojure.string :as s]))

(defn split-descriptor
  [descriptor]
  (rest (re-find #"(\D+)-(\d+)\[(.*)\]" descriptor)))

(defn parse-parts
  [descriptor]
  (let [[crypt-name sector-id checksum] (split-descriptor descriptor)]
    {:name (s/replace crypt-name "-" ""),
     :crypt-words (s/split crypt-name #"-"),
     :sector (read-string sector-id),
     :checksum checksum}))

(defn parse-rooms
  [input]
  (map parse-parts (s/split input #"\n")))

(defn unwrap
  [groups]
   (map (partial map first) (map second groups)))

(defn make-groups
  [room-name]
  (sort-by key > (group-by second (frequencies room-name))))

(defn calculate-checksum
  [room-name]
  (let [groups (make-groups room-name)
        letters (map sort (unwrap groups))]
    (apply str (take 5 (flatten (map sort letters))))))

(defn check-checksum
  [room]
  (= (:checksum room) (calculate-checksum (:name room))))

(defn shift-letter
  [shift-length letter]
  (let [int-letter (int letter)
        shifted-int (+ shift-length int-letter)]

    (char (if (> shifted-int 122)
            (- shifted-int 26)
            shifted-int))))

(defn decrypt-word
  [sector encrypted-word]
  (let [steps (mod sector 26)]
    (apply str (map (partial shift-letter steps) encrypted-word))))

(defn decrypt-name
  [room]
  (s/join " " (map (partial decrypt-word (:sector room)) (:crypt-words room))))

(defn interesting-room-name
  [room]
  (s/includes? (decrypt-name room) "pole"))

(defn solve
  [input]
  (let [rooms (parse-rooms input)
        real-rooms (filter check-checksum rooms)
        interesting-rooms (filter interesting-room-name real-rooms)]



    [["Sector sum: " (reduce + (map :sector real-rooms))]
     ["Interesting sector: " (map :sector interesting-rooms)]]))
