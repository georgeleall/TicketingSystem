package com.example.ticketSystem.Controller;

import com.example.ticketSystem.model.Ticket;
import com.example.ticketSystem.model.Comment;
import com.example.ticketSystem.Service.TicketService;
import com.example.ticketSystem.Service.UserService;
import com.example.ticketSystem.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTickets(Model model) {
        Iterable<Ticket> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ListTickets";
    }

    @GetMapping("/new")
    public String showCreateTicketForm() {
        return "addTicket";
    }

    @PostMapping("/new")
    public String createTicket(@RequestParam String category, @RequestParam String subject,
                               @RequestParam(required = false) String date,
                               @RequestParam(required = false) String status, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("user");

        if (date == null || date.isEmpty()) {
            date = java.time.LocalDate.now().toString();
        }

        if (status == null || status.isEmpty()) {
            status = "Open";
        }

        List<User> users = userService.getAllUsers();

        String assignedTo = "None";
        if (!users.isEmpty()) {
            Random random = new Random();
            List<User> technicians = users.stream()
                    .filter(user -> user.getRole() == User.Role.TECHNICIAN)
                    .toList();
            if (!technicians.isEmpty()) {
                User randomTechnician = technicians.get(random.nextInt(technicians.size()));
                assignedTo = randomTechnician.getName();
            } else {
                return "error";
            }
        } else {
            return "error";
        }

        Ticket newTicket = new Ticket(null, category, subject, loggedInUser.getName(), date, assignedTo, status);
        ticketService.saveTicket(newTicket);

        return "redirect:/tickets";
    }

    @PostMapping("/{ticketNum}/comment")
    public String addComment(@PathVariable Long ticketNum, @RequestParam("author") String author,
                             @RequestParam("content") String content) {
        Ticket ticket = ticketService.getTicketByNumber(ticketNum);
        if (ticket != null) {
            Comment newComment = new Comment(author, content, new java.util.Date().toString());
            newComment.setTicket(ticket);
            ticket.addComment(newComment);
            ticketService.saveTicket(ticket);
        }
        return "redirect:/tickets/" + ticketNum;
    }


    @GetMapping("/{ticketNum}")
    public String viewTicket(@PathVariable Long ticketNum, Model model, HttpSession session) {
        Ticket ticket = ticketService.getTicketByNumber(ticketNum);
        User loggedInUser = (User) session.getAttribute("user");
        if (ticket != null) {
            model.addAttribute("ticket", ticket);
            model.addAttribute("loggedInUser", loggedInUser);
            return "viewTicket";
        }
        return "redirect:/tickets";
    }


    @PostMapping("/{ticketNum}/close")
    public String closeTicket(@PathVariable Long ticketNum) {
        Ticket ticket = ticketService.getTicketByNumber(ticketNum);
        if (ticket != null && (ticket.getStatus().equals("Open") || ticket.getStatus().equals("Assigned"))) {
            ticket.setStatus("Closed");
            ticketService.saveTicket(ticket);
        }
        return "redirect:/tickets/" + ticketNum;
    }

    @PostMapping("/{ticketNum}/assign")
    public String assignTicket(@PathVariable Long ticketNum, @RequestParam String assignedTo) {
        Ticket ticket = ticketService.getTicketByNumber(ticketNum);
        if (ticket != null && !assignedTo.isEmpty()) {
            ticket.setAssignedTo(assignedTo);
            ticketService.saveTicket(ticket);
        }
        return "redirect:/tickets/" + ticketNum;
    }

}
