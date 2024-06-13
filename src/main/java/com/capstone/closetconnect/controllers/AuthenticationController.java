package com.capstone.closetconnect.auth;

import com.capstone.closetconnect.dtos.AuthenticateUser;
import com.capstone.closetconnect.dtos.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody CreateUser request
    ){
        //
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticateUser request
    ){
        //
    }
}
