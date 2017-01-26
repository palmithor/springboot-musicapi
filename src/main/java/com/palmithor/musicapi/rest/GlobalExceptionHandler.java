package com.palmithor.musicapi.rest;

import com.palmithor.musicapi.rest.response.ErrorResponse;
import com.palmithor.musicapi.service.MessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * A global exception handler to take care of all exceptions.
 * <p>
 * Sets the correct status code and constructs a response
 *
 * @author palmithor
 * @since 25.1.2017.
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @Autowired MessageByLocaleService messageByLocaleService;


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        ErrorResponse errorResponse = ErrorResponse.create(500, messageByLocaleService.getMessage(MessageByLocaleService.Errors.INTERNAL_SERVER_ERROR));
        return ResponseEntity
                .status(500)
                .body(errorResponse);
    }

}
