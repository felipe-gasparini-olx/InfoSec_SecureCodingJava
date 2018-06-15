package com.ticketmagpie.infrastructure.security;

import com.ticketmagpie.User;
import com.ticketmagpie.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Collections.singleton;

@Component
public class TicketMagPieAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        Optional<User> user = userRepository.get(username);

        boolean success = user.map(u -> passwordEncoder.matches(password, u.getPassword())).orElse(false);

        if (!success) {
            throw new InternalAuthenticationServiceException("Something failed");
        }

        return toAuthentication(user.get());

    }

    private Authentication toAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), singleton(new SimpleGrantedAuthority(user.getRole())));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication));
    }
}
