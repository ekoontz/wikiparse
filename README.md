# Introduction

Clojure library for retrieving wiktionary pages from wiktionary dumps.
Requires (for now) a [modified
version](https://github.com/ekoontz/data.xml/tree/upgraded-dependencies-with-woodstox)
of Clojure core's [`data.xml`](https://github.com/clojure/data.xml)
library that uses the
[Woodstox](https://github.com/FasterXML/woodstox) XML parsing library
rather than the built-in JVM-provided one, in order to handle wikimedia's large `text` sections.

# Setup

## Get wiktionary dump XML file

```
$ wget https://dumps.wikimedia.org/nlwiktionary/20200701/nlwiktionary-20200701-pages-articles.xml.bz2
$ bunzip2 nlwiktionary-20200701-pages-articles.xml.bz2
```

## Install data.xml with woodstox support

```
git clone git@github.com:ekoontz/data.xml.git
cd data.xml
git checkout upgraded-dependencies-with-woodstox
lein install
```

# Demo

The following `(lookup)`s correspond to the following wiktionary pages:

- [hond (dog)](https://nl.wiktionary.org/wiki/hond)
- [kat (cat)](https://nl.wiktionary.org/wiki/kat)
- [jongen (boy)](https://nl.wiktionary.org/wiki/jongen)
- [meisje (girl)](https://nl.wiktionary.org/wiki/meisje)

```
$ lein repl
OpenJDK 64-Bit Server VM warning: Options -Xverify:none and -noverify were deprecated in JDK 13 and will likely be removed in a future release.
nREPL server started on port 61773 on host 127.0.0.1 - nrepl://127.0.0.1:61773
REPL-y 0.4.4, nREPL 0.6.0
Clojure 1.10.0
OpenJDK 64-Bit Server VM 14.0.1+7
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=> (load "test")
nil
user=> (in-ns 'wiktionary)
#object[clojure.lang.Namespace 0x694b8f32 "wiktionary"]
wiktionary=> (subs (lookup "hond") 0 30)
"[[Bestand:Rottweiler3.jpg|thum"
wiktionary=> (subs (lookup "kat") 0 30)
"{{=universeel=}}\n{{-etym-}}\n* "
wiktionary=> (subs (lookup "jongen") 0 30)
"[[Bestand:Albert Anker - Schul"
wiktionary=> (subs (lookup "meisje") 0 30)
"[[Bestand:Leon Fortunski Schle"
wiktionary=>
```
