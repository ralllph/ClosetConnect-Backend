package com.capstone.closetconnect.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String missingEntity, Long id){
        super(id!=null ? "The" + " " + missingEntity + " with the id"
                + id + "does not exist": "The" + " " + missingEntity  + " does not exist;");
    }

}
