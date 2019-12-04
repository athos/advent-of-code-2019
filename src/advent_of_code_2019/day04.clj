(ns advent-of-code-2019.day04)

(defn num->digits [n]
  (loop [n n ds ()]
    (if (< n 10)
      (cons n ds)
      (recur (quot n 10) (cons (rem n 10) ds)))))

(defn digits-increasing? [ds]
  (every? (fn [[i j]] (<= i j)) (partition 2 1 ds)))

(defn has-repeated-digits? [ds]
  (first (filter (fn [[i j]] (= i j)) (partition 2 1 ds))))

(defn make-validator [fs]
  (fn [n]
    (let [ds (num->digits n)]
      (every? #(% ds) fs))))

(defn make-solver [fs]
  (let [valid? (make-validator fs)]
    (fn [from to]
      (->> (range from (inc to))
           (filter valid?)
           count))))

(defn part1 [from to]
  (let [solve (make-solver [digits-increasing? has-repeated-digits?])]
    (solve from to)))

(defn run-length-encode [ds]
  (loop [d (first ds) ds (rest ds) n 1 ret []]
    (cond (empty? ds) (conj ret [n d])
          (= d (first ds)) (recur d (rest ds) (inc n) ret)
          :else (recur (first ds) (rest ds) 1 (conj ret [n d])))))

(defn has-twinned-digits? [ds]
  (->> (run-length-encode ds)
       (filter (fn [[n _]] (= n 2)))
       first))

(defn part2 [from to]
  (let [solve (make-solver [digits-increasing? has-twinned-digits?])]
    (solve from to)))
