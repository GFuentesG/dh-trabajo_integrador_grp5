package com.xplora.backend.authentication;

import com.xplora.backend.configuration.JwtService;
import com.xplora.backend.entity.Role;
import com.xplora.backend.entity.User;
import com.xplora.backend.exception.ResourceNotFoundException;
import com.xplora.backend.repository.IUserRepository;
import com.xplora.backend.service.IEmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository iUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IEmailService iEmailService;

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {
        logger.info("register - Registro de usuario: " + request);
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        iUserRepository.save(user);
        iEmailService.sendMailWelcome(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        logger.info("login - Login de usuario: " + request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = iUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
