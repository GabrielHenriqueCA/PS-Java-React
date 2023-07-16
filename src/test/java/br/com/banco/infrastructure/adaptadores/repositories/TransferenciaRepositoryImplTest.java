package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.application.mappers.TransferenciaMapper;
import br.com.banco.domain.exception.SaldoNaoEncontradoException;
import br.com.banco.domain.exception.TransferenciaInvalidaException;
import br.com.banco.domain.exception.TransferenciaNaoEncontradaException;
import br.com.banco.domain.models.Transferencia;
import br.com.banco.domain.ports.repositories.ITransferenciaRepositoryPort;
import br.com.banco.infrastructure.adaptadores.persistence.entities.TransferenciaEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaRepositoryImplTest {

    private final SpringTransferenciaRepository transferenciaRepository = mock(SpringTransferenciaRepository.class);
    private final TransferenciaMapper transferenciaMapper = mock(TransferenciaMapper.class);
    private final ITransferenciaRepositoryPort repositorioTransferencia = new TransferenciaRepositoryImpl(transferenciaRepository, transferenciaMapper);

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaRepositoryImplTest.class);

    @Test
    void obterTodasTransferencias_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaEntity> transferenciaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);

        when(transferenciaRepository.findAll(pageable)).thenReturn(transferenciaEntityMock);
        when(transferenciaMapper.mapToDtoPage(transferenciaEntityMock)).thenReturn(transferenciasMock);

        Page<TransferenciaDTO> response = repositorioTransferencia.obterTodasTransferencias(pageable);

        assertEquals(transferenciasMock, response);
        verify(transferenciaRepository, times(1)).findAll(pageable);
        verify(transferenciaMapper, times(1)).mapToDtoPage(transferenciaEntityMock);
    }


    @Test
    void obterTodasTransferencias_ComExcecao_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);

        when(transferenciaRepository.findAll(pageable)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaNaoEncontradaException.class, () -> {
            repositorioTransferencia.obterTodasTransferencias(pageable);
        });

        verify(transferenciaRepository, times(1)).findAll(pageable);
    }

    @Test
    void obterTransferenciaPorId_ComIdExistente_RetornaTransferencia() {
        int id = 1;
        TransferenciaEntity transferenciaEntityMock = mock(TransferenciaEntity.class);
        Transferencia transferenciaMock = mock(Transferencia.class);

        when(transferenciaRepository.findById(id)).thenReturn(Optional.of(transferenciaEntityMock));
        when(transferenciaMapper.mapEntityToModel(transferenciaEntityMock)).thenReturn(transferenciaMock);

        Optional<Transferencia> result = repositorioTransferencia.obterTransferenciaPorId(id);

        assertTrue(result.isPresent());
        assertEquals(transferenciaMock, result.get());
        verify(transferenciaRepository, times(1)).findById(id);
        verify(transferenciaMapper, times(1)).mapEntityToModel(transferenciaEntityMock);
    }

    @Test
    void obterTransferenciaPorId_ComExcecao_RetornaTransferenciaNaoEncontrada() {
        int id = 1;

        when(transferenciaRepository.findById(id)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaNaoEncontradaException.class, () -> {
            repositorioTransferencia.obterTransferenciaPorId(id);
        });

        verify(transferenciaRepository, times(1)).findById(id);
    }

    @Test
    void obterTransferenciasPorPeriodo_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaEntity> transferenciaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();

        when(transferenciaRepository.findByDataTransferenciaBetween(pageable, dataInicial, dataFinal)).thenReturn(transferenciaEntityMock);
        when(transferenciaMapper.mapToDtoPage(transferenciaEntityMock)).thenReturn(transferenciasMock);

        Page<TransferenciaDTO> response = repositorioTransferencia.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);

        assertEquals(transferenciasMock, response);
        verify(transferenciaRepository, times(1)).findByDataTransferenciaBetween(pageable, dataInicial, dataFinal);
        verify(transferenciaMapper, times(1)).mapToDtoPage(transferenciaEntityMock);
    }


    @Test
    void obterTransferenciasPorPeriodo_ComExcecao_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();

        when(transferenciaRepository.findByDataTransferenciaBetween(pageable, dataInicial, dataFinal)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);
        });

        verify(transferenciaRepository, times(1)).findByDataTransferenciaBetween(pageable, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciasPorOperadorEData_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaEntity> transferenciaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();
        String nomeOperador = "Operador";

        when(transferenciaRepository.findByNomeOperadorTransacaoAndDataTransferenciaBetween(pageable, nomeOperador, dataInicial, dataFinal)).thenReturn(transferenciaEntityMock);
        when(transferenciaMapper.mapToDtoPage(transferenciaEntityMock)).thenReturn(transferenciasMock);

        Page<TransferenciaDTO> response = repositorioTransferencia.obterTransferenciasPorOperadorEData(pageable, nomeOperador, dataInicial, dataFinal);

        assertEquals(transferenciasMock, response);
        verify(transferenciaRepository, times(1)).findByNomeOperadorTransacaoAndDataTransferenciaBetween(pageable, nomeOperador, dataInicial, dataFinal);
        verify(transferenciaMapper, times(1)).mapToDtoPage(transferenciaEntityMock);
    }


    @Test
    void obterTransferenciasPorOperadorEData_ComExcecao_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();
        String nomeOperador = "Operador";

        when(transferenciaRepository.findByNomeOperadorTransacaoAndDataTransferenciaBetween(pageable, nomeOperador, dataInicial, dataFinal)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.obterTransferenciasPorOperadorEData(pageable, nomeOperador, dataInicial, dataFinal);
        });

        verify(transferenciaRepository, times(1)).findByNomeOperadorTransacaoAndDataTransferenciaBetween(pageable, nomeOperador, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciasPorOperador_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaEntity> transferenciaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        String nomeOperador = "Operador";

        when(transferenciaRepository.findByNomeOperadorTransacao(pageable, nomeOperador)).thenReturn(transferenciaEntityMock);
        when(transferenciaMapper.mapToDtoPage(transferenciaEntityMock)).thenReturn(transferenciasMock);

        Page<TransferenciaDTO> response = repositorioTransferencia.obterTransferenciasPorOperador(pageable, nomeOperador);

        assertEquals(transferenciasMock, response);
        verify(transferenciaRepository, times(1)).findByNomeOperadorTransacao(pageable, nomeOperador);
        verify(transferenciaMapper, times(1)).mapToDtoPage(transferenciaEntityMock);
    }


    @Test
    void obterTransferenciasPorOperador_ComExcecao_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);
        String nomeOperador = "Operador";

        when(transferenciaRepository.findByNomeOperadorTransacao(pageable, nomeOperador)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.obterTransferenciasPorOperador(pageable, nomeOperador);
        });

        verify(transferenciaRepository, times(1)).findByNomeOperadorTransacao(pageable, nomeOperador);
    }

    @Test
    void obterTransferenciasPorConta_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaEntity> transferenciaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        int idConta = 1;

        when(transferenciaRepository.findByContaId(pageable, idConta)).thenReturn(transferenciaEntityMock);
        when(transferenciaMapper.mapToDtoPage(transferenciaEntityMock)).thenReturn(transferenciasMock);

        Page<TransferenciaDTO> response = repositorioTransferencia.obterTransferenciasPorConta(pageable, idConta);

        assertEquals(transferenciasMock, response);
        verify(transferenciaRepository, times(1)).findByContaId(pageable, idConta);
        verify(transferenciaMapper, times(1)).mapToDtoPage(transferenciaEntityMock);
    }


    @Test
    void obterTransferenciasPorConta_ComExcecao_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);
        int idConta = 1;

        when(transferenciaRepository.findByContaId(pageable, idConta)).thenThrow(RuntimeException.class);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.obterTransferenciasPorConta(pageable, idConta);
        });

        verify(transferenciaRepository, times(1)).findByContaId(pageable, idConta);
    }

    @Test
    void obterSaldoTotalPorPeriodo_ComValoresValidos_RetornaSaldoTotal() {
        BigDecimal saldoMock = BigDecimal.valueOf(1000);
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();

        when(transferenciaRepository.sumValorByDataTransferenciaBetween(dataInicial, dataFinal)).thenReturn(saldoMock);

        BigDecimal result = repositorioTransferencia.obterSaldoTotalPorPeriodo(dataInicial, dataFinal);

        assertEquals(saldoMock, result);
        verify(transferenciaRepository, times(1)).sumValorByDataTransferenciaBetween(dataInicial, dataFinal);
    }

    @Test
    void obterSaldoTotalPorPeriodo_ComExcecao_RetornaSaldoNaoEncontrado() {
        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = LocalDateTime.now();

        when(transferenciaRepository.sumValorByDataTransferenciaBetween(dataInicial, dataFinal)).thenThrow(RuntimeException.class);

        assertThrows(SaldoNaoEncontradoException.class, () -> {
            repositorioTransferencia.obterSaldoTotalPorPeriodo(dataInicial, dataFinal);
        });

        verify(transferenciaRepository, times(1)).sumValorByDataTransferenciaBetween(dataInicial, dataFinal);
    }

    @Test
    void obterSaldoTotalPorNomeOperador_ComNomeOperadorValido_RetornaSaldoTotal() {
        BigDecimal saldoMock = BigDecimal.valueOf(1000);
        int numeroConta = 1;

        when(transferenciaRepository.sumValorByOperadorTransacao(numeroConta)).thenReturn(saldoMock);

        BigDecimal result = repositorioTransferencia.obterSaldoTotalPorNumeroConta(numeroConta);

        assertEquals(saldoMock, result);
        verify(transferenciaRepository, times(1)).sumValorByOperadorTransacao(numeroConta);
    }

    @Test
    void obterSaldoTotalPorNomeOperador_ComExcecao_RetornaSaldoNaoEncontrado() {
        int numeroConta = 1;

        when(transferenciaRepository.sumValorByOperadorTransacao(numeroConta)).thenThrow(RuntimeException.class);

        assertThrows(SaldoNaoEncontradoException.class, () -> {
            repositorioTransferencia.obterSaldoTotalPorNumeroConta(numeroConta);
        });

        verify(transferenciaRepository, times(1)).sumValorByOperadorTransacao(numeroConta);
    }

    @Test
    void salvar_ComTransferenciaValida_SalvaTransferencia() {
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaEntity transferenciaEntityMock = mock(TransferenciaEntity.class);

        when(transferenciaMapper.mapModelToEntity(transferenciaMock)).thenReturn(transferenciaEntityMock);

        repositorioTransferencia.salvar(transferenciaMock);

        verify(transferenciaRepository, times(1)).save(transferenciaEntityMock);
    }

    @Test
    void salvar_ComExcecao_RetornaTransferenciaInvalida() {
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaEntity transferenciaEntityMock = mock(TransferenciaEntity.class);

        when(transferenciaMapper.mapModelToEntity(transferenciaMock)).thenReturn(transferenciaEntityMock);

        doThrow(RuntimeException.class).when(transferenciaRepository).save(transferenciaEntityMock);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.salvar(transferenciaMock);
        });

        verify(transferenciaMapper, times(1)).mapModelToEntity(transferenciaMock);
        verify(transferenciaRepository, times(1)).save(transferenciaEntityMock);
    }


    @Test
    void deletar_ComTransferenciaValida_DeletaTransferencia() {
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaEntity transferenciaEntityMock = mock(TransferenciaEntity.class);

        when(transferenciaMapper.mapModelToEntity(transferenciaMock)).thenReturn(transferenciaEntityMock);

        repositorioTransferencia.deletar(transferenciaMock);

        verify(transferenciaRepository, times(1)).delete(transferenciaEntityMock);
    }

    @Test
    void deletar_ComExcecao_RetornaTransferenciaInvalida() {
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaEntity transferenciaEntityMock = mock(TransferenciaEntity.class);

        when(transferenciaMapper.mapModelToEntity(transferenciaMock)).thenReturn(transferenciaEntityMock);
        doThrow(RuntimeException.class).when(transferenciaRepository).delete(transferenciaEntityMock);

        assertThrows(TransferenciaInvalidaException.class, () -> {
            repositorioTransferencia.deletar(transferenciaMock);
        });

        verify(transferenciaRepository, times(1)).delete(any(TransferenciaEntity.class));
    }
}
