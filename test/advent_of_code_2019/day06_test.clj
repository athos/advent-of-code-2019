(ns advent-of-code-2019.day06-test
  (:require [advent-of-code-2019.day06 :as day06]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is are]]))

(deftest part1-test
  (let [lines ["COM)B"
               "B)C"
               "C)D"
               "D)E"
               "E)F"
               "B)G"
               "G)H"
               "D)I"
               "E)J"
               "J)K"
               "K)L"]
        m (day06/build-deps lines)]
    (is (= {"B" "COM"
            "C" "B"
            "G" "B"
            "D" "C"
            "H" "G"
            "E" "D"
            "I" "D"
            "F" "E"
            "J" "E"
            "K" "J"
            "L" "K"}
           m))
    (is (= ["K" "J" "E" "D" "C" "B" "COM"]
           (day06/orbital-path m "L")))
    (is (= 42 (day06/part1 lines))))
  (with-open [r (io/reader (io/resource "input06.txt"))]
    (is (= 223251 (day06/part1 (line-seq r))))))

(deftest part2-test
  (let [lines ["COM)B"
               "B)C"
               "C)D"
               "D)E"
               "E)F"
               "B)G"
               "G)H"
               "D)I"
               "E)J"
               "J)K"
               "K)L"
               "K)YOU"
               "I)SAN"]]
    (is (= 4 (day06/part2 lines))))
  (with-open [r (io/reader (io/resource "input06.txt"))]
    (is (= 430 (day06/part2 (line-seq r))))))
