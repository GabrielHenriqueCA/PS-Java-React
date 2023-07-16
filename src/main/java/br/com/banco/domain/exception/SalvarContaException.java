package br.com.banco.domain.exception;

public class SalvarContaException extends RuntimeException {
    public SalvarContaException(String message) {
        super(message);
    }
}