package main.java.br.com.academiadev.infrastructure.ui;

import main.java.br.com.academiadev.application.usecases.EnrollStudentUseCase;
import main.java.br.com.academiadev.application.usecases.GenerateReportsUseCase;
import main.java.br.com.academiadev.application.usecases.SupportServiceUseCase;
import main.java.br.com.academiadev.domain.entities.Course;
import main.java.br.com.academiadev.domain.entities.Student;
import main.java.br.com.academiadev.domain.entities.SupportTicket;
import main.java.br.com.academiadev.domain.entities.User;
import main.java.br.com.academiadev.domain.enums.DifficultyLevel;
import main.java.br.com.academiadev.domain.exceptions.BusinessException;
import main.java.br.com.academiadev.infrastructure.persistence.UserRepositoryInMemory;
import main.java.br.com.academiadev.infrastructure.utils.GenericCsvExporter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleController {
    private final ConsoleView view;
    private final EnrollStudentUseCase enrollStudentUseCase;
    private final GenerateReportsUseCase reportsUseCase;
    private final UserRepositoryInMemory userRepository;
        private final SupportServiceUseCase supportUseCase;
    private final Scanner scanner;

    public ConsoleController(EnrollStudentUseCase enrollStudentUseCase,
                             GenerateReportsUseCase reportsUseCase,
                             UserRepositoryInMemory userRepository,
                             SupportServiceUseCase supportUseCase) {
        this.view = new ConsoleView();
        this.enrollStudentUseCase = enrollStudentUseCase;
        this.reportsUseCase = reportsUseCase;
        this.userRepository = userRepository;
        this.supportUseCase = supportUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        view.showWelcome();
        while (true) {
            try {
                view.showLogin();
                String email = scanner.nextLine();
                if (email.isEmpty()) break;

                login(email);

            } catch (Exception e) {
                view.showError("Erro inesperado no sistema: " + e.getMessage());
            }
        }
    }

    private void login(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (!userOpt.isPresent()) {
            view.showError("Usuário não encontrado");
            return;
        }

        User user = userOpt.get();

        if(user instanceof Student) {
            handleStudentSession((Student) user);
        } else {
            handleAdminSession(user);
        }
    }

    private void handleStudentSession(Student student) {
        boolean loggedIn = true;
        while(loggedIn) {
            view.showStudentMenu(student.getName());
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1":
                        System.out.println("Digite o nome exato do curso: ");
                        String courseTitle = scanner.nextLine();
                        enrollStudentUseCase.execute(student.getEmail(), courseTitle);
                        view.showMessage("Matrícula realizada com sucesso!");
                        break;
                    case "2":
                        view.showMessage("Cursos matriculados: " + student.getEnrollments().size());
                        break;
                    case "3":
                        System.out.println("Título do Problema: ");
                        String title = scanner.nextLine();
                        System.out.println("Descreva seu problema: ");
                        String message = scanner.nextLine();

                        supportUseCase.openTicket(student.getEmail(), title, message);
                        view.showMessage("Ticket de suporte criado com sucesso! Aguarde atendimento");
                        break;
                    case "0":
                        loggedIn = false;
                        break;
                    default:
                        view.showError("Opção inválida");
                }
            } catch (BusinessException e) {
                view.showError(e.getMessage());
            }
        }
    }

    public void handleAdminSession(User admin) {
        boolean loggedIn = true;
        while(loggedIn) {
            view.showAdminMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    List<Course> courses = reportsUseCase.getCoursesByDifficulty(DifficultyLevel.ADVANCED);
                    courses.forEach(c -> System.out.println(c.getTitle() + " - " + c.getInstructorName()));
                    break;
                case "2":
                    reportsUseCase.getUniqueActiveInstructors().forEach(System.out::println);
                    break;
                case "3":
                    exportCoursesCsv();
                    break;
                case "4":
                    Optional<SupportTicket> ticketOpt = supportUseCase.processNextTicket();

                    if (ticketOpt.isPresent()) {
                        SupportTicket ticket = ticketOpt.get();
                        System.out.println("\n-------------------------------");
                        System.out.println("ATENDENDO TICKET #" + ticket.getId());
                        System.out.println("Aluno: " + ticket.getUserEmail());
                        System.out.println("Mensagem: " + ticket.getMessage());
                        System.out.println("-------------------------------");
                        view.showMessage("Ticket removido da fila com sucesso");
                    } else {
                        view.showMessage("A fila de atendimento está vazia!");
                    }
                    break;
                case "0":
                    loggedIn = false;
                    break;
                default:
                    view.showError("Opção inválida");
            }
        }
    }

    private void exportCoursesCsv() {
        view.showMessage("Gerando CSV de Cursos Avançados...");

        List<Course> data = reportsUseCase.getCoursesByDifficulty(DifficultyLevel.ADVANCED);
        List<String> columns = Arrays.asList("title", "instructorName", "durationInHours");
        String csvOutput = GenericCsvExporter.exportToCsv(data, columns);

        System.out.println("\n--- CSV GERADO ---");
        System.out.println(csvOutput);
        System.out.println("-------------------");
    }
}
