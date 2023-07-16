package br.com.banco.domain.adaptadores.services;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.application.mappers.TransferenciaMapper;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.SaldoNaoEncontradoException;
import br.com.banco.domain.exception.TransferenciaInvalidaException;
import br.com.banco.domain.exception.TransferenciaNaoEncontradaException;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.models.Transferencia;
import br.com.banco.domain.ports.interfaces.ITransferenciaServicePort;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import br.com.banco.domain.ports.repositories.ITransferenciaRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


public class TransferenciaServiceImpl implements ITransferenciaServicePort {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaServiceImpl.class);

    private final ITransferenciaRepositoryPort transferenciaRepository;
    private final TransferenciaMapper transferenciaMapper;
    private final IContaRepositoryPort contaRepository;

    public TransferenciaServiceImpl(ITransferenciaRepositoryPort transferenciaRepository,
                                    TransferenciaMapper transferenciaMapper,
                                    IContaRepositoryPort contaRepository) {
        this.transferenciaRepository = transferenciaRepository;
        this.transferenciaMapper = transferenciaMapper;
        this.contaRepository = contaRepository;
    }

    @Override
    public ResponseEntity<Page<TransferenciaDTO>> obterTodasTransferencias(final Pageable pageable) {
        try {
            LOGGER.info("Obtendo todas as transferencias");
            Page<TransferenciaDTO> transferencias = transferenciaRepository.obterTodasTransferencias(pageable);
            return ResponseEntity.ok(transferencias);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter todas as transferencias");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao obter todas as transferencias.");
        }
    }

    @Override
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciaPorOperadorEPorData(final Pageable pageable,
                                                                                        final String nomeOperador,
                                                                                        final LocalDateTime dataInicial,
                                                                                        final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo transferencias por operador e data");
            Page<TransferenciaDTO> transferencias;

            if (nomeOperador != null && dataInicial != null && dataFinal != null) {
                transferencias = transferenciaRepository.obterTransferenciasPorOperadorEData(pageable, nomeOperador,
                        dataInicial, dataFinal);
            } else if (nomeOperador != null) {
                transferencias = transferenciaRepository.obterTransferenciasPorOperador(pageable, nomeOperador);
            } else if (dataInicial != null && dataFinal != null) {
                transferencias = transferenciaRepository.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);
            } else {
                transferencias = transferenciaRepository.obterTodasTransferencias(pageable);
            }

            return ResponseEntity.ok(transferencias);
        } catch (TransferenciaNaoEncontradaException e) {
            LOGGER.error("Transferência não encontrada", e);
            throw new TransferenciaNaoEncontradaException("Transferência não encontrada.");
        }
    }

    @Override
    public ResponseEntity<TransferenciaDTO> obterTransferenciaPorId(final int id) {
        try {
            LOGGER.info("Obtendo transferencia por ID: {}", id);
            Optional<Transferencia> transferencia = transferenciaRepository.obterTransferenciaPorId(id);

            if (transferencia.isPresent()) {
                TransferenciaDTO transferenciaDTO = transferenciaMapper.mapModelToDto(transferencia.get());
                return ResponseEntity.ok(transferenciaDTO);
            }

            throw new TransferenciaNaoEncontradaException("Transferencia não encontrada");
        } catch (TransferenciaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter transferencia por ID: {}", id, e);
            throw new TransferenciaNaoEncontradaException("Transferência não encontrada.");
        }
    }

    @Override
    public ResponseEntity<TransferenciaDTO> realizarTransferencia(@Valid final TransferenciaDTO transferenciaDTO,
                                                                  final int numeroConta, final int numeroContaOrigem) {
        try {
            LOGGER.info("Realizando transferencia");
            Optional<Conta> contaDestinatarioOpt = contaRepository.obterContaPorId(numeroConta);
            Optional<Conta> contaOrigemOpt = contaRepository.obterContaPorId(numeroContaOrigem);

            if (contaDestinatarioOpt.isEmpty() || contaOrigemOpt.isEmpty()) {
                throw new ContaNaoEncontradaException("A conta de destino não foi encontrada.");
            }

            Conta contaDestinatario = contaDestinatarioOpt.get();
            Conta contaOrigem = contaOrigemOpt.get();

            Transferencia transferencia = transferenciaMapper.mapTransferencia(transferenciaDTO,
                    contaDestinatario, contaOrigem);

            transferenciaRepository.salvar(transferencia);

            TransferenciaDTO transferenciaDTO1 = transferenciaMapper.mapModelToDto(transferencia);

            LOGGER.info("Transferência realizada com sucesso");

            return ResponseEntity.ok(transferenciaDTO1);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao realizar a transferencia");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao realizar a transferência.");
        }
    }

    @Override
    public ResponseEntity<TransferenciaDTO> realizarDeposito(@Valid final TransferenciaDTO transferenciaDTO, final int numeroConta) {
        try {
            LOGGER.info("Realizando deposito");
            Optional<Conta> contaDestinatarioOpt = contaRepository.obterContaPorId(numeroConta);

            if (contaDestinatarioOpt.isEmpty()) {
                throw new ContaNaoEncontradaException("A conta de destino não foi encontrada.");
            }

            Conta contaDestinatario = contaDestinatarioOpt.get();

            Transferencia deposito = transferenciaMapper.mapTransferenciaDeposito(transferenciaDTO,
                    contaDestinatario);

            transferenciaRepository.salvar(deposito);

            TransferenciaDTO transferenciaDTO1 = transferenciaMapper.mapModelToDto(deposito);

            LOGGER.info("Deposito realizado com sucesso");

            return ResponseEntity.ok(transferenciaDTO1);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao realizar o depósito");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao realizar o depósito.");
        }
    }

    @Override
    public ResponseEntity<TransferenciaDTO> realizarSaque(@Valid final TransferenciaDTO transferenciaDTO, final int numeroConta) {
        try {
            LOGGER.info("Realizando saque");
            Optional<Conta> contaOpt = contaRepository.obterContaPorId(numeroConta);

            if (contaOpt.isEmpty()) {
                throw new ContaNaoEncontradaException("Conta não encontrada.");
            }

            Conta conta = contaOpt.get();

            Transferencia saque = transferenciaMapper.mapTransferenciaSaque(transferenciaDTO, conta);

            transferenciaRepository.salvar(saque);

            TransferenciaDTO transferenciaDTO1 = transferenciaMapper.mapModelToDto(saque);

            LOGGER.info("Saque realizado com sucesso");

            return ResponseEntity.ok(transferenciaDTO1);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao realizar o saque");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao realizar o saque.");
        }
    }

    @Override
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorPeriodo(final Pageable pageable,
                                                                                final LocalDateTime dataInicial,
                                                                                final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo transferências por período");
            Page<TransferenciaDTO> transferencias = transferenciaRepository.obterTransferenciasPorPeriodo(pageable,
                    dataInicial, dataFinal);

            return ResponseEntity.ok(transferencias);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferências por período");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao obter as transferências por período.");
        }
    }

    @Override
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorOperador(final Pageable pageable,
                                                                                 final String nomeOperador) {
        try {
            LOGGER.info("Obtendo transferências por operador");
            Page<TransferenciaDTO> transferencias = transferenciaRepository.obterTransferenciasPorOperador(pageable,
                    nomeOperador);

            return ResponseEntity.ok(transferencias);
        } catch (TransferenciaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter transferencias por operador");
            throw new TransferenciaNaoEncontradaException("Ocorreu um erro ao obter as transferências por operador.");
        }
    }

    @Override
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorConta(final Pageable pageable, final int idConta) {
        try {
            LOGGER.info("Obtendo transferencias por conta");
            Page<TransferenciaDTO> transferencias = transferenciaRepository.obterTransferenciasPorConta(pageable,
                    idConta);

            return ResponseEntity.ok(transferencias);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter transferencias por conta");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao obter as transferencias por conta.");
        }
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoTotalPorPeriodo(final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        try {
            LOGGER.info("Obtendo saldo total por período");
            BigDecimal valorTotal = transferenciaRepository.obterSaldoTotalPorPeriodo(dataInicial, dataFinal);

            return ResponseEntity.ok(valorTotal);
        } catch (TransferenciaInvalidaException e) {
            LOGGER.error("Erro ao obter saldo total por periodo");
            throw new TransferenciaInvalidaException("Ocorreu um erro ao obter o saldo total por periodo.");
        }
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoTotalPorNumeroConta(final int numeroConta) {
        try {
            LOGGER.info("Obtendo saldo total por operador");
            BigDecimal valorTotal = transferenciaRepository.obterSaldoTotalPorNumeroConta(numeroConta);

            return ResponseEntity.ok(valorTotal);
        } catch (SaldoNaoEncontradoException e) {
            LOGGER.error("Erro ao obter saldo total por operador", e);
            throw new SaldoNaoEncontradoException("Ocorreu um erro ao obter o saldo total por operador.");
        }
    }
}