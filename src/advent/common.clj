(ns advent.common)

(require '[advent.day-1 :as day-1])
(require '[advent.day-2 :as day-2])

(use '[clojure.string :only [trim]])

(defn read-input
  "Reads input for given day"
  [day-no]
  (try (trim (slurp (str "input-files/day-" day-no ".txt")))
       (catch Exception e
         (throw (Exception. (str "Error while reading input for day " day-no ": " (.getMessage e)))))))

(defn find-solver
  "Fetches a solve function for the specified day"
  [day-no]
  (resolve (symbol (str "advent.day-" day-no "/solve"))))

(defn apply-solution
  "Applies solution to matching input"
  [day-no]
  (let [solver-fn (find-solver day-no)
        input (read-input day-no)]
    (solver-fn input)))

(defn format-item
  [item]
  (apply str "\n" item))

(defn format-output
  "Formats output to be (somewhat) readable"
  [input]
  (map format-item input))


