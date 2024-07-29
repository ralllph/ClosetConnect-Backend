package com.capstone.closetconnect.exceptions;

public class NotAssociatedException extends RuntimeException{

    public NotAssociatedException(String attemptedEntity, String nonAssociatedEntity){
        super(attemptedEntity + " " + "is not associated with " + " " + nonAssociatedEntity);
    }

}
