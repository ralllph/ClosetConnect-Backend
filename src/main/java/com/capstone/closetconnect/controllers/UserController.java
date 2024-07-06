package com.capstone.closetconnect.controllers;

import com.capstone.closetconnect.dtos.request.UpdateUser;
import com.capstone.closetconnect.dtos.response.UserDetail;
import com.capstone.closetconnect.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDetail> updateUser
            (@RequestBody @Valid UpdateUser userDto, @PathVariable Long id){
        log.info("Update user request coming in for user with id {} and body{}",id,userDto);
        return new ResponseEntity<>(userService.updateUser(id,userDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetail>getUser (@PathVariable Long id)
    {
        log.info("Retrieve user details request coming in for user with id {}}",id);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

}
