package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.application.mappers.TransferenciaMapper;
import br.com.banco.domain.exception.SaldoNaoEncontradoException;
import br.com.banco.domain.exception.TransferenciaInvalidaException;
import br.com.banco.domain.exception.TransferenciaNaoEncontradaException;
import br.com.banco.domain.models.Transferencia;
import br.com.banco.domain.ports.repositories.ITransferenciaRepositoryPort;
import br.com.banco.infrastructure.adaptadores.persistence.entities.TransferenciaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TransferenciaRepositoryImpl implements ITransferenciaRepositoryPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaRepositoryImpl.class);

    private final SpringTransferenciaRepository transferenciaRepository;
    private final TransferenciaMapper transferenciaMapper;

    public TransferenciaRepositoryImpl(SpringTransferenciaRepository transferenciaRepository, TransferenciaMapper transferenciaMapper) {
        this.transferenciaRepository = transferenciaRepository;
        this.transferenciaMapper = transferenciaMapper;
    }

    @Override
    public Page<TransferenciaDTO> obterTodasTransferencias(final Pageable pageable) {
        try {
            LOGGER.info("Obtendo todas as transferencias");
            Page<TransferenciaEntity> transferenciaEntities = transferenciaRepository.findAll(pageable);

            if (transferenciaEntities.isEmpty()) {
                throw new TransferenciaInvalidaException("Transferencia nao concentrate");
            }

            return transferenciaMapper.mapToDtoPage(transferenciaEntities);
        } catch (TransferenciaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter todas as transferências", e);
            throw new TransferenciaNaoEncontradaException("Erro ao obter todas as transferências");
        }
    }

    @Override
    public Optional<Transferencia> obterTransferenciaPorId(final int id) {
        try {
            LOGGER.info("Obtendo transferência por ID: {}", id);
            Optional<TransferenciaEntity> transferenciaEntity = transferenciaRepository.findById(id);

            if (transferenciaEntity.isEmpty()) {
                throw new TransferenciaNaoEncontradaException("Transferencia nao encontradas");
            }

            return transferenciaEntity.map(transferenciaMapper::mapEntityToModel);
        } catch (TransferenciaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter transferência por ID: {}", id, e);
            throw new TransferenciaNaoEncontradaException("Erro ao obter transferência por ID: " + id);
        }
    }

    @Override
    public Page<TransferenciaDTO> obterTransferenciasPorPeriodo(final Pageable pageable, final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo transferências por período: {} a {}", dataInicial, dataFinal);
            Page<TransferenciaEntity> transferenciaEntities = transferenciaRepository.findByDataTransferenciaBetween(pageable, dataInicial, dataFinal);

            if (transferenciaEntities.isEmpty()) {
                throw new TransferenciaInvalidaException("Transferencia nao encontrada");
            }

            return transferenciaMapper.mapToDtoPage(transferenciaEntities);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferências por período: {} a {}", dataInicial, dataFinal);
            throw new TransferenciaInvalidaException("Erro ao obter transferências por período");
        }
    }

    @Override
    public Page<TransferenciaDTO> obterTransferenciasPorOperadorEData(final Pageable pageable, final String nomeOperador, final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo transferências por operador: {} e período: {} a {}", nomeOperador, dataInicial, dataFinal);
            Page<TransferenciaEntity> transferenciaEntities = transferenciaRepository.findByNomeOperadorTransacaoAndDataTransferenciaBetween(pageable, nomeOperador, dataInicial, dataFinal);

            if (transferenciaEntities.isEmpty()) {
                throw new TransferenciaInvalidaException("Transferencia nao encontrada");
            }

            return transferenciaMapper.mapToDtoPage(transferenciaEntities);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferências por operador: {} e período: {} a {}", nomeOperador, dataInicial, dataFinal);
            throw new TransferenciaInvalidaException("Erro ao obter transferências por operador e período");
        }
    }

    @Override
    public Page<TransferenciaDTO> obterTransferenciasPorOperador(final Pageable pageable, final String nomeOperador) {
        try {
            LOGGER.info("Obtendo transferências por operador: {}", nomeOperador);
            Page<TransferenciaEntity> transferenciaEntities = transferenciaRepository.findByNomeOperadorTransacao(pageable, nomeOperador);

            if (transferenciaEntities.isEmpty()) {
                throw new TransferenciaInvalidaException("Transferencia nao encontrada");
            }

            return transferenciaMapper.mapToDtoPage(transferenciaEntities);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferências por operador: {}", nomeOperador);
            throw new TransferenciaInvalidaException("Erro ao obter transferências por operador");
        }
    }

    @Override
    public Page<TransferenciaDTO> obterTransferenciasPorConta(final Pageable pageable, final int idConta) {
        try {
            LOGGER.info("Obtendo transferências por conta com ID: {}", idConta);
            Page<TransferenciaEntity> transferenciaEntities = transferenciaRepository.findByContaId(pageable, idConta);

            if (transferenciaEntities.isEmpty()) {
                throw new TransferenciaInvalidaException("Transferência não encontrada");
            }

            return transferenciaMapper.mapToDtoPage(transferenciaEntities);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferências por conta com ID: {}", idConta);
            throw new TransferenciaInvalidaException("Erro ao obter transferências por conta");
        }
    }

    @Override
    public BigDecimal obterSaldoTotalPorPeriodo(final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo saldo total por periodo: {} a {}", dataInicial, dataFinal);
            return transferenciaRepository.sumValorByDataTransferenciaBetween(dataInicial, dataFinal);
        } catch (SaldoNaoEncontradoException e) {
            LOGGER.error("Erro ao obter saldo total por periodo: {} a {}", dataInicial, dataFinal);
            throw new SaldoNaoEncontradoException("Erro ao obter saldo total por periodo");
        }
    }

    @Override
    public BigDecimal obterSaldoTotalPorNumeroConta(final int numeroConta) {
        try {
            LOGGER.info("Obtendo saldo total por operador: {}", numeroConta);
            return transferenciaRepository.sumValorByOperadorTransacao(numeroConta);
        } catch (SaldoNaoEncontradoException e) {
            LOGGER.error("Erro ao obter saldo total por operador: {}", numeroConta);
            throw new SaldoNaoEncontradoException("Erro ao obter saldo total por operador");
        }
    }

    @Override
    public void salvar(final Transferencia transferencia) {
        try {
            LOGGER.info("Salvando transferência");
            TransferenciaEntity transferenciaEntity = transferenciaMapper.mapModelToEntity(transferencia);
            transferenciaRepository.save(transferenciaEntity);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao salvar transferência");
            throw new TransferenciaInvalidaException("Erro ao salvar transferência");
        }
    }

    @Override
    public void deletar(final Transferencia transferencia) {
        try {
            LOGGER.info("Deletando transferência com ID: {}", transferencia.getId());
            TransferenciaEntity transferenciaEntity = transferenciaMapper.mapModelToEntity(transferencia);
            transferenciaRepository.delete(transferenciaEntity);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao deletar transferência com ID: {}", transferencia.getId());
            throw new TransferenciaInvalidaException("Erro ao deletar transferência com ID: " + transferencia.getId());
        }
    }
}