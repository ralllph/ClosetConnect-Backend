package com.capstone.closetconnect.exceptions;

public class MissingParameterException extends RuntimeException{

    public MissingParameterException(){
        super("Missing search param. You must pass at least one");
    }

}
