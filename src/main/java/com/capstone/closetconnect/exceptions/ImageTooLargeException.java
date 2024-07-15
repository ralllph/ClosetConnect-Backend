package com.capstone.closetconnect.exceptions;

public class ImageTooLargeException extends RuntimeException{

    public ImageTooLargeException(int maxImageSize){
        super("Image too large. Maximum size allowed  " + " " +  maxImageSize);
    }

}
