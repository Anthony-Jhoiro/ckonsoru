package com.fges.ckonsoru.data.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// XML dependencies
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import com.fges.ckonsoru.models.xml.XMLFriendly;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;


/**
 * This class is an XML adapter to interact with the XML database
 */
public class XMLAdapterSingleton {
    /**
     * Document representing the database content
     */
    protected Document xmlData;

    /**
     * filename where the database is stored
     */
    protected static  String filename;

    public static void init(Properties props) {
        filename = props.getProperty("xml.fichier");
    }

    private static XMLAdapterSingleton instance;

    /**
     * Create a XMLAdapter from a filename.
     * Parse the content of the file to load the data from the XML database (see {@link this.openXML})
     */
    private XMLAdapterSingleton() {
        this.xmlData = this.openXML(filename);
    }

    public static XMLAdapterSingleton getInstance() {
        if (instance == null) {
            instance = new XMLAdapterSingleton();
        }
        return instance;
    }

    /**
     * Parse the content of the filename to be used as database content
     * @param filename path to the file to parse
     * @return the parsed document
     */
    protected Document openXML(String filename) {
        // Prepare XML builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder xmlbuilder;
        try{
            // Try loading the file and return it if it succeeds
            xmlbuilder = factory.newDocumentBuilder();
            return  xmlbuilder.parse(filename);
        }
        catch(IOException | ParserConfigurationException | SAXException e) {
            // Error parsing or opening the file
            System.err.println("Can not open database : " + filename);
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * Find elements in the XML database that corresponds to the given xpath query
     * @param xpathRequest query used to fetch data from the database
     * @return A list of elements representing the fetched data from the database
     */
    List<Element> find(String xpathRequest) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr =xpath.compile(xpathRequest);
            NodeList nodes = (NodeList) expr.evaluate(this.xmlData, XPathConstants.NODESET);

            List<Element> elements = new ArrayList<>();

            for(int i = 0; i < nodes.getLength(); i++){
                Node nNode = nodes.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    elements.add(eElement);
                }
            }
            return elements;

        } catch (XPathExpressionException e) {
            System.err.println("Can not parse XPATH [" + xpathRequest + "]");
            e.printStackTrace(System.err);
            return new ArrayList<>();
        }
    }

    /**
     * Insert the given object in the database
     * @param element an XMLFriendly element that needs to be inserted
     * @return true if the element as successfully been inserted
     */
    boolean insert(XMLFriendly element) {
        NodeList nodes = this.xmlData.getElementsByTagName(element.getCollectionName());
        nodes.item(0).appendChild(element.toXMLElement(xmlData));
        return save();
    }

    /**
     * Remove elements from the database from the given xpath
     * @param xpathRequest request used to find elements to remove
     * @return true if the operation succeeds
     */
    boolean remove(String xpathRequest) {
        try {

            XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(xpathRequest);
        NodeList nodes = (NodeList) expr.evaluate(this.xmlData, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            node.getParentNode().removeChild(node);
        }
            return save();
        } catch (XPathExpressionException e) {
            System.err.println("Can not remove element");
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * Save the content of the database in the file
     * @return true if the operation succeeds
     */
    boolean save() {
        DOMSource domSource = new DOMSource(xmlData);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult streamResult = new StreamResult(this.filename);
            transformer.transform(domSource, streamResult);
            return true;
        } catch (TransformerException e) {
            System.err.println("Can not save data");
            e.printStackTrace(System.err);
            return false;
        }
    }
}