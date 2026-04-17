package rus.cheremisin.itktasksspringmvc.exception;

import java.io.IOException;

public class ObjectToJsonMappingException extends RuntimeException {
    public ObjectToJsonMappingException(String message) {
        super(message);
    }
}
