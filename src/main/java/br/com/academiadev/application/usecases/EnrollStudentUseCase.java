package main.java.br.com.academiadev.application.usecases;

import main.java.br.com.academiadev.application.repositories.CourseRepository;
import main.java.br.com.academiadev.application.repositories.EnrollmentRepository;
import main.java.br.com.academiadev.application.repositories.UserRepository;
import main.java.br.com.academiadev.domain.entities.Course;
import main.java.br.com.academiadev.domain.entities.Enrollment;
import main.java.br.com.academiadev.domain.entities.Student;
import main.java.br.com.academiadev.domain.entities.User;
import main.java.br.com.academiadev.domain.exceptions.BusinessException;

public class EnrollStudentUseCase {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollStudentUseCase(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String studentEmail, String courseTitle) {
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado"));

        if (!(user instanceof Student)) {
            throw new BusinessException("Apenas alunos podem se matricular");
        }
        Student student = (Student) user;

        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new BusinessException("Curso não encontrado"));

        if(!course.isActive()) {
            throw new BusinessException("Curso não está ativo");
        }

        if (!student.canEnroll()) {
            throw new BusinessException("Plano de assinatura não permite mais matrículas");
        }

        boolean alreadyEnrolled = student.getEnrollments().stream()
                .anyMatch(e -> e.getCourse().getTitle().equals(courseTitle));

        if (alreadyEnrolled) {
            throw new BusinessException("Aluno já matriculado neste curso");
        }

        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);

        enrollmentRepository.save(enrollment);
    }
}
