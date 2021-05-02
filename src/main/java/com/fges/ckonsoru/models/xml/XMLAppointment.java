package com.fges.ckonsoru.models.xml;

import com.fges.ckonsoru.models.Appointment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XMLAppointment extends Appointment implements XMLFriendly {
    /**
     * String representation of of the collection name from appointments
     */
    static protected final String collectionName = "rdvs";

    /**
     * Create an XML Appointment as you would create a {@link Appointment}
     * @param beginDateTime datetime of the appointment
     * @param clientName name of the client
     * @param veterinaryName name of the veterinary
     */
    public XMLAppointment(LocalDateTime beginDateTime, String clientName, String veterinaryName) {
        super(beginDateTime, clientName, veterinaryName);
    }

    /**
     * Create an XMLAppointment from an Appointment
     * @param appointment appointment to cast
     */
    public XMLAppointment(Appointment appointment) {
        super(appointment.getBeginDateTime(), appointment.getClientName(), appointment.getVeterinaryName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element toXMLElement(Document doc) {
        Element a = doc.createElement("rdv");

        // beginDateTime
        Element beginDateTimeNode = doc.createElement("debut");
        String beginDateTimeString = this.beginDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        beginDateTimeNode.appendChild(doc.createTextNode(beginDateTimeString));
        a.appendChild(beginDateTimeNode);

        // client
        Element clientNode = doc.createElement("client");
        clientNode.appendChild(doc.createTextNode(this.clientName));
        a.appendChild(clientNode);

        // veterinary
        Element docNode = doc.createElement("veterinaire");
        docNode.appendChild(doc.createTextNode(this.veterinaryName));
        a.appendChild(docNode);

        return a;
    }

    /**
     * Create a XMLAppointment from an xml element
     * @param element element to parse
     * @return the created XMLAppointment object
     */
    public static XMLAppointment fromXMLElement(Element element) {
        String sDebut =  element.getElementsByTagName("debut").item(0).getTextContent();
        String clientName = element.getElementsByTagName("client").item(0).getTextContent();
        String vetoName = element.getElementsByTagName("veterinaire").item(0).getTextContent();

        LocalDateTime begin = LocalDateTime.parse(sDebut);

        return new XMLAppointment(begin, clientName, vetoName);
    }
}
