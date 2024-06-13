package com.capstone.closetconnect;

import com.capstone.closetconnect.exceptions.ErrorResponse;

import com.capstone.closetconnect.exceptions.UserrAlreadyExistsException;
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

import java.util.Arrays;
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

    @ExceptionHandler(UserrAlreadyExistsException.class)
    public ResponseEntity<Object> NotFoundException(UserrAlreadyExistsException exception){
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
