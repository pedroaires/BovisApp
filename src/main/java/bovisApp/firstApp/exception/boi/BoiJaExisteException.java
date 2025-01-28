package bovisApp.firstApp.exception.boi;

import javax.persistence.EntityExistsException;

public class BoiJaExisteException extends EntityExistsException {
    public BoiJaExisteException(String message){
        super(message);
    }
}
