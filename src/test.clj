(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]))

(def wholething (with-open
                  [input (java.io.FileInputStream.
                          "nlwiktionary-20200701-pages-articles.xml")]
                  (parse input :input-buffer-length (int (Math/pow 2 28)))))

(def all-pages
  (->> (-> wholething :content)
       (filter #(= (type %) clojure.data.xml.node.Element))
       (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page))))

(defn lookup [canonical-form]
  (let [page
        (->> all-pages
             (take 100000)
             (filter #(= (page-title %) canonical-form))
             (map :content)
             first)]
    (->
     (->> page
          (filter #(= clojure.data.xml.node.Element (type %)))
          (filter #(= :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/revision (:tag %)))
          (mapcat :content)
          (filter #(= (type %) clojure.data.xml.node.Element))
          (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/text)))
     first
     :content
     first)))








