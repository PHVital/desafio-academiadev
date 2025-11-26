package main.java.br.com.academiadev.main;

import main.java.br.com.academiadev.application.repositories.CourseRepository;
import main.java.br.com.academiadev.application.repositories.EnrollmentRepository;
import main.java.br.com.academiadev.application.repositories.TicketRepository;
import main.java.br.com.academiadev.application.repositories.UserRepository;
import main.java.br.com.academiadev.application.usecases.EnrollStudentUseCase;
import main.java.br.com.academiadev.application.usecases.GenerateReportsUseCase;
import main.java.br.com.academiadev.application.usecases.SupportServiceUseCase;
import main.java.br.com.academiadev.domain.entities.Admin;
import main.java.br.com.academiadev.domain.entities.Course;
import main.java.br.com.academiadev.domain.entities.PremiumPlan;
import main.java.br.com.academiadev.domain.entities.Student;
import main.java.br.com.academiadev.domain.enums.DifficultyLevel;
import main.java.br.com.academiadev.infrastructure.persistence.CourseRepositoryInMemory;
import main.java.br.com.academiadev.infrastructure.persistence.EnrollmentRepositoryInMemory;
import main.java.br.com.academiadev.infrastructure.persistence.TicketRepositoryInMemory;
import main.java.br.com.academiadev.infrastructure.persistence.UserRepositoryInMemory;
import main.java.br.com.academiadev.infrastructure.ui.ConsoleController;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepo = new UserRepositoryInMemory();
        CourseRepository courseRepo = new CourseRepositoryInMemory();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepositoryInMemory();
        TicketRepository ticketRepo = new TicketRepositoryInMemory();

        popularDados(userRepo, courseRepo);

        EnrollStudentUseCase enrollUseCase = new EnrollStudentUseCase(userRepo, courseRepo, enrollmentRepo);
        GenerateReportsUseCase reportsUseCase = new GenerateReportsUseCase(courseRepo, userRepo, enrollmentRepo);
        SupportServiceUseCase supportUseCase = new SupportServiceUseCase(ticketRepo);

        ConsoleController controller = new ConsoleController(
            enrollUseCase,
            reportsUseCase,
            (UserRepositoryInMemory) userRepo,
            supportUseCase
        );

        controller.start();
    }

    private static void popularDados(UserRepository userRepo, CourseRepository courseRepo) {
        Course javaCourse = new Course("Java Architecture", "João da Silva", DifficultyLevel.ADVANCED);
        courseRepo.save(javaCourse);

        Student pedro = new Student("Pedro", "pedro@email.com", new PremiumPlan());
        userRepo.save(pedro);

        Admin admin = new Admin("Admin", "admin@email.com");
        userRepo.save(admin);
    }
}