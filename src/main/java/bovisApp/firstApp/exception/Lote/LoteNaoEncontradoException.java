package bovisApp.firstApp.exception.Lote;

import javax.persistence.EntityNotFoundException;

public class LoteNaoEncontradoException extends EntityNotFoundException {
    public  LoteNaoEncontradoException(String message){
        super(message);
    }
}
