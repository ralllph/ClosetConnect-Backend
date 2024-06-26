package com.capstone.closetconnect.exceptions;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(){
        super("Invalid username or Password. Please try again");
    }
}
