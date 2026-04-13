package rus.cheremisin.itktasksspringmvc.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rus.cheremisin.itktasksspringmvc.exception.NoCustomerFoundException;
import rus.cheremisin.itktasksspringmvc.exception.NoOrderFoundException;

@ControllerAdvice
public class MyExceptionHandler {
    //  Rus Cheremisin, все реализации - плейсхолдеры, поэтому и одинаковые

    @ExceptionHandler(NoCustomerFoundException.class)
    public void handleNoCustomerFoundException(NoCustomerFoundException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

    @ExceptionHandler(NoOrderFoundException.class)
    public void handleNoOrderFoundException(NoOrderFoundException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException(NullPointerException ex) {
        System.out.println(ex.fillInStackTrace() + "\n" + ex.getMessage());
    }

}
