package br.com.banco.domain.exception;

public class SaldoNaoEncontradoException extends RuntimeException {
    public SaldoNaoEncontradoException(String message) {
        super(message);
    }
}