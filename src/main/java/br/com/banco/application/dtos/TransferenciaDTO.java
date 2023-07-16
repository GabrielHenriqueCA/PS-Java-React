package br.com.banco.application.dtos;

import br.com.banco.domain.enums.TipoTransferencia;
import br.com.banco.domain.models.Conta;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class TransferenciaDTO {
    private LocalDateTime dataTransferencia;

    @NotNull(message = "Valor nao pode ser nulo")
    @Digits(integer = 20, fraction = 2)
    private BigDecimal valor;

    private TipoTransferencia tipo;


    private String nomeOperadorTransacao;

    private Conta conta;

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    public TipoTransferencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransferencia tipo) {
        this.tipo = tipo;
    }

    public String getNomeOperadorTransacao() {
        return nomeOperadorTransacao;
    }

    public void setNomeOperadorTransacao(String nomeOperadorTransacao) {
        this.nomeOperadorTransacao = nomeOperadorTransacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}