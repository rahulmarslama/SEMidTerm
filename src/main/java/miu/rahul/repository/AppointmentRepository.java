package miu.rahul.repository;

import miu.rahul.model.Appointment;
import miu.rahul.model.Bill;
import miu.rahul.model.Dentist;
import miu.rahul.model.Patient;
import miu.rahul.model.Surgery;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(Long id);
    List<Appointment> findAll();
    List<Appointment> findByDentistId(Long dentistId);
    List<Appointment> findByPatientId(Long patientId);
    Optional<Patient> findPatientById(Long patientId);
    List<Patient> findAllPatients();
    Optional<Dentist> findDentistById(Long dentistId);
    List<Dentist> findAllDentists();
    Optional<Surgery> findSurgeryById(Long surgeryId);
    List<Bill> findBillsByPatientId(Long patientId);
    void delete(Long id);
}
