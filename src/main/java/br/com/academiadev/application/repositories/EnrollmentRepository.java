package main.java.br.com.academiadev.application.repositories;

import main.java.br.com.academiadev.domain.entities.Course;
import main.java.br.com.academiadev.domain.entities.Enrollment;

import java.util.List;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findAll();
    List<Enrollment> findByStudentEmail(String email);
}
