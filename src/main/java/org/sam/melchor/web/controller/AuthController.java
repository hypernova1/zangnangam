package org.sam.melchor.web.controller;

import lombok.RequiredArgsConstructor;
import org.sam.melchor.service.AccountService;
import org.sam.melchor.service.AuthService;
import org.sam.melchor.web.payload.AccountDto;
import org.sam.melchor.web.payload.ApiResponse;
import org.sam.melchor.web.payload.AuthDto;
import org.sam.melchor.web.payload.JwtAuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthDto.SignUpRequest request) {

        if (accountService.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email is already taken"), HttpStatus.BAD_REQUEST);
        }

        AccountDto.SummaryResponse summary = authService.signUp(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/{id}")
                .buildAndExpand(summary.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User Registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid @RequestBody AuthDto.LoginRequest request) {

        String jwt = authService.signIn(request);

        return ResponseEntity.ok(new JwtAuthenticationResponse((jwt)));
    }

}
