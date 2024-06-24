package com.capstone.closetconnect.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String missingEntity, Long id){
        super("The" + " " + missingEntity + " with the id" + id + "does not exist"  );
    }

}
