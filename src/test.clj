(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]))

(def small (with-open [input (java.io.FileInputStream. "small-notext.xml")]
             (parse input)))

(def tenk (with-open [input (java.io.FileInputStream. "10000.xml")]
            (parse input)))

(def wholething (with-open
                  [input (java.io.FileInputStream.
                          "nlwiktionary-20200701-pages-articles.xml")]
                  (parse input)))

(def one-page
  (nth (->> (-> wholething :content)
            (filter #(= (type %) clojure.data.xml.node.Element))
            (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page)))
       0))

(def elements
  (->> (-> small :content)
       (filter #(= (type %) clojure.data.xml.node.Element))))

(def all-pages
  (->> (-> small :content)
       (filter #(= (type %) clojure.data.xml.node.Element))
       (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page))))

(defn all-page-elements [page]
  (->> (-> page :content)
       (filter #(= (type %) clojure.data.xml.node.Element))))

(defn page-title [page]
  (->
   (->> (-> page :content)
        (filter #(= (type %) clojure.data.xml.node.Element))
        (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/title)))
   first
   :content
   first))

(defn get-revision [page]
  )

(defn wtf []
  (with-open [input (java.io.FileInputStream. "small-notext.xml")]
    (parse input :include-node? #{:element})))

(defn wtf2 []
  (with-open [input (java.io.FileInputStream. "small.xml")]
    (parse input)))


