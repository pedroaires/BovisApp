package bovisApp.firstApp.exception;

import javax.persistence.EntityExistsException;

public class BoiExistsException extends EntityExistsException {
    public BoiExistsException(Long loteId, int numero) {
        super(String.format("Boi com número: %d já existe no Lote de id: %d", numero, loteId));
    }
}
