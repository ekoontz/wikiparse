(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]))

(def small (with-open [input (java.io.FileInputStream. "small.xml")]
             (parse input)))


(def thousand (with-open [input (java.io.FileInputStream. "1000.xml")]
                (parse input)))

(def ten-thousand (with-open [input (java.io.FileInputStream. "10000.xml")]
                    (parse input)))

(def hundred-thousand (with-open [input (java.io.FileInputStream. "100000.xml")]
                        (parse input)))


