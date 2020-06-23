package edu.utn.UTNPhones.controllers;

import edu.utn.UTNPhones.dtos.ErrorResponseDto;
import edu.utn.UTNPhones.exceptions.*;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, "Invalid login");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ErrorResponseDto handleDataAccessException(DataAccessException exc) {
        return new ErrorResponseDto(2, exc.getRootCause().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotExistException.class)
    public ErrorResponseDto handleNotExists(NotExistException e) {
        return new ErrorResponseDto(3, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamException.class)
    public ErrorResponseDto handleParamException(ParamException e){
        return new ErrorResponseDto(4, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException e){
        return new ErrorResponseDto(4, e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(EmptyListException.class)
    public ErrorResponseDto handleEmptyListException(EmptyListException e){ return new ErrorResponseDto(5, e.getMessage()); }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponseDto handleNoSuchElementException(){ return new ErrorResponseDto(6, "Not exists"); }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GenericJDBCException.class)
    public ErrorResponseDto handleGenericJDBCException(GenericJDBCException e){ return new ErrorResponseDto(7, e.getMessage() ); }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto handleIllegalArgumentException(IllegalArgumentException exc){
        return new ErrorResponseDto(8,exc.getMessage());
    }

}