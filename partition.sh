partition_size=30000
for i in $(seq 0 30); do
    from=$(echo "($i * ${partition_size}) + 1" | bc -l)
    to=$(echo "($i * ${partition_size}) + ${partition_size}" | bc -l)
    xsltproc --param from ${from} --param to ${to} partition.xsl nlwiktionary-20200701-pages-articles.xml > subwiki-$i.xml
done


