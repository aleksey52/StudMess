package com.spbstu.StudMess.controller.authcontroller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.AuthenticationRequest;
import com.spbstu.StudMess.dto.request.RegistrationRequest;
import com.spbstu.StudMess.dto.response.AuthenticationResponse;
import com.spbstu.StudMess.model.enums.Role;
import com.spbstu.StudMess.security.JwtTokenProvider;
import com.spbstu.StudMess.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.spbstu.StudMess.security.JwtTokenProvider.TOKEN_PREFIX;

@Tag(name = "Authentication")
@ApiV1
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> login(@NonNull @Valid @RequestBody AuthenticationRequest request) {

        AuthenticationResponse response = authenticationService.login(request.getEmail(), request.getPassword());
        final String token = jwtTokenProvider.createToken(response.getEmail(), Role.valueOf(response.getRole()));

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION)
                .body(response);
    }

    @PostMapping("/auth/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request, response);
    }

    @PostMapping("/auth/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> registration(@NonNull @Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.register(request.getLogin(), request.getPassword(),
                        request.getFirstName(), request.getMiddleName(), request.getLastName(),
                        request.getGroup(), request.getEmail(), request.getPhone()));
    }

    @GetMapping("/auth/checkToken")
    public ResponseEntity<String> checkValidateToken(@NonNull @RequestParam String token) {
        jwtTokenProvider.validateToken(token);
        return ResponseEntity.ok("Token is valid");
    }
}
