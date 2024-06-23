package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.AuthenticateUser;
import com.capstone.closetconnect.dtos.request.CreateUser;
import com.capstone.closetconnect.dtos.response.AuthenticationResponse;
import com.capstone.closetconnect.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid CreateUser request
    ){
        log.info("Registration request coming in for user with email {}", request.getEmail());
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid AuthenticateUser request
    ){
        log.info("Login request coming in for user with email {}", request.getEmail());
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
}
