package br.com.banco.infrastructure.config;

import br.com.banco.domain.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<Object> handleContaNaoEncontradaException(ContaNaoEncontradaException ex, WebRequest request) {
        String message = "Conta nao encontrada";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Object> handleSaldoInsuficienteException(SaldoInsuficienteException ex, WebRequest request) {
        String message = "Saldo insuficiente";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(TransferenciaNaoEncontradaException.class)
    public ResponseEntity<Object> handleTransferenciaNaoEncontradaException(TransferenciaNaoEncontradaException ex, WebRequest request) {
        String message = "Transferencia nao encontrada";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<Object> handleValorInvalidoException(ValorInvalidoException ex, WebRequest request) {
        String message = "Valor invalido";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DeletarContaException.class)
    public ResponseEntity<Object> handleDeletarContaException(DeletarContaException ex, WebRequest request) {
        String message = "Erro ao deletar a conta";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(SalvarContaException.class)
    public ResponseEntity<Object> handleSalvarContaException(SalvarContaException ex, WebRequest request) {
        String message = "Erro ao salvar a conta";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(SaldoNaoEncontradoException.class)
    public ResponseEntity<Object> handleSaldoNaoEncontradoException(SaldoNaoEncontradoException ex, WebRequest request) {
        String message = "Saldo nao encontrado";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(TransferenciaInvalidaException.class)
    public ResponseEntity<Object> handleTransferenciaInvalidaException(TransferenciaInvalidaException ex, WebRequest request) {
        String message = "Erro ao realizar a transaco";
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Houve um erro ao processar sua requisição, tente novamente em instantes ou acione nosso suporte!";
        return super.handleExceptionInternal(ex, message, headers, status, request);
    }
}