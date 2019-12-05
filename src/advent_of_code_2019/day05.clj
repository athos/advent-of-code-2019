(ns advent-of-code-2019.day05
  (:require [clojure.string :as str]))

(defn num->digits [n]
  (loop [n n ds ()]
    (if (< n 10)
      (cons n ds)
      (recur (quot n 10) (cons (rem n 10) ds)))))

(defn decode-opcode [n]
  {:op (rem n 100)
   :param-modes (reverse (num->digits (quot n 100)))})

(defn param-values [program n param-modes params]
  (map (fn [i]
         (cond->> (nth params i)
           (= (nth param-modes i 0) 0)
           (nth program)))
       (range n)))

(defn step [{:keys [ip program] :as state}]
  (let [[opcode & args] (subvec program ip)
        {:keys [op param-modes]} (decode-opcode opcode)]
    (case (int op)
      (1 2) (-> state
                (assoc-in [:program (nth args 2)]
                          (->> (param-values program 2 param-modes args)
                               (apply (if (= op 1) + *))))
                (update :ip + 4))
      3 (-> state
            (assoc-in [:program (first args)] (read))
            (update :ip + 2))
      4 (let [[n] (param-values program 1 param-modes args)]
          (pr n)
          (flush)
          (update state :ip + 2))
      (5 6) (let [[n addr] (param-values program 2 param-modes args)]
              (if (or (and (= op 5) (not= n 0))
                      (and (= op 6) (= n 0)))
                (assoc state :ip addr)
                (update state :ip + 3)))
      (7 8) (let [[m n] (param-values program 2 param-modes args)
                  result (if ((if (= op 7) < =) m n) 1 0)]
              (-> state
                  (assoc-in [:program (nth args 2)] result)
                  (update :ip + 4)))
      99 (assoc state :finished? true))))

(defn run [program]
  (let [state {:ip 0 :program program}]
    (->> (iterate step state)
         (drop-while (complement :finished?))
         first
         :program)))

(defn part1 [program input]
  (with-in-str input
    (-> (with-out-str
          (run program))
        (str/replace #"^0+" ""))))

(defn part2 [program input]
  (with-in-str input
    (with-out-str
      (run program))))

(comment

  (require '[clojure.java.io :as io])
  (def program
    (read-string (str \[ (slurp (io/resource "input05.txt")) \])))

  (run program)
  (run [3,9,8,9,10,9,4,9,99,-1,8])
  (run [3,9,7,9,10,9,4,9,99,-1,8])
  (run [3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
        1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
        999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99])

  (part1 program "1")
  (part2 program "5")

  )
