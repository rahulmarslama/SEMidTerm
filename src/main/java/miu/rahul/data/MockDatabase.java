package miu.rahul.data;

import miu.rahul.model.Appointment;
import miu.rahul.model.Bill;
import miu.rahul.model.Dentist;
import miu.rahul.model.Patient;
import miu.rahul.model.Surgery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDatabase {
    public static List<Patient> patients = new ArrayList<>(Arrays.asList(
            new Patient(1L, "John", "Smith", "101 Maple St, Fairfield, IA", "(641) 001-1234", "john.smith@example.com", LocalDate.of(1987, 1, 19)),
            new Patient(2L, "Anna", "Jones", "202 Cedar Ave, Iowa City, IA", "(319) 716-1987", "anna.jones@example.com", LocalDate.of(2001, 7, 26)),
            new Patient(3L, "Carlos", "Jimenez", "303 Birch Rd, Cedar Rapids, IA", "(319) 098-7711", "carlos.jimenez@example.com", LocalDate.of(1969, 11, 5)),
            new Patient(4L, "Albert", "Einstein", "404 Oak Blvd, Fairfield, IA", "(641) 119-6142", "albert.einstein@example.com", LocalDate.of(1955, 12, 28))
    ));
    
    public static List<Dentist> dentists = new ArrayList<>(Arrays.asList(
            new Dentist(1L, "David", "Smith"),
            new Dentist(2L, "Sarah", "Connor"),
            new Dentist(3L, "Maria", "Garcia")
    ));
    
    public static List<Surgery> surgeries = new ArrayList<>(Arrays.asList(
            new Surgery(1L, "Fairfield Dental Surgery", "100 Main St, Fairfield, IA", "(641) 555-1000"),
            new Surgery(2L, "Iowa City Dental Care", "200 Market St, Iowa City, IA", "(319) 555-2000"),
            new Surgery(3L, "Cedar Rapids Smile Center", "300 River Dr, Cedar Rapids, IA", "(319) 555-3000")
    ));
    
    public static List<Appointment> appointments = new ArrayList<>(Arrays.asList(
            new Appointment(1L, LocalDateTime.of(2026, 2, 28, 10, 5), dentists.get(0), patients.get(0), surgeries.get(0)),
            new Appointment(2L, LocalDateTime.of(2025, 12, 31, 13, 45), dentists.get(1), patients.get(1), surgeries.get(1)),
            new Appointment(3L, LocalDateTime.of(2027, 5, 4, 14, 0), dentists.get(2), patients.get(2), surgeries.get(2)),
            new Appointment(4L, LocalDateTime.of(2026, 9, 16, 11, 15), dentists.get(0), patients.get(3), surgeries.get(0))
    ));
    
    public static List<Bill> bills = new ArrayList<>(Arrays.asList(
            new Bill(1L, patients.get(0), 125.00, LocalDate.of(2025, 4, 20), true),
            new Bill(2L, patients.get(1), 250.00, LocalDate.of(2024, 12, 15), false),
            new Bill(3L, patients.get(2), 175.50, LocalDate.of(2026, 1, 10), false),
            new Bill(4L, patients.get(3), 300.00, LocalDate.of(2025, 8, 5), true)
    ));
    
    public static long appointmentIdCounter = 5;
}
