package main.java.br.com.academiadev.infrastructure.persistence;

import main.java.br.com.academiadev.application.repositories.CourseRepository;
import main.java.br.com.academiadev.domain.entities.Course;

import java.util.*;

public class CourseRepositoryInMemory implements CourseRepository {
    private final Map<String, Course> db = new HashMap<>();

    @Override
    public void save(Course course) {
        db.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(db.get(title));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(db.values());
    }
}
