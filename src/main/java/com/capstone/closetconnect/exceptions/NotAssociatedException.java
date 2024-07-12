package com.capstone.closetconnect.exceptions;

public class NotAssociatedException extends RuntimeException{

    public NotAssociatedException(String attemptedEntity, String nonAssociatedEntity){
        super("This " + " " + attemptedEntity + " " + "is not associated with this" + " " + nonAssociatedEntity);
    }

}
