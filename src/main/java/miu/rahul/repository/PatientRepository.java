package miu.rahul.repository;

import miu.rahul.data.MockDatabase;
import miu.rahul.model.Patient;

import java.util.List;
import java.util.Optional;

public class PatientRepository {
    public Optional<Patient> findById(Long id) {
        return MockDatabase.patients.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Patient> findAll() {
        return MockDatabase.patients;
    }
}
