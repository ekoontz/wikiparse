![Clojars Project](https://img.shields.io/clojars/v/org.clojars.ekoontz/wikiparse.svg)

# Introduction

Clojure library for retrieving wiktionary pages from wiktionary dumps.
Requires (for now) a [modified
version](https://github.com/ekoontz/data.xml/tree/upgraded-dependencies-with-woodstox)
of Clojure core's [`data.xml`](https://github.com/clojure/data.xml)
library that uses the
[Woodstox](https://github.com/FasterXML/woodstox) XML parsing library
rather than the built-in JVM-provided one, in order to handle wikimedia's large `text` sections.

## Related work

- [wiktionary: get word definitions from wikitionary.org](https://github.com/leonardiwagner/wiktionary)

# Setup

## Get wiktionary dump XML file

```
$ wget https://dumps.wikimedia.org/nlwiktionary/20200701/nlwiktionary-20200701-pages-articles.xml.bz2
$ bunzip2 nlwiktionary-20200701-pages-articles.xml.bz2
```

## Install data.xml with woodstox support

```
$ git clone git@github.com:ekoontz/data.xml.git
$ cd data.xml
$ git checkout upgraded-dependencies-with-woodstox
$ lein install
```

# Demo

The following `(lookup)`s correspond to the following wiktionary pages:

- [hond (dog)](https://nl.wiktionary.org/wiki/hond)
- [kat (cat)](https://nl.wiktionary.org/wiki/kat)
- [jongen (boy)](https://nl.wiktionary.org/wiki/jongen)
- [meisje (girl)](https://nl.wiktionary.org/wiki/meisje)


Wikiparse retrieves the underlying wikitionary source code entries,
which is what wiktionary authors use to create the entries, rather
than the HTML-formatted output that end-users read on their web
browsers in the links above. You can see the wikitionary source by
clicking on the "Bewerken" (edit) tab on the above wikitionary
pages. For example, you can see how [the wikitionary source
code](https://nl.wiktionary.org/w/index.php?title=hond&action=edit)
differs from the end-user HTML for
['hond'](https://nl.wiktionary.org/wiki/hond).

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

user=> (load "core")
nil
user=> (in-ns 'wikiparse)
#object[clojure.lang.Namespace 0x694b8f32 "wikiparse"]
wikiparse=> (subs (lookup "hond") 0 30)
"[[Bestand:Rottweiler3.jpg|thum"
wikiparse=> (subs (lookup "kat") 0 30)
"{{=universeel=}}\n{{-etym-}}\n* "
wikiparse=> (subs (lookup "jongen") 0 30)
"[[Bestand:Albert Anker - Schul"
wikiparse=> (subs (lookup "meisje") 0 30)
"[[Bestand:Leon Fortunski Schle"
```

This function `(demo)`, shows the first 20 lines of the wikisource for two pages, 'hond' and 'kat':

```
wikiparse=> (demo)
[[Bestand:Rottweiler3.jpg|thumb|right|200px|Een hond]]
{{=nld=}}
{{-pron-}}
*{{sound}}: {{audio|nl-{{pn}}.ogg|{{pn}}|nld}}
*{{WikiW|IPA}}:
**{{pron-reg|N=a}} {{IPA|/ˈɦɔnt/|nld}}
**{{pron-reg|V=a}} {{IPA|/ˈɦɔnt/|nld}}
**{{pron-reg|L=a}} {{IPA|/ˈhɔnd/|nld}}
{{-syll-}}
*hond
{{-etym-}}
* In de betekenis van ‘hondachtige’ voor het eerst aangetroffen in het jaar 901-1000 {{sijs}} {{ebank|hond1}}
* In de betekenis van ‘landmaat van 100 roeden’ voor het eerst aangetroffen in het jaar 1130-1161 {{sijs}} {{ebank|hond2}}
*{{((}}
*afkomstig van:
:{{dum}}: hont
:{{odt}}: hunt
:{{gem}}: *hundaz
{{=}}
*Verwant in Germaans:
---
{{=universeel=}}
{{-etym-}}
* Afgeleid van [[katal]]
{{-symbool-}}
'''{{pn}}'''
#{{natuurkunde|geentaal}}, {{eenheid|geentaal}} het symbool voor [[katal]], een [[eenheid]] voor [[katalytische activiteit]]
{{-rel-}}
{{eenheden-katal}}

{{=nld=}}
{{-pron-}}
*{{sound}}: {{audio|nl-{{pn}}.ogg|{{pn}}|nld}}
*{{WikiW|IPA}}: {{IPA-nl-standaard|kɑt}}
{{-syll-}}
*kat
{{-etym-}}
* Leenwoord uit het Latijn, in de betekenis van ‘katachtige’ voor het eerst aangetroffen in het jaar 1210 {{sijs}}
* In de betekenis van ‘standje’ voor het eerst aangetroffen in het jaar 1976 {{sijs}}
{{-nlnoun-|kat|[[katten]]|[[katje]]|[[katjes]]}}
{{-noun-|nld}}
---
nil
wikiparse=>
```
