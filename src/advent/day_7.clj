(ns advent.day-7
  (:require [clojure.string :as s]
            [clojure.set]))

(defn compact
  [list]
  (remove nil? list))

(defn dezip
  [input]
  (map compact (apply map list (partition 2 2 [nil] input))))

(defn parse-line
  [line]
  (dezip (s/split line #"[\[\]]")))

(defn parse-input
  [input]
  (let[lines (s/split-lines input)]
    (map parse-line lines)))


(defn abba?
  [string]
  (let [head      (first string)
        neck      (second string)
        legs      (nth string 2)
        feet      (last string)
        outer     (= head feet)
        inner     (= neck legs)
        not-same  (not (= head neck))]
    (and outer inner not-same)))

(defn has-abba?
  [string]
  (if (abba? (take 4 string))
    true
    (if (> (count string) 3)
      (has-abba? (rest string))
      false)))

(defn tls?
  [input]
  (let [[outsides hypernets] input]
    (and (some has-abba? outsides) (not-any? has-abba? hypernets))))

(defn aba-bab? [candidate]
  (let [head   (first candidate)
        middle (second candidate)
        feet   (last candidate)]
    (and (= head feet) (not (= head middle)))))

(defn aba-bab-candidates [prev-candidates string]
  (let [new-candidate  (apply str (take 3 string))
        candidates (conj prev-candidates new-candidate)]
    (if (> (count string) 3)
      (aba-bab-candidates candidates (rest string))
      candidates)))

(defn aba-babs
  [string]
  (let [candidates (aba-bab-candidates #{} string)]
    (filter aba-bab? candidates)))

(defn invert-bab-aba [input-bab]
  (let [head   (first input-bab)
        middle (second input-bab)]
    (str middle head middle)))

(defn flat-set [coll-of-colls] (set (flatten coll-of-colls)))

(defn ssl?
  [input]
  (let [[outsides hypernets] input
        abas (flat-set (map aba-babs outsides))
        babs (flat-set (map aba-babs hypernets))
        wanted-babs (set (map invert-bab-aba abas))]
    (not-empty (clojure.set/intersection babs wanted-babs))))

(defn solve
  [input]
  (let [address-tuples (parse-input input)]
    {:tls-count (count (filter tls? address-tuples)), :ssl-count (count (filter ssl? address-tuples))}))
