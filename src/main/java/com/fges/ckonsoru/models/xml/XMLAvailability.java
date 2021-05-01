package com.fges.ckonsoru.models.xml;

import com.fges.ckonsoru.models.Availability;
import org.w3c.dom.Element;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class XMLAvailability extends Availability {
    public XMLAvailability(LocalTime begin, LocalTime end, String veterinaryName) {
        super(begin, end, veterinaryName);
    }

    /**
     * Create a XMLAvailability from an xml element
     * @param element element to parse
     * @return the created XMLAvailability object
     */
    public static XMLAvailability fromXMLElement(Element element) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:m");
        String beginStr = element.getElementsByTagName("debut").item(0).getTextContent();
        String endStr = element.getElementsByTagName("fin").item(0).getTextContent();
        String veterinaryName = element.getElementsByTagName("veterinaire").item(0).getTextContent();

        LocalTime startTime = LocalTime.parse(beginStr, dateTimeFormatter);
        LocalTime endTime = LocalTime.parse(endStr, dateTimeFormatter);

        return new XMLAvailability(startTime, endTime, veterinaryName);
    }
}
