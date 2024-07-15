package com.capstone.closetconnect.exceptions;

public class NotImageException extends RuntimeException{
    public NotImageException(){
        super("The file uploaded is not an image ");
    }
}
