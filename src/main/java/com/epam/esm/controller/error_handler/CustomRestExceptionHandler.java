package com.epam.esm.controller.error_handler;

import com.epam.esm.controller.exception.AbstractRuntimeException;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GiftEntityNotFoundException.class, WrongParameterFormatException.class})
    public ResponseEntity<ApiError> handleAll(AbstractRuntimeException e, WebRequest request) {
        ApiError apiError = new ApiError(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
