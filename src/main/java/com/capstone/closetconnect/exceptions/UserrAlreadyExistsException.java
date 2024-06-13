package com.capstone.closetconnect.exceptions;

public class UserrAlreadyExistsException extends RuntimeException{
    public UserrAlreadyExistsException(String email){
        super("The user with email" + " "+ email + " " +  "already exists in our record");
    }
}
