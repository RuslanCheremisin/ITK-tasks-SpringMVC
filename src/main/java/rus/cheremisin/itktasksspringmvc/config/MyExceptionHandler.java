package rus.cheremisin.itktasksspringmvc.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rus.cheremisin.itktasksspringmvc.exception.CustomerNotFoundException;
import rus.cheremisin.itktasksspringmvc.exception.OrderNotFoundException;

@ControllerAdvice
public class MyExceptionHandler {
    //  Rus Cheremisin, все реализации - плейсхолдеры, поэтому и одинаковые

    @ExceptionHandler(CustomerNotFoundException.class)
    public void handleNoCustomerFoundException(CustomerNotFoundException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public void handleNoOrderFoundException(OrderNotFoundException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException(NullPointerException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

}
