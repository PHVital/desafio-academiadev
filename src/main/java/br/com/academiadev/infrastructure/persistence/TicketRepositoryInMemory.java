package main.java.br.com.academiadev.infrastructure.persistence;

import main.java.br.com.academiadev.application.repositories.TicketRepository;
import main.java.br.com.academiadev.domain.entities.SupportTicket;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class TicketRepositoryInMemory implements TicketRepository {
    private final Queue<SupportTicket> queue = new LinkedList<>();

    @Override
    public void add(SupportTicket ticket) {
        queue.offer(ticket);
    }

    @Override
    public Optional<SupportTicket> next() {
        return Optional.ofNullable(queue.poll());
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
