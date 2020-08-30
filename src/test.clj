(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]))

(def small (with-open [input (java.io.FileInputStream. "small.xml")]
             (parse input :input-buffer-length 16384)))

(def tenk (with-open [input (java.io.FileInputStream. "10000.xml")]
            (parse input)))

(def wholething (with-open
                  [input (java.io.FileInputStream.
                          "nlwiktionary-20200701-pages-articles.xml")]
                  (parse input :input-buffer-length (int (Math/pow 2 27)))))

(def one-page
  (nth (->> (-> wholething :content)
            (filter #(= (type %) clojure.data.xml.node.Element))
            (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page)))
       0))

(def elements
  (->> (-> small :content)
       (filter #(= (type %) clojure.data.xml.node.Element))))

(def a-few-pages
  (->> (-> small :content)
       (filter #(= (type %) clojure.data.xml.node.Element))
       (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/page))))

(def a-few-titles
  (map page-title a-few-pages))

(def all-pages
  (->> (-> wholething :content)
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

(def fifty-titles (take 50 (map page-title all-pages)))

(defn count-up-the-pages []
  (count all-pages))

(defn print-all-the-titles []
  (doall
   (map println
        (sort (map page-title all-pages)))))

