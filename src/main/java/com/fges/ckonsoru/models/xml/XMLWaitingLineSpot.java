package com.fges.ckonsoru.models.xml;

import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.WaitingLineSpot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class XMLWaitingLineSpot extends WaitingLineSpot implements XMLFriendly {
    public XMLWaitingLineSpot(String clientName, String numTel, LocalDate deadline, LocalDateTime requestedDate) {
        super(clientName, numTel, deadline, requestedDate);
    }

    public XMLWaitingLineSpot(String clientName, String numTel, LocalDate deadline, LocalDateTime proposedTimeslot, LocalDateTime requestedDate) {
        super(clientName, numTel, deadline, proposedTimeslot, requestedDate);
    }

    public XMLWaitingLineSpot(WaitingLineSpot waitingLineSpot) {
        super(waitingLineSpot.getClientName(), waitingLineSpot.getNumTel(), waitingLineSpot.getDeadline(), waitingLineSpot.getProposedTimeslot(), waitingLineSpot.getRequestedDate());
    }

    @Override
    public String getCollectionName() {
        return "fileattente";
    }

    @Override
    public Element toXMLElement(Document doc) {
        Element a = doc.createElement("place");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("");

        // user
        Element clientNode = doc.createElement("client");
        clientNode.appendChild(doc.createTextNode(this.getClientName()));
        a.appendChild(clientNode);

        // telephone
        Element phoneNode = doc.createElement("telephone");
        phoneNode.appendChild(doc.createTextNode(this.getClientName()));
        a.appendChild(phoneNode);

        // deadline
        Element deadLineNode = doc.createElement("deadline");
        String deadlineString = this.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        deadLineNode.appendChild(doc.createTextNode(deadlineString));
        a.appendChild(deadLineNode);

        // demande
        Element requestedDateNode = doc.createElement("demande");
        String requestedDateString = this.getRequestedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        requestedDateNode.appendChild(doc.createTextNode(requestedDateString));
        a.appendChild(requestedDateNode);

        // proposition
        Element proposeTimeslotNode = doc.createElement("proposition");
        String proposeTimeslotString = this.getProposedTimeslot().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        proposeTimeslotNode.appendChild(doc.createTextNode(proposeTimeslotString));
        a.appendChild(proposeTimeslotNode);

        return a;
    }
}
