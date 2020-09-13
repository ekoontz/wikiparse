<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:wiki="http://www.mediawiki.org/xml/export-0.10/">
  <xsl:output omit-xml-declaration="yes" indent="yes"/>
  <xsl:strip-space elements="*"/>

  <xsl:param name="from"/>
  <xsl:param name="to"/>
 
  <xsl:template match="/">
    <subwiki>
      <info>Showing pages from: <xsl:value-of select="$from"/> to: <xsl:value-of select="$to"/>.</info>
      <xsl:apply-templates
	  select="wiki:mediawiki/wiki:page[position() >= $from and not(position() > $to)]"/>
    </subwiki>
  </xsl:template>
    
  <xsl:template match="wiki:page">
    <xsl:copy-of select="."/>
  </xsl:template>

  
</xsl:stylesheet>
