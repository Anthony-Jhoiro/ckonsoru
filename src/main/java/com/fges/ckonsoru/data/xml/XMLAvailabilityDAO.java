package com.fges.ckonsoru.data.xml;

import com.fges.ckonsoru.data.AvailabilityDAO;
import com.fges.ckonsoru.models.Availability;
import com.fges.ckonsoru.models.xml.XMLAvailability;
import org.w3c.dom.Element;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Represents an AvailabilityRepository working with an XML database (see {@link AvailabilityDAO})
 */
public class XMLAvailabilityDAO implements AvailabilityDAO {
    /**
     * XML adapter used to interact with the X%ML database
     */
    protected final XMLAdapter adapter;

    /**
     * Create an XMLAvailabilityRepository
     * @param adapter adapter used to interact with the XML database
     */
    public XMLAvailabilityDAO(XMLAdapter adapter){
        this.adapter = adapter;
    }


    /**
     * Fetch the database for availabilities corresponding to the given xpath
     * @param xpathQuery xpath that defines to data to fetch
     * @return the fetched availabilities
     */
    public List<Availability> findAvailabilities(String xpathQuery) {
        List<Availability> availabilities = new ArrayList<>();
        List<Element> elements = this.adapter.find(xpathQuery);

        for (Element element: elements) {
            Availability availability = XMLAvailability.fromXMLElement(element);
            availabilities.add(availability);
        }
        return availabilities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Availability> getAvailabilityByDay(DayOfWeek day) {
        String dayInFrench = day.getDisplayName(TextStyle.FULL, Locale.FRANCE);

        String xpath = "/ckonsoru/disponibilites/disponibilite[jour='"+dayInFrench+"']";

        return this.findAvailabilities(xpath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAvailable(LocalDateTime datetime, String veterinaryName) {

        String dayInFrench = datetime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);

        String xpath = "/ckonsoru/disponibilites/disponibilite[veterinaire='"+ veterinaryName +"' and jour='"+dayInFrench+"']";

        List<Availability> availabilities = this.findAvailabilities(xpath);

        for (Availability availability: availabilities) {
            if (availability.getBegin().isBefore(datetime.toLocalTime()) &&
                    availability.getEnd().isAfter(datetime.toLocalTime())) {
                return true;
            }
        }
        return false;
    }
}
