package org.example.Objects;

import java.time.LocalDateTime;
import java.util.*;

public class Doctor extends User{
    private int docID;
    private String field;
    private int emrgphone;

    public Doctor(String name, String email, String password, String field, int phone, int emrgphone, int docID) {
        super(name, email, password, phone);
        this.field = field;
        this.emrgphone = emrgphone;
        this.docID = docID;
    }

    public List<Patient> getTreatedPatients(List<Appointment> allAppointments, LocalDateTime now) {
        Map<Integer, Patient> seen = new LinkedHashMap<>();
        allAppointments.stream()
                .filter(a -> a.getDoctor().getDocID() == docID)
                .filter(a -> a.getDate().isBefore(now))
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED || a.getStatus() == AppointmentStatus.LATE)
                .sorted(Comparator.comparing(Appointment::getDate).reversed())
                .forEach(a -> seen.putIfAbsent(a.getPatient().getPatientID(), a.getPatient()));
        return new ArrayList<>(seen.values());
    }

    public List<Appointment> getUpcomingAppointments(List<Appointment> allAppointments, LocalDateTime now) {
        return allAppointments.stream()
                .filter(a -> a.getDoctor().getDocID() == docID)
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED)
                .filter(a -> a.getDate().isAfter(now))
                .sorted(Comparator.comparing(Appointment::getDate))
                .toList();
    }

    public int getDocID() {
        return docID;
    }

    public String getField() {
        return field;
    }

    public int getEmrgphone() {
        return emrgphone;
    }
}
