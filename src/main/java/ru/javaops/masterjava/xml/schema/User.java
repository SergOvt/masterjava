
package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="flag" use="required" type="{http://javaops.ru}flagType" />
 *       &lt;attribute name="city" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="email" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fullName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "User", namespace = "http://javaops.ru")
public class User {

    @XmlAttribute(name = "flag", required = true)
    protected FlagType flag;
    @XmlAttribute(name = "city", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object city;
    @XmlAttribute(name = "email", required = true)
    protected String email;
    @XmlAttribute(name = "fullName", required = true)
    protected String fullName;

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link FlagType }
     *     
     */
    public FlagType getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagType }
     *     
     */
    public void setFlag(FlagType value) {
        this.flag = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCity(Object value) {
        this.city = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

}
