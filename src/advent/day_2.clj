(ns advent.day-2)

(use '[clojure.string :only (split)])

(defn translate-instruction
  [c]
  (let [trans-table {"D" [:vert 0],
                     "U" [:vert 1],
                     "R" [:hori 0],
                     "L" [:hori 1]}]
    (trans-table (str c))))

(defn parse-instructions
  [input]
  (map (partial map translate-instruction) (split input #"\n")))

(defn transition-filter
  [origin direction pair]
  (= origin (nth pair direction)))

(defn follow-instruction1
  [origin [axis direction]]
  (let [transitions {:hori [[1 2] [2 3] [4 5] [5 6] [7 8] [8 9]]
                     :vert [[1 4] [2 5] [3 6] [4 7] [5 8] [6 9]]}
        transition (first (filter (partial transition-filter origin direction) (transitions axis)))]
    (if transition
      (first (remove (partial = origin) transition))
      origin)))

(defn follow-instruction2
  [origin [axis direction]]
  (let [transitions {:hori [[2 3] [3 4] [5 6] [6 7] [7 8] [8 9] ["A" "B"] ["B" "C"]]
                     :vert [[2 6] [6 "A"] [1 3] [3 7] [7 "B"] ["B" "D"] [4 8] [8 "C"]]}
        transition (first (filter (partial transition-filter origin direction) (transitions axis)))]
    (if transition
      (first (remove (partial = origin) transition))
      origin)))

(defn follow-instructions1
  [stops current-line]
  (let [origin (last stops)]
    (conj stops (reduce follow-instruction1 origin current-line))))

(defn follow-instructions2
  [stops current-line]
  (let [origin (last stops)]
    (conj stops (reduce follow-instruction2 origin current-line))))

(defn solve
  "Should solve the problem(s) of day 2, right?"
  [input]
  (let [instructions (parse-instructions input)
        stops1 (reduce follow-instructions1 [5] instructions)
        stops2 (reduce follow-instructions2 [5] instructions)]
    [["Code 1: " (rest stops1)]["Code 2: " (rest stops2)]]))
