(ns one-function-to-rule-them-all)

(defn concat-elements [a-seq]
  (reduce concat a-seq))

(defn str-cat [a-seq]
  (let [interpose-space (fn [x y]
                          (apply str (concat x " " y)))]
    (if (empty? a-seq)
      ""
      (reduce interpose-space (first a-seq) (rest a-seq)))))

(defn my-interpose [x a-seq]
  (let [interpose-x (fn [y z]
                      (conj (conj y x) z))]
    (if (empty? a-seq)
      []
      (reduce interpose-x [(first a-seq)] (rest a-seq)))))


(defn my-count [a-seq]
  (let [seq-counter (fn [count elem]
                      (if (nil? elem)
                        count
                        (inc count)))]
    (reduce seq-counter 0 a-seq)))

(defn my-reverse [a-seq]
  (let [reverser (fn [prev nextelem]
                   (cons nextelem prev))]
    (if (empty? a-seq)
      a-seq
      (reduce reverser [(first a-seq)] (rest a-seq)))))

(defn min-max-element [a-seq]
  (let [min-max (fn [[curr-min curr-max] elem]
                  (cond (nil? curr-min) [elem elem]
                        (< curr-max elem) [curr-min elem]
                        (< elem curr-min) [elem curr-max]
                        :else [curr-min curr-max]))]
  (reduce min-max [nil nil] a-seq)))

(defn insert [sorted-seq n]
  (let [find-index (fn [sorted-seq n curr-index sorted-seq-count]
                     (cond (empty? sorted-seq) curr-index
                           (= curr-index sorted-seq-count) curr-index
                           (< n (nth sorted-seq curr-index)) curr-index
                           :else (recur sorted-seq n (inc curr-index) sorted-seq-count)))
        index (find-index sorted-seq n 0 (count sorted-seq))]
    (if (empty? sorted-seq)
      [n]
      (concat (concat (take index sorted-seq) [n]) (drop index sorted-seq)))))

(defn insertion-sort [a-seq]
  (reduce insert [] a-seq))

(defn toggle [a-set elem]
  (if (contains? a-set elem) (disj a-set elem) (conj a-set elem)))

(defn parity [a-seq]
  (let [parity-checker (fn [parity-set elem]
                        (toggle parity-set elem))]
    (reduce parity-checker #{} a-seq)))

(defn minus
  ([x] (- 0 x))
  ([x y] (- x y)))

(defn count-params [& more]
  (count more))

(defn my-*
  ([] 1)
  ([x] x)
  ([x y] (* x y))
  ([x y & more] (reduce * (* x y) more)))

(defn pred-and
    ([] (fn [x] true))
    ([pred] pred)
    ([pred1 pred2] (fn [x] (and (pred1 x) (pred2 x))))
    ([pred1 pred2 & more]
       (let [combine-preds (fn [pred1 pred2]
                        (fn [x] (and (pred1 x) (pred2 x))))]
         (reduce combine-preds (combine-preds pred1 pred2) more))))


(defn length-of-shortest-seq [seqs]
  (if (= 1 (count seqs))
    (count (first seqs))
    (min (count (first seqs)) (length-of-shortest-seq (rest seqs)))))

(defn get-element-at-index-from-seqs [index seqs]
  (if (empty? seqs)
    []
    (cons (nth (first seqs) index) (get-element-at-index-from-seqs index (rest seqs)))))

(defn get-elements-at-index-seq [max-index seqs]
  (loop [index 0
         current-seq []]
    (if (<= max-index index)
      current-seq
      (recur (inc index) (cons (get-element-at-index-from-seqs index seqs) current-seq)))))

(defn my-map
  ([f a-seq]
    (let [apply-f-to-elem (fn [prev-seq elem]
                            (conj prev-seq (f elem)))]
      (reduce apply-f-to-elem [] a-seq)))
  ([f a-seq & seqs]
   (let [min-seq-len (min (count a-seq) (length-of-shortest-seq seqs))
         seqs-as-sequence (cons a-seq seqs)
         apply-f-to-elem (fn [prev-seq elemseq]
                           (cons (apply f elemseq) prev-seq))]
      (reduce apply-f-to-elem [] (get-elements-at-index-seq min-seq-len seqs-as-sequence)))))
