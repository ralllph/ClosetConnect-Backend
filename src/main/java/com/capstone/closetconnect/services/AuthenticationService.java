package com.capstone.closetconnect.services;

import com.capstone.closetconnect.dtos.request.AuthenticateUser;
import com.capstone.closetconnect.dtos.request.CreateUser;
import com.capstone.closetconnect.dtos.response.AuthenticationResponse;
import com.capstone.closetconnect.exceptions.LoginFailedException;
import com.capstone.closetconnect.exceptions.UserAlreadyExistsException;
import com.capstone.closetconnect.models.User;
import com.capstone.closetconnect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(CreateUser request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent() ){
            throw new UserAlreadyExistsException(request.getEmail());
        }
        User user = CreateUser.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticateUser request) {
        //authenticate user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            throw  new LoginFailedException();
        }

        //user authenticated at this point,send token
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }
}
