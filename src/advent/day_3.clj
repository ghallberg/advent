(ns advent.day-3)

(use '[clojure.string :only (split trim)])

(defn parse-sides
  [triangle-string]
  (map read-string (split triangle-string #" +")))

(defn convert
  [input]
  (partition 3 (reduce into (apply mapv vector input))))

(defn parse-possibles
  [input]
  (let [possibles1 (map parse-sides (map trim (split input #"\n")))
        possibles2 (convert possibles1)]
    [possibles1 possibles2]))

(defn triangle?
  [sides]
  (let [sorted-sides (sort sides)
        lesser-sides (take 2 sorted-sides)
        longest-side (last sorted-sides)]
    (> (apply + lesser-sides) longest-side)))

(defn count-triangles
  [possibles]
  (let [actual-triangles(filter triangle? possibles)]
    (count actual-triangles)))

(defn solve
  [input]
  (let [[possibles1 possibles2] (parse-possibles input)]
    [["Count 1: " (count-triangles possibles1)]["Count 2: " (count-triangles possibles2)]]))
