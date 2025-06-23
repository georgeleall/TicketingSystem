package com.example.ticketSystem.Service;

import com.example.ticketSystem.model.Ticket;
import com.example.ticketSystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // Get all tickets from the database
    public Iterable<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get a ticket by ticket number (ticketNum)
    public Ticket getTicketByNumber(Long ticketNum) {
        return ticketRepository.findById(ticketNum).orElse(null);
    }

    // Save a new ticket
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
