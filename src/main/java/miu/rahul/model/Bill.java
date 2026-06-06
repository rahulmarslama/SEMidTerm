package miu.rahul.model;

import java.time.LocalDate;

public class Bill {
    private Long id;
    private Patient patient;
    private double amount;
    private LocalDate serviceDate;
    private boolean isPaid;

    public Bill(Long id, Patient patient, double amount, LocalDate serviceDate, boolean isPaid) {
        this.id = id;
        this.patient = patient;
        this.amount = amount;
        this.serviceDate = serviceDate;
        this.isPaid = isPaid;
    }

    public Long getId() { return id; }
    public Patient getPatient() { return patient; }
    public double getAmount() { return amount; }
    public LocalDate getServiceDate() { return serviceDate; }
    public boolean isPaid() { return isPaid; }
}
