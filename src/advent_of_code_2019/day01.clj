(ns advent-of-code-2019.day01)

(defn fuel-required [mass]
  (- (quot mass 3) 2))

(defn part1 [modules]
  (transduce (map #(fuel-required (Long/parseLong %))) + 0 modules))

(defn fuel-required' [mass]
  (->> (iterate fuel-required mass)
       rest
       (take-while #(>= % 0))
       (apply +)))

(defn part2 [modules]
  (transduce (map #(fuel-required' (Long/parseLong %))) + 0 modules))

(comment

  (require '[clojure.java.io :as io])
  (def r
    (io/reader (io/resource "input01.txt")))

  (part1 (line-seq r))
  (part2 (line-seq r))

  )
