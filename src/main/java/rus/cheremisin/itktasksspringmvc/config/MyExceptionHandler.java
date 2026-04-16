package rus.cheremisin.itktasksspringmvc.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rus.cheremisin.itktasksspringmvc.exception.*;

import java.sql.SQLIntegrityConstraintViolationException;

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

    @ExceptionHandler(OrderListIsNullException.class)
    public ResponseEntity<String> handleShoppingOrderListIsNullException(
            OrderListIsNullException ex) {

        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(JsonToObjectMappingException.class)
    public ResponseEntity<String> handleJsonToObjectMappingException(JsonToObjectMappingException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(ObjectToJsonMappingException.class)
    public ResponseEntity<String> handleObjectToJsonMappingException(ObjectToJsonMappingException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }



}
