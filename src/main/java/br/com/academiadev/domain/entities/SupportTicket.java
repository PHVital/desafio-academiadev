package main.java.br.com.academiadev.domain.entities;

public class SupportTicket {
    private static int idCounter = 1;
    private Integer id;
    private String userEmail;
    private String title;
    private String message;

    public SupportTicket(String userEmail, String title, String message) {
        this.id = idCounter++;
        this.userEmail = userEmail;
        this.title = title;
        this.message = message;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public Integer getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("[Ticket #%d] User: %s | Title: %s", id, userEmail, title);
    }
}
