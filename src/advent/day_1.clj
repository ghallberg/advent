(ns advent.day-1)

(use '[clojure.string :only (split)])

(defn get-parts
  [instruction]
    [(str(first instruction)) (read-string (subs instruction 1))])

(defn parse-instructions
  [input]
  (map get-parts (split input #" ")))

(defn turner
  [headings instruction]
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

(defn summer
  [coords step]
  (let [step-matrix {"N" ["x" +],
                     "E" ["y" +],
                     "S" ["x" -],
                     "W" ["y" -]}
        [direction count] step
        [target operation] (step-matrix direction)
        [x-value y-value] coords]
    (if (= target "x")
      [(operation x-value count) y-value]
      [x-value (operation y-value count)])))

(defn abs-dist
  [coords]

  (let [[x y] coords]
    (+ (Math/abs x) (Math/abs y))))

(defn solve
  [input]

  (let [instructions (parse-instructions input)]
    (abs-dist (reduce summer [0,0] (reduce turner [["N" 0]] instructions)))))
