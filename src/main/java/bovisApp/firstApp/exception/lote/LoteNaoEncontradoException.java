package bovisApp.firstApp.exception.lote;

import javax.persistence.EntityNotFoundException;

public class LoteNaoEncontradoException extends EntityNotFoundException {
    public  LoteNaoEncontradoException(String message){
        super(message);
    }
}
