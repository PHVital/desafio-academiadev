package main.java.br.com.academiadev.infrastructure.persistence;

import main.java.br.com.academiadev.application.repositories.EnrollmentRepository;
import main.java.br.com.academiadev.domain.entities.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepositoryInMemory implements EnrollmentRepository {
    private final List<Enrollment> db = new ArrayList<>();


    @Override
    public void save(Enrollment enrollment) {
        db.add(enrollment);
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public List<Enrollment> findByStudentEmail(String email) {
        return db.stream()
                .filter(e -> e.getStudent().getEmail().equals(email))
                .toList();
    }
}
