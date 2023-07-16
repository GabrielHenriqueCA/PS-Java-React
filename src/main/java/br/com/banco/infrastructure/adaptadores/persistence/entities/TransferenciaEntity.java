package br.com.banco.infrastructure.adaptadores.persistence.entities;

import br.com.banco.domain.enums.TipoTransferencia;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencia")
public class TransferenciaEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "data_transferencia", nullable = false)
    private LocalDateTime dataTransferencia;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private TipoTransferencia tipo;

    @Column(name = "nome_operador_transacao")
    private String nomeOperadorTransacao;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private ContaEntity conta;

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

    public ContaEntity getConta() {
        return conta;
    }

    public void setConta(ContaEntity conta) {
        this.conta = conta;
    }
}