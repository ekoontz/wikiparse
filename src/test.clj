(ns wiktionary
  (:require [clojure.data.xml :as xml :refer [parse]]))

(def small (with-open [input (java.io.FileInputStream. "small.xml")]
             (parse input :input-buffer-length 16384)))

(def tenk (with-open [input (java.io.FileInputStream. "10000.xml")]
            (parse input)))

(def wholething (with-open
                  [input (java.io.FileInputStream.
                          "nlwiktionary-20200701-pages-articles.xml")]
                  (parse input :input-buffer-length (int (Math/pow 2 28)))))

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

(def a-few-titles
  (map page-title a-few-pages))

(defn get-revision [page]
  )

(def fifty-titles (take 50 (map page-title all-pages)))

(defn count-up-the-pages []
  (count all-pages))

(defn print-all-the-titles []
  (doall
   (map println
        (sort (map page-title all-pages)))))


(def uitspraak
  (->> all-pages
       (take 100)
       (filter #(= (page-title %) "uitspraak"))
       (map :content)
       first))

(def revision-content
  (->> uitspraak
       (filter #(= clojure.data.xml.node.Element (type %)))
       (filter #(= :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/revision (:tag %)))
       (map :content)
       first))

(def actual-content
  (-> (->> revision-content
           (filter #(= (type %) clojure.data.xml.node.Element))
           (filter #(= (:tag %) :xmlns.http%3A%2F%2Fwww.mediawiki.org%2Fxml%2Fexport-0.10%2F/text)))
      first :content first))

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








