package rus.cheremisin.itktasksspringmvc.exception;

import java.io.IOException;

public class JsonToObjectMappingException extends RuntimeException {
    public JsonToObjectMappingException(String message) {
        super(message);
    }
}
