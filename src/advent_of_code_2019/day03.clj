(ns advent-of-code-2019.day03
  (:require [clojure.string :as str]))

(defn parse-instruction [instr]
  (let [[_ dir by] (re-matches #"([URDL])(\d+)" instr)]
    [(first dir) (Long/parseLong by)]))

(defn parse-line [line]
  (->> (str/split line #",")
       (map parse-instruction)))

(defn step
  ([state instr] (step (fn [state _] state) state instr))
  ([on-cross {w :wire :as state} [dir by]]
   (let [[c dy dx] (case dir
                     \U [\|  1  0]
                     \R [\-  0  1]
                     \D [\| -1  0]
                     \L [\-  0 -1])]
     (reduce (fn [{i :i [y x] :pos :as state} _]
               (let [pos [(+ y dy) (+ x dx)]
                     entry (get-in state [:map pos])
                     entry' (-> entry
                                (assoc :w w :c (if entry \X c))
                                (assoc-in [:steps w] i))]
                 (-> state
                     (update :i inc)
                     (assoc :pos pos)
                     (assoc-in [:map pos] entry')
                     (cond-> (and entry (not= (:w entry) w)) (on-cross pos)))))
             state
             (range 1 (inc by))))))

(defn print-map [m]
  (newline)
  (let [ys (map first (keys m))
        xs (map second (keys m))
        min-y (apply min ys)
        max-y (apply max ys)
        min-x (apply min xs)
        max-x (apply max xs)]
    (doseq [i (range max-y (dec min-y) -1)]
      (doseq [j (range min-x (inc max-x))]
        (print (or (some-> (get m [i j]) :c) \.)))
      (newline))))

(defn run [first-path second-path]
  (let [state (reduce step {:map {} :wire 1 :i 1 :pos [0 0]} first-path)]
    (reduce (partial step #(update %1 :x-points conj %2))
            (assoc state :wire 2 :i 1 :pos [0 0] :x-points [])
            second-path)))

(defn manhattan-distance [[y x]]
  (+ (Math/abs (long y)) (Math/abs (long x))))

(defn part1 [lines]
  (let [[first-path second-path] (map parse-line lines)]
    (->> (run first-path second-path)
         :x-points
         (map manhattan-distance)
         (apply min))))

(defn part2 [lines]
  (let [[first-path second-path] (map parse-line lines)
        {m :map :keys [x-points]} (run first-path second-path)]
    (->> x-points
         (map (fn [pos]
                (let [{{f 1 s 2} :steps} (get m pos)]
                  (+ f s))))
         (apply min))))

(comment

 (parse-instruction "R1008")

 (-> {:map {[0 0] {:c \o}} :pos [0 0] :wire 1 :i 0}
     (step [\R 8])
     (step [\U 5])
     (step [\L 5])
     (step [\D 3])
     :map
     print-map)

 (let [{m :map} (run [[\R 8] [\U 5] [\L 5] [\D 3]] [[\U 7] [\R 6] [\D 4] [\L 4]])]
   (print-map m))
 (part1 ["R8,U5,L5,D3" "U7,R6,D4,L4"])

 (require '[clojure.java.io :as io])
 (with-open [r (io/reader (io/resource "input03.txt"))]
   (part1 (line-seq r)))

 (part2 ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
         "U62,R66,U55,R34,D71,R55,D58,R83"])
 (part2 ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
         "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"])
 (with-open [r (io/reader (io/resource "input03.txt"))]
   (part2 (line-seq r)))

 )
