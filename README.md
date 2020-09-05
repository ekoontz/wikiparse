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

# Wiktionary source code

Wiktionary entries are formatted in a line-oriented template markup
language that is more similar to Markdown than to a tag-oriented
language like XML or HTML. The former, line-oriented template language
is what wiktionary authors use to create the entries, which are then
formatted by wikimedia web server software to produce the
HTML-formatted output that end-users read on their web
browser. Similarly, these wiktionary source code entries are embedded
as text sections within XML dump files, bzipped and made available on
[dumps.wikimedia.org](https://dumps.wikimedia.org) as on the link
shown above. 

Wikiparse's `(lookup)` function retrieves these underlying wikitionary
source code entries nested as text within the XML dump file's tags. You can
see the wikitionary source by clicking on the "Bewerken" (edit) tab on
a wiktionary page. For example, you can see how [the wikitionary
source code for
'hond'](https://nl.wiktionary.org/w/index.php?title=hond&action=edit)
differs from [the end-user HTML for
'hond'](https://nl.wiktionary.org/wiki/hond).

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

This function `(demo)`, shows the first 20 lines of the wikisource for three words: 'hond', 'kat', and 'kind':

```
wikiparse=> (demo)
--- hond ---

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
:'''West''': {{eng}}: [[hound]] ({{ang}}: hund), {{deu}}: [[Hund]], ({{goh}}: hunt), {{fry}}: [[hûn]], [[huund]] ([[Oudfries]]: hund)
:'''Noord''': {{swe}}/{{dan}}/{{nor}}: [[hund]], ({{non}}: hundr), {{isl}}/{{fao}}: [[hundur]]
:'''Oost''': {{got}}: [[hunds]]
{{=}}
*Verwant in Romaans:
*{{fra}}: [[chien]]
*{{ita}}: [[cane]]
*{{lat}}: [[canis]]
{{=}}
* Verwant in andere talen:
*{{ell}}: [[κύων]]
{{))}}
{{-nlnoun-|hond|[[honden]]|[[hondje]]|[[hondjes]]}}
{{-noun-|nld}}
'''{{pn}}''' {{m}}
#{{dierkunde|nld}}, {{zoogdieren|nld}} {{species|Canis lupus familiaris}}, een zoogdier dat tot huisdier getemd is
{{bijv-1|Een '''hond''' moet regelmatig uitgelaten worden.}}
{{-hyper-}}
*[[carnivoor]]
*[[huisdier]]

--- kat ---

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
'''{{pn}}''' {{f}}/{{m}}
#{{zoogdieren|nld}} {{species|Felis sylvestris catus}}, tot de katachtigen behorende soort die tam is geworden
#{{zoogdieren|nld}} gebruikt als naam voor een geslacht van vleesetende dieren, zoals de tijgers en leeuwen
#{{informeel|nld}} een grote pluizige windprotectiehoes voor microfoons
#een vals meisje
#bitse terechtwijzing
{{-homo-}}
*[[qat]]
{{-syn-}}
*[1]: [[poes]]
*[1]: [[dakhaas]]
*[3]: [[korf]]
*[5]: [[snauw]]
{{-rel-}}
*[1]: [[kater]]
*[1]: [[kattin]]
*[1]: [[siamees]]
{{-ana-}}
*[[kta]], [[tak]], [[TAK]], [[t.k.a.]]
{{-hypo-}}

--- kind ---

{{zie-ook|Kind}}
{{=nld=}}
{{-pron-}}
*{{sound}}: {{audio|nl-{{pn}}.ogg|{{pn}}|nld}}
*{{WikiW|IPA}}: {{IPA-nl-standaard|kɪnt}}
{{-syll-}}
*kind
{{-etym-}}
* In de betekenis van ‘jong mens, zoon of dochter’ voor het eerst aangetroffen in 901 {{sijs}}
*{{((}}
*afkomstig van:
:{{dum}}: kint
:{{odt}}: kint
:{{gem}}: *kindan
:{{ine}}: *ǵenh₁tóm
{{=}}
*Verwant in Germaans:
: {{deu}}: [[Kind]]
: ({{goh}}: [[chind]]
* {{fry}}: [[kyn]] ([[Oudfries]]: kind)
{{=}}
*Verwant in Romaans:
: {{lat}}: [[gens#Latijn|gens]] (mens)
: {{fra}}: [[gens#Frans|gens]] (mensen)
: {{ita}}: [[gente]] (mensen)
{{))}}
{{-nlnoun-|{{pn}}|[[{{pn}}eren]],<br>[[{{pn}}ers]]|[[{{pn}}je]]|[[{{pn}}jes]],<br>[[{{pn}}ertjes]]|bezield=persoon|meta=fysiek}}
{{-noun-|nld}}
'''{{pn}}''' {{n}}
#[[mens]] tussen 0 en 18 [[jaar]]
{{citeer|boek|jaar=1973|auteur=Marijke van Raephorst |auteurlink=Marijke van Raephorst|titel=Het hele jaar rond: van Sinterklaas tot Sintemaarten|isbn=|pagina=10|uitgever=Lemniscaat|taal=nl|citaat=Van lieverlede werd hij echter beschouwd als de 'vriend der '''kinderen''''. In Nederland leest men over het St. Nicolaasfeest voor het eerst in het jaar 1360. De koorknaapjes in Dordrecht kregen er vrij voor. In optocht trokken zij door de stad en bedelden, met een smekend gebaar, hun bisschopsgeld bij elkaar. Maar in de zeventiende eeuw werd dit verboden!}}
{{citeer|artikel|datum=09 aug. 2018|auteur=MARIEKE ’T HART|titel=Lang leve de Franse opvoeding!|url=https://www.telegraaf.nl/vrouw/2409552/lang-leve-de-franse-opvoeding|uitgever=De Telegraaf|taal=nl|citaat=Franse '''kinderen''' schreeuwen niet<br/>Terwijl Nederlandse moeders over het strand schallen: 'Kevin, hiieeerrr kooomeeen…’, praten Franse moeders alleen op gedempte toon met hun '''kinderen'''. Sterker nog; ik heb een heel gezin naast ons een hele dag lang alleen op fluistertoon met elkaar horen praten. Niemand viel uit zijn of haar rol. Heerlijk rustig. Waarom moeten wij eigenlijk altijd zo tetteren?}}
#persoon voortkomend uit, [[zoon]] of [[dochter]]
{{bijv-1|Zij laat haar '''{{pn}}''' bij de oppas achter.}}
{{-syn-}}
*[1] [[minderjarige#Zelfstandig naamwoord|minderjarige]]
{{-rel-}}
*[1] [[jongere]] (mens tussen 14 en 25 jaar)
*[2] [[afstammeling]], [[nakomeling]], [[nazaat]], [[telg]]
{{-hypo-}}
40
wikiparse=>
```
