(ns wikiparse
  (:require [clojure.data.xml :as xml :refer [parse]]
            [clojure.string :refer [split]]))

(def all-pages
 (with-open
   [input (java.io.FileInputStream.
           (cond
             true
             "subwiki-1-30000.xml"
             :else
             "nlwiktionary-20200701-pages-articles.xml"))]
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
        (filter #(do
                   (println (str "looking at title: " (page-title %)))
                   (= (page-title %) canonical-form)))
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

(def num-pages (atom 0))

(defn lookup-short [canonical-form]
  (->
   (->> all-pages
        (filter #(do
                   (println (str "looking at title #" @num-pages ": " (page-title %)))
                   (swap! num-pages inc)
                   (= (page-title %) canonical-form)))
        first)))

(defn split-lines [wiki-content]
  (split wiki-content #"\n"))

(defn demo []
  (println "--- hond ---")
  (println "")
  (type (->> (-> (lookup-short "hond") println)))
  (println "")
  (println "--- kat ---")
  (println "")  
  (type (->> (-> (lookup-short "kat") println)))
  (println "")
  (println "--- kind ---")
  (println "")  
  (count (->> (-> (lookup-short "kind") println)))
  (println "--- podá ---")
  (println "")  
  (count (->> (-> (lookup-short "podá") println))))











