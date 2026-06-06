package miu.rahul.model;

import java.time.LocalDate;
import java.time.Period;

public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String mailingAddress;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;

    public Patient(Long id, String firstName, String lastName, String mailingAddress, String phoneNumber, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailingAddress = mailingAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMailingAddress() { return mailingAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
