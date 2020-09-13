partition_size=30000
for i in $(seq 0 31); do
    from=$(echo "($i * ${partition_size}) + 1" | bc -l)
    to=$(echo "($i * ${partition_size}) + ${partition_size}" | bc -l)
    xsltproc --param from ${from} --param to ${to} one-thousand.xsl nlwiktionary-20200701-pages-articles.xml > subwiki-$from-$to.xml
done


