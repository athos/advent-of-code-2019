(ns advent-of-code-2019.day02-test
  (:require [advent-of-code-2019.day02 :as day02]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(defn program []
  (read-string (str \[ (slurp (io/resource "input02.txt")) \])))

(deftest part1-test
  (is (= [3500,9,10,70,2,3,11,0,99,30,40,50]
         (day02/run [1,9,10,3,2,3,11,0,99,30,40,50])))
  (is (= [2,0,0,0,99] (day02/run [1,0,0,0,99])))
  (is (= [2,3,0,6,99] (day02/run [2,3,0,3,99])))
  (is (= [30,1,1,4,2,5,6,0,99] (day02/run [1,1,1,4,99,5,6,0,99])))
  (is (= 4138687 (day02/part1 (program) 12 2))))

(deftest part2-test
  (is (= [66 35] (day02/part2 (program) 19690720))))
