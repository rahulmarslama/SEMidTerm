package miu.rahul.model;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private LocalDateTime dateTime;
    private Dentist dentist;
    private Patient patient;
    private Surgery surgery;

    public Appointment(Long id, LocalDateTime dateTime, Dentist dentist, Patient patient, Surgery surgery) {
        this.id = id;
        this.dateTime = dateTime;
        this.dentist = dentist;
        this.patient = patient;
        this.surgery = surgery;
    }

    public Long getId() { return id; }
    public LocalDateTime getDateTime() { return dateTime; }
    public Dentist getDentist() { return dentist; }
    public Patient getPatient() { return patient; }
    public Surgery getSurgery() { return surgery; }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", dentist=" + (dentist != null ? dentist.getLastName() : "null") +
                ", patient=" + (patient != null ? patient.getLastName() : "null") +
                ", surgery=" + (surgery != null ? surgery.getName() : "null") +
                '}';
    }
}
