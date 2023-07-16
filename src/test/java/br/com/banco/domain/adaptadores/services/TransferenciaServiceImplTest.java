package br.com.banco.domain.adaptadores.services;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.application.mappers.TransferenciaMapper;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.models.Transferencia;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import br.com.banco.domain.ports.repositories.ITransferenciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TransferenciaServiceImplTest {

    private TransferenciaServiceImpl servicoTransferencia;

    @Mock
    private ITransferenciaRepositoryPort repositorioTransferencia;

    @Mock
    private TransferenciaMapper mapperTransferencia;

    @Mock
    private IContaRepositoryPort repositorioConta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        servicoTransferencia = new TransferenciaServiceImpl(repositorioTransferencia, mapperTransferencia, repositorioConta);
    }

    @Test
    void obterTodasTransferencias_ComPageableValido_RetornaTransferencias() {
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(repositorioTransferencia.obterTodasTransferencias(pageable)).thenReturn(transferenciasMock);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTodasTransferencias(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciasMock, response.getBody());
        verify(repositorioTransferencia, times(1)).obterTodasTransferencias(pageable);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComParametrosValidos_RetornaTransferencias() {
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        String nomeOperador = "Operador";
        LocalDateTime dataInicial = LocalDateTime.now().minusDays(7);
        LocalDateTime dataFinal = LocalDateTime.now();

        when(repositorioTransferencia.obterTransferenciasPorOperadorEData(pageable, nomeOperador, dataInicial, dataFinal))
                .thenReturn(transferenciasMock);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTransferenciaPorOperadorEPorData(
                pageable, nomeOperador, dataInicial, dataFinal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciasMock, response.getBody());
        verify(repositorioTransferencia, times(1))
                .obterTransferenciasPorOperadorEData(pageable, nomeOperador, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComParametrosNulos_RetornaTransferencias() {
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(repositorioTransferencia.obterTodasTransferencias(pageable)).thenReturn(transferenciasMock);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTransferenciaPorOperadorEPorData(
                pageable, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciasMock, response.getBody());
        verify(repositorioTransferencia, times(1)).obterTodasTransferencias(pageable);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComApenasNomeOperador_RetornaTransferencias() {
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        String nomeOperador = "Operador";

        when(repositorioTransferencia.obterTransferenciasPorOperador(pageable, nomeOperador))
                .thenReturn(transferenciasMock);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTransferenciaPorOperadorEPorData(
                pageable, nomeOperador, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciasMock, response.getBody());
        verify(repositorioTransferencia, times(1)).obterTransferenciasPorOperador(pageable, nomeOperador);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComApenasDataInicialEDataFinal_RetornaTransferencias() {
        Page<TransferenciaDTO> transferenciasMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        LocalDateTime dataInicial = LocalDateTime.now().minusDays(7);
        LocalDateTime dataFinal = LocalDateTime.now();

        when(repositorioTransferencia.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal))
                .thenReturn(transferenciasMock);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTransferenciaPorOperadorEPorData(
                pageable, null, dataInicial, dataFinal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciasMock, response.getBody());
        verify(repositorioTransferencia, times(1)).obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComTodosParametrosNulos_RetornaInternalServerError() {
        Pageable pageable = mock(Pageable.class);

        when(repositorioTransferencia.obterTodasTransferencias(pageable)).thenThrow(RuntimeException.class);

        ResponseEntity<Page<TransferenciaDTO>> response = servicoTransferencia.obterTransferenciaPorOperadorEPorData(
                pageable, null, null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(repositorioTransferencia, times(1)).obterTodasTransferencias(pageable);
    }

    @Test
    void obterTransferenciaPorId_ComIdExistente_RetornaTransferenciaDTO() {
        int id = 1;
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaDTO transferenciaDTOMock = mock(TransferenciaDTO.class);

        when(repositorioTransferencia.obterTransferenciaPorId(id)).thenReturn(Optional.of(transferenciaMock));
        when(mapperTransferencia.mapModelToDto(transferenciaMock)).thenReturn(transferenciaDTOMock);

        ResponseEntity<TransferenciaDTO> response = servicoTransferencia.obterTransferenciaPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciaDTOMock, response.getBody());
        verify(repositorioTransferencia, times(1)).obterTransferenciaPorId(id);
        verify(mapperTransferencia, times(1)).mapModelToDto(transferenciaMock);
    }

    @Test
    void obterTransferenciaPorId_ComExcecao_RetornaInternalServerError() {
        int id = 1;

        when(repositorioTransferencia.obterTransferenciaPorId(id)).thenThrow(RuntimeException.class);

        ResponseEntity<TransferenciaDTO> response = servicoTransferencia.obterTransferenciaPorId(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(repositorioTransferencia, times(1)).obterTransferenciaPorId(id);
    }

    @Test
    void realizarTransferencia_ComTransferenciaDTOValidaENumerosDeConta_RetornaTransferenciaDTO() {

        TransferenciaDTO transferenciaDTOMock = mock(TransferenciaDTO.class);
        Conta contaDestinatarioMock = mock(Conta.class);
        Conta contaOrigemMock = mock(Conta.class);
        Transferencia transferenciaMock = mock(Transferencia.class);
        TransferenciaDTO transferenciaDTO1Mock = mock(TransferenciaDTO.class);

        when(repositorioConta.obterContaPorId(anyInt())).thenReturn(Optional.of(contaDestinatarioMock), Optional.of(contaOrigemMock));
        when(mapperTransferencia.mapTransferencia(eq(transferenciaDTOMock), eq(contaDestinatarioMock), eq(contaOrigemMock))).thenReturn(transferenciaMock);
        when(mapperTransferencia.mapModelToDto(transferenciaMock)).thenReturn(transferenciaDTO1Mock);

        ResponseEntity<TransferenciaDTO> response = servicoTransferencia.realizarTransferencia(
                transferenciaDTOMock, 123, 456);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferenciaDTO1Mock, response.getBody());
        verify(repositorioConta, times(2)).obterContaPorId(anyInt());
        verify(mapperTransferencia, times(1)).mapTransferencia(eq(transferenciaDTOMock), eq(contaDestinatarioMock), eq(contaOrigemMock));
        verify(repositorioTransferencia, times(1)).salvar(transferenciaMock);
        verify(mapperTransferencia, times(1)).mapModelToDto(transferenciaMock);
    }
}
