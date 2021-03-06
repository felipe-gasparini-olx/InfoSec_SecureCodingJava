package com.ticketmagpie.controllers;

import com.ticketmagpie.Comment;
import com.ticketmagpie.Ticket;
import com.ticketmagpie.infrastructure.persistence.CommentRepository;
import com.ticketmagpie.infrastructure.persistence.ConcertRepository;
import com.ticketmagpie.infrastructure.persistence.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MainController mainController;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String userHome(Model model, Authentication authentication) {
        model.addAttribute("tickets", ticketRepository.getTicketsForUser(authentication.getName()));
        return "user";
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String userBook(@RequestParam String id, Model model) {
        model.addAttribute("concertid", id);
        return "bookingform";
    }

    @RequestMapping(value = "/book/payment", method = RequestMethod.POST)
    public String userPayment
            (@RequestParam("firstname") String firstName,
             @RequestParam("lastname") String lastName,
             @RequestParam("address1") String address1,
             @RequestParam("address2") String address2,
             @RequestParam("address3") String address3,
             @RequestParam("postcode") String postCode,
             @RequestParam("country") String country,
             @RequestParam("paymentmethod") String paymentmethod,
             @RequestParam("cardnumber") String cardnumber,
             @RequestParam("cvv2") String ccv2,
             @RequestParam("expirydate") String expirydate,
             @RequestParam("concertid") Integer concertId,
             Authentication currentUser) {
        Ticket ticket =
                new Ticket(concertRepository.get(concertId), firstName, lastName, address1, address2, address3, postCode, country, paymentmethod, cardnumber, ccv2, expirydate, currentUser.getName());
        int savedTicketId = ticketRepository.save(ticket);
        return "redirect:/ticket?id=" + savedTicketId;
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String comment(@RequestParam("concertId") Integer concertId,
                          @RequestParam("text") String text,
                          Authentication authentication,
                          Model model)
            throws IOException {

        String username = authentication.getName();

        Comment newComment = new Comment(concertId, username, text);
        commentRepository.save(newComment);
        return mainController.concert(concertId, model);
    }

}
