//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.23 at 01:44:42 PM BRT 
//


package org.softlang.company;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.softlang.org/company.xsd}name"/>
 *         &lt;element name="manager" type="{http://www.softlang.org/company.xsd}employee"/>
 *         &lt;element ref="{http://www.softlang.org/company.xsd}subunit" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "manager",
    "subunit"
})
@XmlRootElement(name = "department")
public class Department {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected Employee manager;
    protected List<Subunit> subunit;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the manager property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * Sets the value of the manager property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setManager(Employee value) {
        this.manager = value;
    }

    /**
     * Gets the value of the subunit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subunit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubunit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Subunit }
     * 
     * 
     */
    public List<Subunit> getSubunit() {
        if (subunit == null) {
            subunit = new ArrayList<Subunit>();
        }
        return this.subunit;
    }

}
