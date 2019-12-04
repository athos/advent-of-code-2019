(ns advent-of-code-2019.day04-test
  (:require [advent-of-code-2019.day04 :as day04]
            [clojure.test :refer [deftest is are]]))

(deftest part1-test
  (let [valid? (day04/make-validator [day04/digits-increasing?
                                      day04/has-repeated-digits?])]
    (are [input expected] (= expected (valid? input))
      111111 true
      223450 false
      123789 false))
  (is (= 945 (day04/part1 264360 746325))))

(deftest part2-test
  (let [valid? (day04/make-validator [day04/digits-increasing?
                                      day04/has-twinned-digits?])]
    (are [input expected] (= expected (valid? input))
      112233 true
      123444 false
      111122 true))
  (is (= 617 (day04/part2 264360 746325))))
