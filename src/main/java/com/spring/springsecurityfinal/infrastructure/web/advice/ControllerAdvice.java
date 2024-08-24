package com.spring.springsecurityfinal.infrastructure.web.advice;

import com.spring.springsecurityfinal.application.dto.errors.ErrorDetails;
import com.spring.springsecurityfinal.domain.exceptions.DniCustomException;
import com.spring.springsecurityfinal.domain.exceptions.EmailCustomException;
import com.spring.springsecurityfinal.domain.exceptions.RolNotFoundException;
import com.spring.springsecurityfinal.domain.exceptions.UsuarioNotFoundException;
import com.spring.springsecurityfinal.infrastructure.security.jwt.exceptions.JwtExceptionCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> method(MethodArgumentNotValidException ex){

        List<FieldError> fieldErrors=ex.getBindingResult().getFieldErrors();
        Map<String,Object> errores=new HashMap<>();
        errores.put("status",HttpStatus.BAD_REQUEST);
        for (FieldError error : fieldErrors) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> sqlException(SQLIntegrityConstraintViolationException ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RolNotFoundException.class)
    public ResponseEntity<?> rolNoEncontrado(RolNotFoundException ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<?> usuarioNoEncontrado(UsuarioNotFoundException ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DniCustomException.class)
    public ResponseEntity<?> dniException(DniCustomException ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailCustomException.class)
    public ResponseEntity<?> emailException(EmailCustomException ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(JwtExceptionCustom.class)
    public ResponseEntity<?> jwtCustomException(JwtExceptionCustom ex){
        return  new ResponseEntity<>(errorResponse(ex.getMessage(), HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.value()),HttpStatus.FORBIDDEN);
    }
    public static ErrorDetails errorResponse(String message, HttpStatusCode httpStatusCode, Integer codigoStatus){
        return new ErrorDetails(
                codigoStatus,
                message,
                httpStatusCode
        );
    }
}
