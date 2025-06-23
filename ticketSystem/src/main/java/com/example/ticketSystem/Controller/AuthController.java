package com.example.ticketSystem.Controller;

import com.example.ticketSystem.model.User;
import com.example.ticketSystem.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(value = "error", required = false) String error, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return new ModelAndView("redirect:/tickets");
        }

        ModelAndView modelAndView = new ModelAndView("home");
        if (error != null) {
            modelAndView.addObject("error", "Invalid credentials, please try again.");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {

            if (email.equals("john@example.com") || email.equals("jane@example.com")) {
                user.setRole(User.Role.TECHNICIAN);
            } else {
                user.setRole(User.Role.USER);
            }
            session.setAttribute("user", user);
            return "redirect:/tickets";
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
