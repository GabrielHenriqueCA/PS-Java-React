package br.com.banco.domain.exception;

public class TransferenciaNaoEncontradaException extends RuntimeException {
    public TransferenciaNaoEncontradaException(String message) {
        super(message);
    }
}