package com.spbstu.StudMess.service;

import com.spbstu.StudMess.dto.response.AuthenticationResponse;
import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.exception.VerificationException;
import com.spbstu.StudMess.model.UserEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(@NonNull String email, @NonNull String password) {
        final UserEntity user;
        try {
            user = userService.findByEmail(email);
        } catch (NotFoundException ex) {
            throw new NotFoundException(format("The account with email %s does not exist", email));
        }

        if (!user.isEnabled()) {
            if (user.getVerificationCode() != null) {
                throw new VerificationException("This account is not verified");
            }
            throw new LockedException("This account is blocked");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return new AuthenticationResponse(user.getId(), email, user.getLogin(), user.getRole().toString());
    }

    public AuthenticationResponse register(@NonNull String login, @NonNull String password,
                                           @NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                                           @NonNull String group, @NonNull String email, @Nullable String phone) {
        if (userService.isExists(email)) {
            throw new BadCredentialsException(format("The account with email %s is already registered", email));
        } else if (userService.isExistsWithLogin(login)) {
            throw new BadCredentialsException(format("The account with login %s is already registered", login));
        }

        UserEntity user = userService.register(login, password, firstName, middleName, lastName, group, email, phone);
        return new AuthenticationResponse(user.getId(), email, login, user.getRole().toString());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, auth);
    }
}
