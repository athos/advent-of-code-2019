(ns advent-of-code-2019.day02)

(defn step [{:keys [ip program] :as state}]
  (let [[op i j dst] (subvec program ip)]
    (if-let [f (case (int op)
                 1 +
                 2 *
                 99 nil
                 (assert false (str op " is not a valid opcode")))]
      (-> state
          (assoc-in [:program dst] (f (nth program i) (nth program j)))
          (update :ip + 4))
      (assoc state :finished? true))))

(defn run [program]
  (let [state {:ip 0 :program program}]
    (->> (iterate step state)
         (drop-while (complement :finished?))
         first
         :program)))

(defn part1 [program noun verb]
  (let [program' (assoc program 1 noun 2 verb)]
    (first (run program'))))

(defn part2 [program val]
  (->> (for [noun (range 100)
             verb (range 100)
             :when (= (part1 program noun verb) val)]
         [noun verb])
       first))

(comment

  (part1 [1,9,10,3,2,3,11,0,99,30,40,50])

  (part1 [1,1,1,4,99,5,6,0,99])

  (require '[clojure.java.io :as io]
           '[clojure.string :as str])
  (def program
    (read-string (str \[ (slurp (io/resource "input02.txt")) \])))
  (part1 program 12 2) ;=> 4138687
  (part2 program 19690720)

  )
