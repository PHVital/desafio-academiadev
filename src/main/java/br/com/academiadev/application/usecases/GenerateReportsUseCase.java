package main.java.br.com.academiadev.application.usecases;

import main.java.br.com.academiadev.application.repositories.CourseRepository;
import main.java.br.com.academiadev.application.repositories.EnrollmentRepository;
import main.java.br.com.academiadev.application.repositories.UserRepository;
import main.java.br.com.academiadev.domain.entities.Course;
import main.java.br.com.academiadev.domain.entities.Enrollment;
import main.java.br.com.academiadev.domain.entities.Student;
import main.java.br.com.academiadev.domain.enums.DifficultyLevel;

import java.util.*;
import java.util.stream.Collectors;

public class GenerateReportsUseCase {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public GenerateReportsUseCase(CourseRepository courseRepository, UserRepository userRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Course> getCoursesByDifficulty(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getDifficultyLevel() == level)
                .sorted(Comparator.comparing(Course::getTitle))
                .toList();
    }

    public Set<String> getUniqueActiveInstructors() {
        return courseRepository.findAll().stream()
                .filter(Course::isActive)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<String, List<Student>> groupStudentsByPlan() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.groupingBy(student ->
                        student.getSubscriptionPlan().getClass().getSimpleName()
                ));
    }

    public double getAverageProgress() {
        return enrollmentRepository.findAll().stream()
                .mapToInt(Enrollment::getProgressPercentage)
                .average()
                .orElse(0.0);
    }

    public Optional<Student> getStudentWithMostEnrollments() {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .max(Comparator.comparingInt(s ->
                        (int) s.getEnrollments().stream()
                                .filter(Enrollment::isActive).count()
                ));
    }
}
