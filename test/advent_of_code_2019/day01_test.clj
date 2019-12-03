(ns advent-of-code-2019.day01-test
  (:require [advent-of-code-2019.day01 :as day01]
            [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]))

(deftest part1-test
  (is (= 2 (day01/fuel-required 12)))
  (is (= 2 (day01/fuel-required 14)))
  (is (= 654 (day01/fuel-required 1969)))
  (is (= 33583 (day01/fuel-required 100756)))
  (with-open [r (io/reader (io/resource "input01.txt"))]
    (is (= 3224048 (day01/part1 (line-seq r))))))

(deftest part2-test
  (is (= 2 (day01/fuel-required' 14)))
  (is (= 966 (day01/fuel-required' 1969)))
  (is (= 50346 (day01/fuel-required' 100756)))
  (with-open [r (io/reader (io/resource "input01.txt"))]
    (is (= 4833211 (day01/part2 (line-seq r))))))
