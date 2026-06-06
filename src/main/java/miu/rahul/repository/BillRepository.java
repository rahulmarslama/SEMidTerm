package miu.rahul.repository;

import miu.rahul.data.MockDatabase;
import miu.rahul.model.Bill;
import miu.rahul.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class BillRepository {

    public List<Bill> findByPatientId(Long patientId) {
        return MockDatabase.bills.stream()
                .filter(b -> b.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }
}
