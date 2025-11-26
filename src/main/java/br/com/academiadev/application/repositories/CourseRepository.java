package main.java.br.com.academiadev.application.repositories;

import main.java.br.com.academiadev.domain.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void save(Course course);
    Optional<Course> findByTitle(String title);
    List<Course> findAll();
}
