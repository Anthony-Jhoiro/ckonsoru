package com.fges.ckonsoru.data.xml;

import com.fges.ckonsoru.data.TimeslotDAO;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.Availability;
import com.fges.ckonsoru.models.Timeslot;
import com.fges.ckonsoru.models.xml.XMLAppointment;
import com.fges.ckonsoru.models.xml.XMLAvailability;
import org.w3c.dom.Element;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class XMLTimeslotDAO implements TimeslotDAO {
    /**
     * XML adapter used to interact with the XML database
     */
    protected final XMLAdapterSingleton adapter;

    /**
     * Create an XMLAvailabilityRepository
     */
    public XMLTimeslotDAO(){
        this.adapter = XMLAdapterSingleton.getInstance();
    }

    public Collection<Availability> getAvailabilityByDay(DayOfWeek day) {
        String dayInFrench = day.getDisplayName(TextStyle.FULL, Locale.FRANCE);

        String xpath = "/ckonsoru/disponibilites/disponibilite[jour='"+dayInFrench+"']";

        List<Availability> availabilities = new ArrayList<>();
        List<Element> elements = this.adapter.find(xpath);

        for (Element element: elements) {
            Availability availability = XMLAvailability.fromXMLElement(element);
            availabilities.add(availability);
        }
        return availabilities;
    }

    public Collection<Appointment> getAllAppointmentsByDate(LocalDate date){

        List<Appointment> appointments = new ArrayList<>();

        String sdate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String xpath = "/ckonsoru/rdvs/rdv[starts-with(debut,'"+sdate+"')]";

        List<Element> nodes = this.adapter.find(xpath);

        for(Element element: nodes) {
            Appointment a = XMLAppointment.fromXMLElement(element);
            appointments.add(a);
        }

        return appointments;
    }

    @Override
    public Collection<Timeslot> getFreeTimeslots(LocalDate date) throws Exception {

        ArrayList<Timeslot> timeslots =
                new ArrayList<Timeslot>();

        DayOfWeek day = date.getDayOfWeek();

        Collection<Availability> availabilities =
                this.getAvailabilityByDay(day);

        Collection<Appointment> appointments =
                this.getAllAppointmentsByDate(date);

        for (Availability availability: availabilities) {
            LocalTime endTime = availability.getEnd();
            for (LocalTime time = availability.getBegin(); time.isBefore(endTime); time = time.plusMinutes(20)){
                boolean shouldDisplay = true;
                for (Appointment appointment: appointments) {
                    if (
                            appointment.getBeginDateTime().toLocalTime().equals(time)
                                    && appointment.getVeterinaryName().equals(availability.getVeterinaryName())
                    ) {
                        shouldDisplay = false;
                        break;
                    }
                }
                if (shouldDisplay) {
                    timeslots.add(new Timeslot(LocalDateTime.of(date, time), availability.getVeterinaryName()));
                }
            }
        }

        return timeslots;
    }
}
