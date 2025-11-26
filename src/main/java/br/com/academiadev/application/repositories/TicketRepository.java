package main.java.br.com.academiadev.application.repositories;

import main.java.br.com.academiadev.domain.entities.SupportTicket;

import java.util.Optional;

public interface TicketRepository {
    void add(SupportTicket ticket);
    Optional<SupportTicket> next();
    boolean isEmpty();
}
