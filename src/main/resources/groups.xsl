<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:param name="projectName"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Groups</title>
            </head>
            <body>
                <table>
                    <tr>
                        <xsl:apply-templates select="/*[name()='Payload']/*[name()='Projects']/*[name()='Project']/*[name()='Group']"/>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="/*[name()='Payload']/*[name()='Projects']/*[name()='Project']/*[name()='Group']">
        <xsl:if test="$projectName = ../@name">
            <td>
                <xsl:value-of select="@name"/>
            </td>
        </xsl:if>
    </xsl:template>
    <xsl:template match="text()"/>
</xsl:stylesheet>