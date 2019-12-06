(ns advent-of-code-2019.day06)

(defn build-deps [lines]
  (reduce (fn [m line]
            (let [[_ parent child] (re-matches #"(.+)\)(.+)" line)]
              (assoc m child parent)))
          {}
          lines))

(defn orbital-path
  ([m from] (orbital-path m from "COM"))
  ([m from to]
   (loop [node from path []]
     (let [parent (get m node)
           path' (conj path parent)]
       (if (= parent to)
         path'
         (recur parent path'))))))

(defn count-hops
  ([m from] (count-hops m from "COM"))
  ([m from to]
   (count (orbital-path m from to))))

(defn part1 [lines]
  (let [m (build-deps lines)]
    (apply + (map (partial count-hops m) (keys m)))))

(defn part2 [lines]
  (let [m (build-deps lines)
        san-parents (set (orbital-path m "SAN"))
        common-parent (some san-parents (orbital-path m "YOU"))]
    (+ (count-hops m (m "YOU") common-parent)
       (count-hops m (m "SAN") common-parent))))

(comment

  (def lines
    ["COM)B"
     "B)C"
     "C)D"
     "D)E"
     "E)F"
     "B)G"
     "G)H"
     "D)I"
     "E)J"
     "J)K"
     "K)L"])

  (def m (build-deps lines))
  (orbital-path m "L" "COM")
  (apply + (map (partial count-hops m) (keys m)))

  (def lines'
    ["COM)B"
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
     "I)SAN"])
  (def m' (build-deps lines'))
  (def san-to-com (set (orbital-path m' "SAN")))
  (some san-to-com (orbital-path m2 "YOU"))
  (+ (count (orbital-path m2 "SAN" "D"))
     (count (orbital-path m2 "YOU" "D"))
     -2)

  )
