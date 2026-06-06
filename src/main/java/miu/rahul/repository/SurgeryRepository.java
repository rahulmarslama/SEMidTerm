package miu.rahul.repository;

import miu.rahul.data.MockDatabase;
import miu.rahul.model.Surgery;

import java.util.List;
import java.util.Optional;

public class SurgeryRepository {
    public Optional<Surgery> findById(Long id) {
        return MockDatabase.surgeries.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public List<Surgery> findAll() {
        return MockDatabase.surgeries;
    }
}
