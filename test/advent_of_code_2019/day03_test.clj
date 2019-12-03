(ns advent-of-code-2019.day03-test
  (:require [advent-of-code-2019.day03 :as day03]
            [clojure.test :refer [deftest is are]]
            [clojure.java.io :as io]))

(deftest part1-test
  (let [{:keys [x-points]} (day03/run
                             [[\R 8] [\U 5] [\L 5] [\D 3]]
                             [[\U 7] [\R 6] [\D 4] [\L 4]])]
    (is (= #{[5 6] [3 3]} (set x-points))))

  (are [inputs expected] (= expected (day03/part1 inputs))
    ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
     "U62,R66,U55,R34,D71,R55,D58,R83"]
    159

    ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
     "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"]
    135)
  (with-open [r (io/reader (io/resource "input03.txt"))]
    (is (= 2180 (day03/part1 (line-seq r))))))

(deftest part2-test
  (are [inputs expected] (= expected (day03/part2 inputs))
    ["R75,D30,R83,U83,L12,D49,R71,U7,L72"
     "U62,R66,U55,R34,D71,R55,D58,R83"]
    610

    ["R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
     "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"]
    410)
  (with-open [r (io/reader (io/resource "input03.txt"))]
    (is (= 112316 (day03/part2 (line-seq r))))))
