package com.capstone.closetconnect.exceptions;

public class InvalidImageException extends RuntimeException{
    public InvalidImageException(){
        super("Invalid image type. upload jpeg or png files ");
    }
}
