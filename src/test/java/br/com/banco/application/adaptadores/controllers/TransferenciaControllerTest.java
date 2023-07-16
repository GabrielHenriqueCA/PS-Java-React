package br.com.banco.application.adaptadores.controllers;

import br.com.banco.application.adptadores.controllers.TransferenciaController;
import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.ports.interfaces.ITransferenciaServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransferenciaControllerTest {
    private TransferenciaController transferenciaController;

    @Mock
    private ITransferenciaServicePort transferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferenciaController = new TransferenciaController(transferenciaService);
    }

    @Test
    void obterTodasTransferencias_ComPageableValido_RetornaPageDeTransferenciaDTO() {
        Sort sort = Sort.by("id").ascending();
        PageRequest pageable = PageRequest.of(0, 10, sort);
        List<TransferenciaDTO> listaDeTransferenciasDTO = new ArrayList<>();
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        listaDeTransferenciasDTO.add(transferenciaDTO);
        Page<TransferenciaDTO> paginaDeTransferenciasDTO = new PageImpl<>(listaDeTransferenciasDTO, pageable, 1);

        when(transferenciaService.obterTodasTransferencias(pageable)).thenReturn(ResponseEntity.ok(paginaDeTransferenciasDTO));


        ResponseEntity<Page<TransferenciaDTO>> resposta = transferenciaController.obterTodasTransferencias(pageable);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(paginaDeTransferenciasDTO, resposta.getBody());
        verify(transferenciaService, times(1)).obterTodasTransferencias(pageable);
    }

    @Test
    void obterTransferenciaPorOperadorEPorData_ComParametrosValidos_RetornaPageDeTransferenciaDTO() {
        Sort sort = Sort.by("id").ascending();
        PageRequest pageable = PageRequest.of(0, 10, sort);
        String nomeOperador = "Operador1";
        LocalDateTime dataInicial = LocalDateTime.now().minusDays(7);
        LocalDateTime dataFinal = LocalDateTime.now();
        List<TransferenciaDTO> listaDeTransferenciasDTO = new ArrayList<>();
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        listaDeTransferenciasDTO.add(transferenciaDTO);
        Page<TransferenciaDTO> paginaDeTransferenciasDTO = new PageImpl<>(listaDeTransferenciasDTO, pageable, 1);

        when(transferenciaService.obterTransferenciaPorOperadorEPorData(pageable, nomeOperador, dataInicial, dataFinal))
                .thenReturn(ResponseEntity.ok(paginaDeTransferenciasDTO));

        ResponseEntity<Page<TransferenciaDTO>> resposta = transferenciaController.obterTransferenciaPorOperadorEPorData(
                pageable, nomeOperador, dataInicial, dataFinal);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(paginaDeTransferenciasDTO, resposta.getBody());
        verify(transferenciaService, times(1))
                .obterTransferenciaPorOperadorEPorData(pageable, nomeOperador, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciaPorId_ComIdValido_RetornaTransferenciaDTO() {
        // Arrange
        int id = 1;
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        // Configurar os atributos do transferenciaDTO

        when(transferenciaService.obterTransferenciaPorId(id)).thenReturn(ResponseEntity.ok(transferenciaDTO));

        // Act
        ResponseEntity<TransferenciaDTO> resposta = transferenciaController.obterTransferenciaPorId(id);

        // Assert
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(transferenciaDTO, resposta.getBody());
        verify(transferenciaService, times(1)).obterTransferenciaPorId(id);
    }

    @Test
    void realizarTransferencia_ComTransferenciaDTOValido_RetornaTransferenciaDTO() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();

        int numeroContaDestino = 123;
        int numeroContaOrigem = 456;

        when(transferenciaService.realizarTransferencia(transferenciaDTO, numeroContaDestino, numeroContaOrigem))
                .thenReturn(ResponseEntity.ok(transferenciaDTO));

        ResponseEntity<TransferenciaDTO> resposta = transferenciaController.realizarTransferencia(
                transferenciaDTO, numeroContaDestino, numeroContaOrigem);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(transferenciaDTO, resposta.getBody());
        verify(transferenciaService, times(1))
                .realizarTransferencia(transferenciaDTO, numeroContaDestino, numeroContaOrigem);
    }

    @Test
    void realizarDeposito_ComTransferenciaDTOValido_RetornaTransferenciaDTO() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        int numeroContaDestino = 123;

        when(transferenciaService.realizarDeposito(transferenciaDTO, numeroContaDestino))
                .thenReturn(ResponseEntity.ok(transferenciaDTO));

        ResponseEntity<TransferenciaDTO> resposta = transferenciaController.realizarDeposito(
                transferenciaDTO, numeroContaDestino);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(transferenciaDTO, resposta.getBody());
        verify(transferenciaService, times(1)).realizarDeposito(transferenciaDTO, numeroContaDestino);
    }

    @Test
    void realizarSaque_ComTransferenciaDTOValido_RetornaTransferenciaDTO() {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        int numeroContaDestino = 123;

        when(transferenciaService.realizarSaque(transferenciaDTO, numeroContaDestino))
                .thenReturn(ResponseEntity.ok(transferenciaDTO));

        ResponseEntity<TransferenciaDTO> resposta = transferenciaController.realizarSaque(
                transferenciaDTO, numeroContaDestino);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(transferenciaDTO, resposta.getBody());
        verify(transferenciaService, times(1)).realizarSaque(transferenciaDTO, numeroContaDestino);
    }

    @Test
    void obterTransferenciasPorPeriodo_ComParametrosValidos_RetornaPageDeTransferenciaDTO() {
        Sort sort = Sort.by("id").ascending();
        PageRequest pageable = PageRequest.of(0, 10, sort);
        LocalDateTime dataInicial = LocalDateTime.now().minusDays(7);

        LocalDateTime dataFinal = LocalDateTime.now();
        List<TransferenciaDTO> listaDeTransferenciasDTO = new ArrayList<>();
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        listaDeTransferenciasDTO.add(transferenciaDTO);
        Page<TransferenciaDTO> paginaDeTransferenciasDTO = new PageImpl<>(listaDeTransferenciasDTO, pageable, 1);

        when(transferenciaService.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal))
                .thenReturn(ResponseEntity.ok(paginaDeTransferenciasDTO));


        ResponseEntity<Page<TransferenciaDTO>> resposta = transferenciaController.obterTransferenciasPorPeriodo(
                pageable, dataInicial, dataFinal);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(paginaDeTransferenciasDTO, resposta.getBody());
        verify(transferenciaService, times(1)).obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);
    }

    @Test
    void obterTransferenciasPorOperador_ComNomeOperadorValidoERequestParamValido_RetornaPageDeTransferenciaDTO() {
        String nomeOperador = "Operador1";
        Sort sort = Sort.by("id").ascending();
        PageRequest pageable = PageRequest.of(0, 10, sort);
        List<TransferenciaDTO> listaDeTransferenciasDTO = new ArrayList<>();

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        listaDeTransferenciasDTO.add(transferenciaDTO);
        Page<TransferenciaDTO> paginaDeTransferenciasDTO = new PageImpl<>(listaDeTransferenciasDTO, pageable, 1);

        when(transferenciaService.obterTransferenciasPorOperador(pageable, nomeOperador))
                .thenReturn(ResponseEntity.ok(paginaDeTransferenciasDTO));

        ResponseEntity<Page<TransferenciaDTO>> resposta = transferenciaController.obterTransferenciasPorOperador(
                nomeOperador, pageable);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(paginaDeTransferenciasDTO, resposta.getBody());
        verify(transferenciaService, times(1)).obterTransferenciasPorOperador(pageable, nomeOperador);
    }

    @Test
    void obterTransferenciasPorConta_ComIdContaValidoERequestParamValido_RetornaPageDeTransferenciaDTO() {
        int idConta = 123;

        Sort sort = Sort.by("id").ascending();
        PageRequest pageable = PageRequest.of(0, 10, sort);
        List<TransferenciaDTO> listaDeTransferenciasDTO = new ArrayList<>();

        TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
        listaDeTransferenciasDTO.add(transferenciaDTO);
        Page<TransferenciaDTO> paginaDeTransferenciasDTO = new PageImpl<>(listaDeTransferenciasDTO, pageable, 1);

        when(transferenciaService.obterTransferenciasPorConta(pageable, idConta))
                .thenReturn(ResponseEntity.ok(paginaDeTransferenciasDTO));

        ResponseEntity<Page<TransferenciaDTO>> resposta = transferenciaController.obterTransferenciasPorConta(
                idConta, pageable);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(paginaDeTransferenciasDTO, resposta.getBody());
        verify(transferenciaService, times(1)).obterTransferenciasPorConta(pageable, idConta);
    }

    @Test
    void obterSaldoTotalPorPeriodo_ComParametrosValidos_RetornaBigDecimal() {
        LocalDateTime dataInicial = LocalDateTime.now().minusDays(7);
        LocalDateTime dataFinal = LocalDateTime.now();
        BigDecimal saldoTotal = BigDecimal.valueOf(1000.00);

        when(transferenciaService.obterSaldoTotalPorPeriodo(dataInicial, dataFinal))
                .thenReturn(ResponseEntity.ok(saldoTotal));

        ResponseEntity<BigDecimal> resposta = transferenciaController.obterSaldoTotalPorPeriodo(dataInicial, dataFinal);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(saldoTotal, resposta.getBody());
        verify(transferenciaService, times(1)).obterSaldoTotalPorPeriodo(dataInicial, dataFinal);
    }

    @Test
    void obterSaldoTotalPorNumero_ComNumeroContaValidoo_RetornaBigDecimal() {
        int numeroConta = 1;
        BigDecimal saldoTotal = BigDecimal.valueOf(1000.00);

        when(transferenciaService.obterSaldoTotalPorNumeroConta(numeroConta))
                .thenReturn(ResponseEntity.ok(saldoTotal));

        ResponseEntity<BigDecimal> resposta = transferenciaController.obterSaldoTotalPorOperador(numeroConta);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(saldoTotal, resposta.getBody());
        verify(transferenciaService, times(1)).obterSaldoTotalPorNumeroConta(numeroConta);
    }
}
