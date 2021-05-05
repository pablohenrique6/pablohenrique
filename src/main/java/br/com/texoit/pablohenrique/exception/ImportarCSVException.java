package br.com.texoit.pablohenrique.exception;

public class ImportarCSVException extends PabloHenriqueException {
    public ImportarCSVException(String message) {
        this(message, null);
    }
    public ImportarCSVException(String message, Exception e) {
        super(message, e);
    }
}
