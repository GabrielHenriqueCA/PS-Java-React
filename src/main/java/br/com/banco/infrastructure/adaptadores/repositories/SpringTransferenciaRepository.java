package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.infrastructure.adaptadores.persistence.entities.TransferenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface SpringTransferenciaRepository extends JpaRepository<TransferenciaEntity, Integer> {
    Page<TransferenciaEntity> findByTipo(final Pageable pageable, final String tipo);

    Page<TransferenciaEntity> findByNomeOperadorTransacao(final Pageable pageable, final String nomeOperadorTransacao);

    Page<TransferenciaEntity> findByContaId(final Pageable pageable, final Integer contaId);

    Page<TransferenciaEntity> findByDataTransferenciaBetween(final Pageable pageable, final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    Page<TransferenciaEntity> findByNomeOperadorTransacaoAndDataTransferenciaBetween(final Pageable pageable, final String nomeOperador, final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    @Query("SELECT SUM(t.valor) FROM TransferenciaEntity t WHERE t.dataTransferencia BETWEEN :startDate AND :endDate AND t.tipo <> 1")
    BigDecimal sumValorByDataTransferenciaBetween(@Param("startDate") final LocalDateTime startDate, @Param("endDate") final LocalDateTime endDate);

    @Query("SELECT SUM(t.valor) FROM TransferenciaEntity t WHERE t.conta.id = :operador AND t.tipo <> 1")
    BigDecimal sumValorByOperadorTransacao(@Param("operador") final int operador);
}