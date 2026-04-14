package rus.cheremisin.itktasksspringmvc.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rus.cheremisin.itktasksspringmvc.exceptions.AuthorNotFoundException;
import rus.cheremisin.itktasksspringmvc.exceptions.BookNotFoundException;
import rus.cheremisin.itktasksspringmvc.exceptions.GenreListIsNullException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException() {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFoundException(AuthorNotFoundException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(
            ConstraintViolationException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(GenreListIsNullException.class)
    public ResponseEntity<String> handleShoppingOrderListIsNullException(
            GenreListIsNullException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }



}