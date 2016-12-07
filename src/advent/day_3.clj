(ns advent.day-3)

(use '[clojure.string :only (split trim)])

(defn parse-sides
  [triangle-string]
  (map read-string (split (trim triangle-string) #" ")))

(defn parse-possibles
  [input]
  (map parse-sides (split input #"\n")))

(defn triangle?
  [sides]
  (let [sorted-sides (sort sides)
        lesser-sides (take 2 sorted-sides)
        longest-side (last sorted-sides)]
    (> (apply + lesser-sides) longest-side)))

(defn count-triangles
  [possibles]
  (let [actual-triangles(filter triangle? possibles)]
    (println actual-triangles)
    (count actual-triangles)))

(defn solve
  [input]
  (let [possibles (parse-possibles input)]
    (println possibles)
    ["Count: " (count-triangles possibles)]))
