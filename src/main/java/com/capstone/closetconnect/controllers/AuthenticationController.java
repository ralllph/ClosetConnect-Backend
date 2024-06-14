package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.AuthenticateUser;
import com.capstone.closetconnect.dtos.request.CreateUser;
import com.capstone.closetconnect.dtos.response.AuthenticationResponse;
import com.capstone.closetconnect.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid CreateUser request
    ){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticateUser request
    ){
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
}
