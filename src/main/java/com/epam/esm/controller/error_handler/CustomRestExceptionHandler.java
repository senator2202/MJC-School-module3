package com.epam.esm.controller.error_handler;

import com.epam.esm.controller.exception.AbstractRuntimeException;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class is responsible for Exception handling.
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Not found response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(GiftEntityNotFoundException.class)
    public ResponseEntity<ApiError> notFound(AbstractRuntimeException e) {
        ApiError apiError = new ApiError(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Wrong parameters response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(WrongParameterFormatException.class)
    public ResponseEntity<ApiError> wrongParameters(AbstractRuntimeException e) {
        ApiError apiError = new ApiError(e.getMessage(), e.getErrorCode());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
