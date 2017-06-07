(ns advent.day-8
  (:require [clojure.string :as s]))

(defn parse-num
  [input]
  (Integer. (second (s/split input #"="))))

(defn parse-x-y
  [input]
  (let [[x y] (s/split input #"x")]
    [(Integer. x) (Integer. y)]))


(defn parse-line
  [line]
  (let [tokens (s/split line #" ")
        command (first tokens)
        args (rest tokens)]
    (case command
      "rect" (let [[y x] (parse-x-y (first args))]
               {:operation :rect, :x x, :y y})
      "rotate" (case (first args)
                 "row" {:operation :rr, :num (parse-num (second args)), :amount (Integer. (last args))}
                 "column" {:operation :rc, :num (parse-num (second args)), :amount (Integer. (last args))}))))

(defn parse-input
  [input]
  (let [lines (s/split-lines input)]
    (map parse-line lines)))

(defn print-display-line
  [display-line]
  (apply str (map (fn [v] (if v "#" ".")) display-line)))

(defn print-display
  [display]
  (doseq [row display]
    (println (print-display-line row))))

(defn gen-display
  [height width]
  (vec (take width (repeat (vec (take height (repeat false)))))))

(defn rotate-row
  [row amount]
  (let [row-length (count row)
        net-rotation (mod amount row-length)
        head (take (- row-length net-rotation) row)
        tail (take-last net-rotation row) ]
    (vec (concat tail head))))

(defn transpose [m]
  (apply mapv vector m))

(defn assoc-first [row values]
  (let [indexes (range (count values))]
    (apply assoc row (interleave indexes values))))

(defn light-up [num row]
  (assoc-first row (take num (repeat true))))

(defmulti apply-command (fn [display command]
                          (print-display display)
                          (println command)
                          (:operation command)))
(defmethod apply-command :rc
  [display command]
  (let [{:keys [num amount]} command
        transposed-display (transpose display)
        updated-trans-display (update transposed-display num rotate-row amount)]
    (transpose updated-trans-display)))

(defmethod apply-command :rr
  [display command]
  (let [{:keys [num amount]} command]
    (update display num rotate-row amount)))

(defmethod apply-command :rect
  [display command]
  (let [{:keys [x y]} command
        affected-rows (take x display)
        updated-rows (map (partial light-up y) affected-rows)]
    (assoc-first display updated-rows)))

(defn bool-to-num [input]
  (if input
    1
    0))

(defn count-lit-pixels
  [display]
  (let [num-display (map (partial map bool-to-num) display)]
    (reduce + (flatten num-display))))

(defn solve
  [input]
  (let [command-list (parse-input input)
        start-display (gen-display 50 6)
        end-display (reduce apply-command start-display command-list)]

    (print-display end-display)
    (println (str "# Lit pixels: " (count-lit-pixels end-display)))))
