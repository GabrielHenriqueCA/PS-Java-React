package br.com.banco.domain.ports.repositories;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.models.Transferencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


public interface ITransferenciaRepositoryPort {
    Page<TransferenciaDTO> obterTodasTransferencias(final Pageable pageable);

    Optional<Transferencia> obterTransferenciaPorId(final int id);

    Page<TransferenciaDTO> obterTransferenciasPorPeriodo(final Pageable pageable, final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    Page<TransferenciaDTO> obterTransferenciasPorOperadorEData(final Pageable pageable, final String nomeOperador , final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    Page<TransferenciaDTO> obterTransferenciasPorOperador(final Pageable pageable, final String nomeOperador);

    Page<TransferenciaDTO> obterTransferenciasPorConta(final Pageable pageable, final int idConta);

    BigDecimal obterSaldoTotalPorPeriodo(final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    BigDecimal obterSaldoTotalPorNumeroConta(final int nomeOperador);

    void salvar(final Transferencia transferencia);

    void deletar(final Transferencia transferencia);

}