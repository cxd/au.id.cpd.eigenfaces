//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.09 at 05:34:29 PM EST 
//


package au.id.cpd.eigenfaces.ui.xml;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.id.cpd.eigenfaces.ui.xml package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LabelOutputFile_QNAME = new QName("http://www.cpd.id.au/Model", "LabelOutputFile");
    private final static QName _SourceDirectory_QNAME = new QName("http://www.cpd.id.au/Model", "SourceDirectory");
    private final static QName _ImageOutputFile_QNAME = new QName("http://www.cpd.id.au/Model", "ImageOutputFile");
    private final static QName _VectorOutputFile_QNAME = new QName("http://www.cpd.id.au/Model", "VectorOutputFile");
    private final static QName _FeatureOutputFile_QNAME = new QName("http://www.cpd.id.au/Model", "FeatureOutputFile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.id.cpd.eigenfaces.ui.xml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cpd.id.au/Model", name = "LabelOutputFile")
    public JAXBElement<String> createLabelOutputFile(String value) {
        return new JAXBElement<String>(_LabelOutputFile_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cpd.id.au/Model", name = "SourceDirectory")
    public JAXBElement<String> createSourceDirectory(String value) {
        return new JAXBElement<String>(_SourceDirectory_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cpd.id.au/Model", name = "ImageOutputFile")
    public JAXBElement<String> createImageOutputFile(String value) {
        return new JAXBElement<String>(_ImageOutputFile_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cpd.id.au/Model", name = "VectorOutputFile")
    public JAXBElement<String> createVectorOutputFile(String value) {
        return new JAXBElement<String>(_VectorOutputFile_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cpd.id.au/Model", name = "FeatureOutputFile")
    public JAXBElement<String> createFeatureOutputFile(String value) {
        return new JAXBElement<String>(_FeatureOutputFile_QNAME, String.class, null, value);
    }

}
