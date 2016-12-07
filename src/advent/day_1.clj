(ns advent.day-1)

(use '[clojure.string :only (split)])

(defn get-parts [instruction]
  [(str(first instruction)) (read-string (subs instruction 1))])

(defn parse-instructions
  [input]
  (map get-parts (split input #" ")))

(defn clean-instruction [headings instruction]
  (let [turning-matrix {["N" "R"] "E",
                        ["N" "L"] "W",
                        ["E" "R"] "S",
                        ["E" "L"] "N",
                        ["S" "R"] "W",
                        ["S" "L"] "E",
                        ["W" "R"] "N",
                        ["W" "L"] "S"}
        cur-heading (first (last headings))
        turn-dir (first instruction)
        new-heading (turning-matrix [cur-heading turn-dir])
        num-steps (last instruction)]

    (conj headings [new-heading num-steps])))

(defn to-operation [[direction num-steps]]
  (let [step-matrix {"N" [0 +],
                     "E" [1 +],
                     "S" [0 -],
                     "W" [1 -]}
        [axis operation] (step-matrix direction)]
    [axis operation num-steps]))

(defn generate-positions[path [axis direction num-steps]]
  (let [cur-pos (last path)
        start (direction (nth cur-pos axis) 1)
        end (direction start num-steps)
        step-direction (direction 1)]
    (into path (map (partial assoc cur-pos axis) (range start end step-direction)))))

(defn first-repeat [stops]
  (reduce (fn [seen cur]
            (if-let [exists (get seen cur)]
              (reduced exists)
              (conj seen cur)))
    #{} stops))

(defn abs-dist [coords]
  (let [[x y] coords]
    (+ (Math/abs x) (Math/abs y))))

(defn solve [input]
  (let [instructions (parse-instructions input)
        stops (reduce generate-positions [[0 0]] (map to-operation (reduce clean-instruction [["N" 0]] instructions)))]
    [["Stops:" stops]
     ["First duplicate:" (first-repeat stops)]
     ["First duplicate dist:" (abs-dist (first-repeat stops))]
     ["Distance from origin:" (abs-dist (last stops))]]))
