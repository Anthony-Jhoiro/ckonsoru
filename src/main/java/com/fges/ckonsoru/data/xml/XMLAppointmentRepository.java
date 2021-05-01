package com.fges.ckonsoru.data.xml;

import com.fges.ckonsoru.data.AppointmentRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import javax.lang.model.element.Element;
// XML dependencies
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLAppointmentRepository extends AppointmentRepository {

    private String XMLFileName;

    public XMLAppointmentRepository(String fileName){
        this.XMLFileName = fileName;
    }

    public Collection<String> getAllAppointmentsByDate(LocalDate date){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder xmlbuilder;
        Document doc = null;
        String sdate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        try{
            // charger le fichier 
            xmlbuilder = factory.newDocumentBuilder();
            Document xmldoc = builder.parse(this.XMLFileName);
            // créer la requête XPATH
            String requeteXPATH = "/ckonsoru/rdvs/rdv[starts-with(debut,'"+sdate+"')]";
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr =xpath.compile(requeteXPATH);
            // evaluer la requête XPATH
            NodeList nodes = (NodeList) expr.evaluate(xmldoc, XPathConstants.NODESET);
            ArrayList<String> returnInfos;
            returnInfos = new ArrayList<String>();
            for(int i = 0; i < nodes.getLength(); i++){
                Node nNode = nodes.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                    String debut =  eElement.getElementsByTagName("debut").item(0).getTextContent();
                    String client = eElement.getElementsByTagName("client").item(0).getTextContent();
                    String veto = eElement.getElementsByTagName("veterinaire").item(0).getTextContent();
                    returnInfos.add("Rendez-vous pour le client " +client + " avec " + veto + " le " + debut);
                }
            }
            return returnInfos;
        }catch(IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
            System.err.println("Erreur à l'ouverture de la bdd xml :  " + this.XMLFileName);
            e.printStackTrace(System.err);
        }
    }

    public Boolean registerAppointment(Date date, String doctor, String client){
        return false;
    }

    public Boolean removeAppointment(Date date, String client){
        return false;
    }

}