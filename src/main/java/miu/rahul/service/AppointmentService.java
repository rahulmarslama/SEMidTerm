package miu.rahul.service;

import miu.rahul.model.Appointment;
import miu.rahul.model.Bill;
import miu.rahul.model.Dentist;
import miu.rahul.model.Patient;
import miu.rahul.model.Surgery;
import miu.rahul.repository.AppointmentRepository;
import miu.rahul.repository.AppointmentRepositoryImpl;
import miu.rahul.utility.DateUtility;
import miu.rahul.utility.EmailUtility;
import miu.rahul.utility.JsonUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService() {
        this.appointmentRepository = AppointmentRepositoryImpl.getInstance();
    }

    public void bookAppointment(Long patientId, Long dentistId, Long surgeryId, LocalDateTime dateTime) {
        Optional<Patient> patientOpt = appointmentRepository.findPatientById(patientId);
        Optional<Dentist> dentistOpt = appointmentRepository.findDentistById(dentistId);
        Optional<Surgery> surgeryOpt = appointmentRepository.findSurgeryById(surgeryId);

        if (patientOpt.isEmpty() || dentistOpt.isEmpty() || surgeryOpt.isEmpty()) {
            System.out.println("Invalid patient, dentist, or surgery ID.");
            return;
        }

        Patient patient = patientOpt.get();
        Dentist dentist = dentistOpt.get();
        Surgery surgery = surgeryOpt.get();

        if (hasOverdueBill(patientId)) {
            System.out.println("Cannot book appointment: Patient has an unpaid bill older than 12 months.");
            return;
        }

        if (dentistHasReachedWeeklyLimit(dentistId, dateTime, null)) {
            System.out.println("Cannot book appointment: Dentist already has 5 appointments this week.");
            return;
        }

        Appointment appointment = new Appointment(null, dateTime, dentist, patient, surgery);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        System.out.println("Appointment booked successfully.");
        new EmailUtility().sendConfirmationEmail(savedAppointment);
    }

    public void cancelAppointment(Long appointmentId) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isPresent()) {
            appointmentRepository.delete(appointmentId);
            System.out.println("Appointment " + appointmentId + " cancelled.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public void changeAppointment(Long appointmentId, Long surgeryId, LocalDateTime newDateTime) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        Optional<Surgery> surgeryOpt = appointmentRepository.findSurgeryById(surgeryId);

        if (appointmentOpt.isEmpty() || surgeryOpt.isEmpty()) {
            System.out.println("Invalid appointment or surgery ID.");
            return;
        }

        Appointment existingAppointment = appointmentOpt.get();
        if (existingAppointment.getDentist() == null) {
            System.out.println("Cannot change appointment: No dentist is assigned to this appointment.");
            return;
        }

        Long dentistId = existingAppointment.getDentist().getId();

        if (dentistHasReachedWeeklyLimit(dentistId, newDateTime, appointmentId)) {
            System.out.println("Cannot change appointment: Dentist already has 5 appointments this week.");
            return;
        }

        Appointment changedAppointment = new Appointment(
                appointmentId,
                newDateTime,
                existingAppointment.getDentist(),
                existingAppointment.getPatient(),
                surgeryOpt.get()
        );

        Appointment savedAppointment = appointmentRepository.save(changedAppointment);
        System.out.println("Appointment " + appointmentId + " changed successfully.");
        new EmailUtility().sendConfirmationEmail(savedAppointment);
    }

    public void viewAppointmentsByDentist(Long dentistId) {
        List<Appointment> dentistAppts = appointmentRepository.findByDentistId(dentistId);

        System.out.println("--- Appointments for Dentist ID " + dentistId + " ---");
        dentistAppts.forEach(System.out::println);
    }

    public void viewAppointmentsByPatient(Long patientId) {
        List<Appointment> patientAppts = appointmentRepository.findByPatientId(patientId);

        System.out.println("--- Appointments for Patient ID " + patientId + " ---");
        patientAppts.forEach(System.out::println);
    }

    public void viewAllPatients() {
        System.out.println("--- Patients ---");
        appointmentRepository.findAllPatients().forEach(System.out::println);
    }

    public void viewAllDentists() {
        System.out.println("--- Dentists ---");
        appointmentRepository.findAllDentists().forEach(System.out::println);
    }

    public void displayAllAppointmentsAsJson() {
        List<Map<String, Object>> appointmentData = appointmentRepository.findAll().stream()
                .sorted(Comparator.comparing(Appointment::getDateTime).reversed())
                .map(this::toAppointmentJsonData)
                .collect(Collectors.toList());

        System.out.println("--- All Appointments (JSON, Descending by Date and Time) ---");
        System.out.println(new JsonUtility().toPrettyJson(appointmentData));
    }

    public void displayQuarterlyUpcomingAppointments() {
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> quarterlyAppts = appointmentRepository.findAll().stream()
                .filter(a -> DateUtility.isInNextQuarter(today, a.getDateTime().toLocalDate()))
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .map(this::toAppointmentJsonData)
                .collect(Collectors.toList());

        System.out.println("--- Quarterly Upcoming Appointments (JSON, Ascending by Date and Time) ---");
        if (quarterlyAppts.isEmpty()) {
            System.out.println("No upcoming appointments in the next quarter.");
        } else {
            System.out.println(new JsonUtility().toPrettyJson(quarterlyAppts));
        }
    }

    private Map<String, Object> toAppointmentJsonData(Appointment appointment) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("appointmentId", appointment.getId());
        data.put("appointmentDate", appointment.getDateTime().toLocalDate().toString());
        data.put("appointmentTime", appointment.getDateTime().toLocalTime().toString());
        data.put("patient", toPatientJsonData(appointment.getPatient()));
        data.put("dentist", toDentistJsonData(appointment.getDentist()));
        data.put("surgery", toSurgeryJsonData(appointment.getSurgery()));
        return data;
    }

    private Map<String, Object> toPatientJsonData(Patient patient) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (patient == null) {
            return data;
        }

        data.put("patientId", patient.getId());
        data.put("firstName", patient.getFirstName());
        data.put("lastName", patient.getLastName());
        data.put("mailingAddress", patient.getMailingAddress());
        data.put("phoneNumber", patient.getPhoneNumber());
        data.put("email", patient.getEmail());
        data.put("dateOfBirth", patient.getDateOfBirth().toString());
        data.put("age", patient.getAge());
        return data;
    }

    private Map<String, Object> toDentistJsonData(Dentist dentist) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (dentist == null) {
            return data;
        }

        data.put("dentistId", dentist.getId());
        data.put("firstName", dentist.getFirstName());
        data.put("lastName", dentist.getLastName());
        return data;
    }

    private Map<String, Object> toSurgeryJsonData(Surgery surgery) {
        Map<String, Object> data = new LinkedHashMap<>();
        if (surgery == null) {
            return data;
        }

        data.put("surgeryId", surgery.getId());
        data.put("name", surgery.getName());
        data.put("locationAddress", surgery.getLocationAddress());
        data.put("telephoneNumber", surgery.getTelephoneNumber());
        return data;
    }

    private boolean hasOverdueBill(Long patientId) {
        List<Bill> patientBills = appointmentRepository.findBillsByPatientId(patientId);
        LocalDate now = LocalDate.now();

        return patientBills.stream()
                .anyMatch(b -> !b.isPaid() && b.getServiceDate().isBefore(now.minusMonths(12)));
    }

    private boolean dentistHasReachedWeeklyLimit(Long dentistId, LocalDateTime dateTime, Long ignoredAppointmentId) {
        long weeklyAppointments = appointmentRepository.findByDentistId(dentistId).stream()
                .filter(a -> ignoredAppointmentId == null || !a.getId().equals(ignoredAppointmentId))
                .filter(a -> DateUtility.isSameWeek(a.getDateTime(), dateTime))
                .count();

        return weeklyAppointments >= 5;
    }
}
