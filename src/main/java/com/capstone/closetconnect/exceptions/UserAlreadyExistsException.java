package com.capstone.closetconnect.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String email){
        super("The user with email" + " "+ email + " " +  "already exists in our record");
    }
}
