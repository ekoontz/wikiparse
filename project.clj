(defproject wiktionary "0.0.1-SNAPSHOT"
  :description "Parse wiktionary dumps and lookup pages within them"
  :url "https://github.com/ekoontz/wiktionary"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [log4j/log4j "1.2.17"]
                 [org.clojure/tools.logging "1.1.0"]
                 [org.clojure/data.xml "0-UE-DEVELOPMENT"]]
  :jvm-opts ["-Xmx6g"])
