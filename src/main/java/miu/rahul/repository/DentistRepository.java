package miu.rahul.repository;

import miu.rahul.data.MockDatabase;
import miu.rahul.model.Dentist;

import java.util.List;
import java.util.Optional;

public class DentistRepository {
    public Optional<Dentist> findById(Long id) {
        return MockDatabase.dentists.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public List<Dentist> findAll() {
        return MockDatabase.dentists;
    }
}
