package com.capstone.closetconnect.exceptions;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(){
        super("Invalid credentials or token. consider checking credentials and expiry");
    }
}
