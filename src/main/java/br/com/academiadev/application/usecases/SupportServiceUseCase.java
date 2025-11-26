package main.java.br.com.academiadev.application.usecases;

import main.java.br.com.academiadev.application.repositories.TicketRepository;
import main.java.br.com.academiadev.domain.entities.SupportTicket;

import java.util.Optional;

public class SupportServiceUseCase {
    private final TicketRepository ticketRepository;

    public SupportServiceUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void openTicket(String userEmail, String title, String message) {
        SupportTicket ticket = new SupportTicket(userEmail, title, message);
        ticketRepository.add(ticket);
    }

    public Optional<SupportTicket> processNextTicket() {
        return  ticketRepository.next();
    }
}
