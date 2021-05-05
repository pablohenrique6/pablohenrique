package br.com.texoit.pablohenrique.exception;

public class PabloHenriquePersistenceException extends PabloHenriqueException {
    public PabloHenriquePersistenceException(String message) {
        this(message, null);
    }
    public PabloHenriquePersistenceException(String message, Exception e) {
        super(message, e);
    }
}
