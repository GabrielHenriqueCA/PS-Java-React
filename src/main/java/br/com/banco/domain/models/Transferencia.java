package br.com.banco.domain.models;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.enums.TipoTransferencia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Transferencia {

    private int id;

    private LocalDateTime dataTransferencia;

    private BigDecimal valor;

    private TipoTransferencia tipo;

    private String nomeOperadorTransacao;

    private Conta contaId;

    public Transferencia() {
    }

    public Transferencia(TransferenciaDTO transferenciaDTO) {
        this.dataTransferencia = transferenciaDTO.getDataTransferencia();
        this.valor = transferenciaDTO.getValor();
        this.tipo = transferenciaDTO.getTipo();
        this.nomeOperadorTransacao = transferenciaDTO.getNomeOperadorTransacao();
        this.contaId = transferenciaDTO.getConta();
    }

    public Transferencia(int id, LocalDateTime dataTransferencia, BigDecimal valor, TipoTransferencia tipo, String nomeOperadorTransacao, Conta contaId) {
        this.id = id;
        this.dataTransferencia = dataTransferencia;
        this.valor = valor;
        this.tipo = tipo;
        this.nomeOperadorTransacao = nomeOperadorTransacao;
        this.contaId = contaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        if (valor == null) {
            throw new IllegalArgumentException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
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

    public Conta getContaId() {
        return contaId;
    }

    public void setContaId(Conta contaId) {
        this.contaId = contaId;
    }
}
