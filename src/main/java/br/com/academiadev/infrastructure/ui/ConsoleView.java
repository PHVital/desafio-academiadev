package main.java.br.com.academiadev.infrastructure.ui;

public class ConsoleView {
    public void showWelcome() {
        System.out.println("\n=== BEM-VINDO À ACADEMIADEV ===");
    }

    public void showLogin() {
        System.out.println("Digite seu e-mail para entrar (ou deixe em branco para encerrar): ");
    }

    public void showAdminMenu() {
        System.out.println("\n--- MENU ADMINISTRADOR ---");
        System.out.println("1. Relatório: Cursos por Nível");
        System.out.println("2. Relatório: Instrutores Ativos");
        System.out.println("3. Exportar Cursos para CSV");
        System.out.println("4. Atender Próximo Ticket (Fila FIFO)");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public void showStudentMenu(String studentName) {
        System.out.println("\n--- MENU ALUNO: " + studentName + " ---");
        System.out.println("1. Matricular em Curso");
        System.out.println("2. Ver minhas Matrículas");
        System.out.println("3. Abrir Ticket de Suporte");
        System.out.println("0. Sair");
        System.out.println("Opção: ");
    }

    public void showMessage(String message) {
        System.out.println(">> " + message);
    }

    public void showError(String error) {
        System.out.println("!! ERRO: " + error);
    }
}
