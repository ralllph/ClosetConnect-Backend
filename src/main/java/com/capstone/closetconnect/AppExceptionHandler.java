package com.task.socialmedia;

import com.task.socialmedia.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CommentCreateFailedException.class)
    //we are returning an object of error here but Object is more generic
    //the parameter is the contact not found exception
    public ResponseEntity<Object> handleDDroneNotFoundException(CommentCreateFailedException exception){
        //create an object of Error response,, exception .getLocalizedMessage( ) is getting the message we passed in super in DroneNotFound exception
        //notice that our message is a list it=n in the error class so we make the error object be returned as a json array that supplies  alist
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        //now we return the error object
        //remember response entity  and serialization where spring boot would go to errResponse and change the object to json before returning it
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleDroneWeightExceededException(CommentNotFoundException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundException(NotFoundException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //type handleMethod ... to override
    //This method is where we handle those validations like not blank
    //when spring boot performs @valid check, the errors are packaged in a binding result which you can access here
    //through the parameter MethodArgument not valid exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //now we need to loop through all the errors binded to Methodargument exception in ex
        //the ex.getBinding.getallerrors is a list with a data type of Object Error which
        //is why the return type for this for loop is ObjectError
        //we create an error list where we can add ech error element from the loop
        List<String> errorList = new ArrayList<>();
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            errorList.add(error.getDefaultMessage());
        }
        //note that this is the return type when you originally override but you need to cancel and return a status of 404 bad request
        //return super.handleMethodArgumentNotValid(ex, headers, status, request);
        //now we can pass a new object containing the errorList as the parameter for the new constructor
        return new ResponseEntity<>(new ErrorResponse(errorList),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostCreateFailedException.class)
    public ResponseEntity<Object> handleDroneUnableToLoadException(PostCreateFailedException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFriendsException.class)
    public ResponseEntity<Object>  NoFriendsException(NoFriendsException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handleDroneUnableToLoadException(PostNotFoundException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class )
    public ResponseEntity<Object> handleDroneUnableToLoadException(UserNotFoundException exception){
        ErrorResponse error = new ErrorResponse(Arrays.asList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
