package com.ticketmagpie.infrastructure.security;

import com.ticketmagpie.User;
import com.ticketmagpie.infrastructure.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class ForgotPasswordService {
    @Autowired
    private MailService mailService;

    public void userForgotPassword(User user) {

        // FIXME should send a token with expiration instead

        try {
            mailService.sendEmail(
                    user.getEmail(),
                    "Your Ticketmagpie password",
                    format(
                            "Hello %s! Have you lost your password? Here it is: %s.",
                            user.getUsername(),
                            user.getPassword()));
        } catch (Exception ignored) {

        }
    }
}
