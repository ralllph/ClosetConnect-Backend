package com.capstone.closetconnect.exceptions;

public class PaginationException extends RuntimeException{

    public PaginationException(){ super("Invalid pagination parameter"); }

}
