package br.com.texoit.pablohenrique.exception;

public class PabloHenriqueException extends Exception {
    public PabloHenriqueException(String message) {
        this(message, null);
    }
    public PabloHenriqueException(String message, Exception e) {
        super(message, e);
    }
}
