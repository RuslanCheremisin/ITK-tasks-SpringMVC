package rus.cheremisin.itktasksspringmvc.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.exception.OrderNotFoundException;
import rus.cheremisin.itktasksspringmvc.exception.ShoppingOrderListIsNullException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleNoCustomerFoundException() {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleNoOrderFoundException(OrderNotFoundException ex) {
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

    @ExceptionHandler(ShoppingOrderListIsNullException.class)
    public ResponseEntity<String> handleShoppingOrderListIsNullException(
            ShoppingOrderListIsNullException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }



}
