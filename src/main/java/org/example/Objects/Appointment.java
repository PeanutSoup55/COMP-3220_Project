package org.example.Objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Appointment {

    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("h:mm a");

    private final int id;
    private final Patient patient;
    private final Doctor doctor;
    private final LocalDateTime date;
    private final String location;
    private AppointmentStatus status;
    private boolean reminderCall;

    public Appointment(int id, Patient patient, Doctor doctor, LocalDateTime date, String location,
                       AppointmentStatus status, boolean reminderCall) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.location = location;
        this.status = status;
        this.reminderCall = reminderCall;
    }

    public void markNoShowUnfinished() {
        if (status == AppointmentStatus.SCHEDULED || status == AppointmentStatus.LATE) {
            status = AppointmentStatus.NO_SHOW_UNFINISHED;
            reminderCall = false;
        }
    }

    public String markLate() {
        if (status != AppointmentStatus.SCHEDULED) {
            return "";
        }
        status = AppointmentStatus.LATE;
        reminderCall = true;
        return alertMessage();
    }

    public String alertMessage() {
        return "ALERT: " + patient.getName() + " is late for the " + date.format(TIME) + " appointment with Dr. " + doctor.getName() + ". Call " + patient.getPhone() + " to remind them.";
    }

    public boolean cancel(LocalDateTime cancelDate) {
        if (status != AppointmentStatus.SCHEDULED || !date.isAfter(cancelDate)) {
            return false;
        }
        status = AppointmentStatus.CANCELLED;
        reminderCall = false;
        return true;
    }

    public int getId() { return id; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDateTime getDate() { return date; }
    public String getLocation() { return location; }
    public AppointmentStatus getStatus() { return status; }
    public boolean isReminderCall() { return reminderCall; }

}
