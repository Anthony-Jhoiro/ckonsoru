package com.fges.ckonsoru.data.xml;

import com.fges.ckonsoru.data.AppointmentDAO;
import com.fges.ckonsoru.models.Appointment;
import com.fges.ckonsoru.models.xml.XMLAppointment;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents an XMLAppointmentDAO working with an XML database (see {@link AppointmentDAO})
 */
public class XMLAppointmentDAO implements AppointmentDAO {

    /**
     * XML adapter used to interact with the X%ML database
     */
    protected final XMLAdapterSingleton adapter;

    /**
     * Create an XMLAppointmentRepository
     */
    public XMLAppointmentDAO(){
        this.adapter = XMLAdapterSingleton.getInstance();
    }


    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Appointment> getAllAppointmentsByClient(String clientName){

        List<Appointment> appointments = new ArrayList<>();

        String xpath = "/ckonsoru/rdvs/rdv[starts-with(client,'"+clientName+"')]";

        List<Element> nodes = this.adapter.find(xpath);

        for(Element element: nodes) {
            Appointment a = XMLAppointment.fromXMLElement(element);
            appointments.add(a);
        }

        return appointments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFree(LocalDateTime datetime, String doctorName) {

        String sdate = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String xpath = "/ckonsoru/rdvs/rdv[veterinaire='"+doctorName+"' and debut='"+sdate+"']";

        return this.adapter.find(xpath).isEmpty();
    }

    @Override
    public Appointment getAppointmentByClientNameAndDate(String clientName, LocalDateTime date) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean registerAppointment(Appointment appointment) {
        XMLAppointment xmlAppointment = new XMLAppointment(appointment);
        return this.adapter.insert(xmlAppointment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAppointment(LocalDateTime datetime, String clientName) {
        String sdate = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String xpath = "/ckonsoru/rdvs/rdv[client='"+clientName+"' and debut='"+sdate+"']";

        return this.adapter.remove(xpath);
    }
}