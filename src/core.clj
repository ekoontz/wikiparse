(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]
            [clojure.string :refer [split]]))

(def all-pages
 (with-open
   [input (java.io.FileInputStream.
           "nlwiktionary-20200701-pages-articles.xml")]
   (->>
    (->
     (parse input
            :input-buffer-length (int (Math/pow 2 28)))
     :content)
    (filter #(= (type %) clojure.data.xml.node.Element))
    (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page)))))

(defn page-title [page]
  (->
   (->> (-> page :content)
        (filter #(= (type %) clojure.data.xml.node.Element))
        (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/title)))
   first
   :content
   first))

(defn lookup [canonical-form]
  (->
   (->> all-pages
        (take 100000)
        (filter #(= (page-title %) canonical-form))
        (map :content)
        first
        (filter #(= (type %) clojure.data.xml.node.Element))
        (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/revision))
        (mapcat :content)
        (filter #(= (type %) clojure.data.xml.node.Element))
        (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/text)))
   first
   :content
   first))

(defn split-lines [wiki-content]
  (split wiki-content #"\n"))

(defn demo []
  (count (->> (-> (lookup "hond") split-lines) (take 40) (map println)))
  (println "---")
  (count (->> (-> (lookup "kat") split-lines) (take 40) (map println)))
  (println "---")
  (count (->> (-> (lookup "kind") split-lines) (take 40) (map println)))
  (println "---"))








