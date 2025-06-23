package com.example.ticketSystem.Repository;

import com.example.ticketSystem.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
