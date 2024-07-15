package com.capstone.closetconnect;

import com.capstone.closetconnect.exceptions.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errorList = new ArrayList<>();
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            errorList.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorResponse(errorList),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> AlreadyExistsException(UserAlreadyExistsException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> NotFoundException(NotFoundException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageTooLargeException.class)
    public ResponseEntity<Object> imageTooLargeException(ImageTooLargeException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<Object> invalidImageException(InvalidImageException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImageException.class)
    public ResponseEntity<Object> notImageException(NotImageException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<Object>  loginFailedException(LoginFailedException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingParameterException.class)
    public ResponseEntity<Object> MissingParameterException(MissingParameterException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAssociatedException.class)
    public ResponseEntity<Object> wrongAssociationException(NotAssociatedException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PaginationException.class)
    public ResponseEntity<Object> paginationException(PaginationException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
