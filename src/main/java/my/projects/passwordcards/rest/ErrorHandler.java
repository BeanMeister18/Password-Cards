package my.projects.passwordcards.rest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    // Handle with no content returned
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResponse> onIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse err = new ErrorResponse(e.getMessage(), BAD_REQUEST);
        return ResponseEntity.badRequest().body(err);
    }

    // Return 404 when jpa repo does not return a single row
    @ExceptionHandler({NoSuchElementException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(NOT_FOUND)
    void onNoSuchElementException() {}

    // Catch all handler with the exception as content
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> onException(Exception e) {
        ErrorResponse err = new ErrorResponse(e.getCause().getMessage(), INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(err);
    }

    public class ErrorResponse {

        private String message;
        private HttpStatus statusCode;

        public ErrorResponse(String message, HttpStatus statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }
    }
}
