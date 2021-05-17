package com.fges.ckonsoru.data.xml;

import com.fges.ckonsoru.data.WaitingLineDAO;
import com.fges.ckonsoru.models.WaitingLineSpot;
import com.fges.ckonsoru.models.xml.XMLAppointment;
import com.fges.ckonsoru.models.xml.XMLWaitingLineSpot;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XMLWaitingLineDAO implements WaitingLineDAO {

    /**
     * XML adapter used to interact with the X%ML database
     */
    protected final XMLAdapterSingleton adapter;

    /**
     * Create an XMLAppointmentRepository
     */
    public XMLWaitingLineDAO(){
        this.adapter = XMLAdapterSingleton.getInstance();
    }


    @Override
    public boolean addToWaitingLine(WaitingLineSpot waitingLineSpot) {
        XMLWaitingLineSpot xmlWaitingLineSpot = new XMLWaitingLineSpot(waitingLineSpot);
        return this.adapter.insert(xmlWaitingLineSpot);
    }

    @Override
    public Collection<WaitingLineSpot> getWaitingLineSpots() {
        List<WaitingLineSpot> spots = new ArrayList<>();

        String query = "/fileattente/place";
        List<Element> elements = this.adapter.find(query);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");

        for (Element element: elements) {
            String clientName = element.getElementsByTagName("client").item(0).getTextContent();
            String phoneNumber = element.getElementsByTagName("telephone").item(0).getTextContent();
            String strDeadLine = element.getElementsByTagName("deadline").item(0).getTextContent();
            String strRequestedDate = element.getElementsByTagName("demande").item(0).getTextContent();

            LocalDate deadline = LocalDate.parse(strDeadLine, dateTimeFormatter);
            LocalDateTime requestedDate = LocalDateTime.parse(strRequestedDate, dateTimeFormatter);

            Node propose = element.getElementsByTagName("proposition").item(0);

            WaitingLineSpot spot;

            if (propose != null) {
                String strPropose = propose.getTextContent();
                LocalDateTime proposeDateTime = LocalDateTime.parse(strPropose, dateTimeFormatter);
                spot = new WaitingLineSpot(clientName, phoneNumber, deadline, proposeDateTime, requestedDate);
            } else {
                spot = new WaitingLineSpot(clientName, phoneNumber, deadline, requestedDate);
            }

            spots.add(spot);
        }

        return spots;
    }

    @Override
    public boolean updateWaitingLine(LocalDateTime localDateTime) {
        return false;
    }
}
