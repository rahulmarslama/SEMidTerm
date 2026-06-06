package miu.rahul.repository;

import miu.rahul.data.MockDatabase;
import miu.rahul.model.Appointment;
import miu.rahul.model.Bill;
import miu.rahul.model.Dentist;
import miu.rahul.model.Patient;
import miu.rahul.model.Surgery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentRepositoryImpl implements AppointmentRepository {

    private static final AppointmentRepositoryImpl INSTANCE = new AppointmentRepositoryImpl();

    private AppointmentRepositoryImpl() {
    }

    public static AppointmentRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            Appointment newAppt = new Appointment(
                    MockDatabase.appointmentIdCounter++,
                    appointment.getDateTime(),
                    appointment.getDentist(),
                    appointment.getPatient(),
                    appointment.getSurgery()
            );
            MockDatabase.appointments.add(newAppt);
            return newAppt;
        } else {
            MockDatabase.appointments.removeIf(a -> a.getId().equals(appointment.getId()));
            MockDatabase.appointments.add(appointment);
            return appointment;
        }
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return MockDatabase.appointments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Appointment> findAll() {
        return MockDatabase.appointments;
    }

    @Override
    public List<Appointment> findByDentistId(Long dentistId) {
        return MockDatabase.appointments.stream()
                .filter(a -> a.getDentist() != null && a.getDentist().getId().equals(dentistId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return MockDatabase.appointments.stream()
                .filter(a -> a.getPatient() != null && a.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Patient> findPatientById(Long patientId) {
        return MockDatabase.patients.stream()
                .filter(patient -> patient.getId().equals(patientId))
                .findFirst();
    }

    @Override
    public List<Patient> findAllPatients() {
        return MockDatabase.patients;
    }

    @Override
    public Optional<Dentist> findDentistById(Long dentistId) {
        return MockDatabase.dentists.stream()
                .filter(dentist -> dentist.getId().equals(dentistId))
                .findFirst();
    }

    @Override
    public List<Dentist> findAllDentists() {
        return MockDatabase.dentists;
    }

    @Override
    public Optional<Surgery> findSurgeryById(Long surgeryId) {
        return MockDatabase.surgeries.stream()
                .filter(surgery -> surgery.getId().equals(surgeryId))
                .findFirst();
    }

    @Override
    public List<Bill> findBillsByPatientId(Long patientId) {
        return MockDatabase.bills.stream()
                .filter(bill -> bill.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        MockDatabase.appointments.removeIf(a -> a.getId().equals(id));
    }
}
