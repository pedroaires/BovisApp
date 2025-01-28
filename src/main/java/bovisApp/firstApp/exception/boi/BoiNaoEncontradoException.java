package bovisApp.firstApp.exception.boi;

import javax.persistence.EntityNotFoundException;

public class BoiNaoEncontradoException extends EntityNotFoundException {
    public BoiNaoEncontradoException(String message){
        super(message);
    }
}
