package bovisApp.firstApp.exception;

import javax.persistence.EntityNotFoundException;

public class BoiNotFoundException extends EntityNotFoundException {
    public BoiNotFoundException(Long id) {
        super(String.format("Boi de Id: %d não existe", id));
    }

}
