package br.com.banco.domain.adaptadores.services;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.application.mappers.ContaMapper;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.DeletarContaException;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceImplTest {
    private ContaServiceImpl contaService;

    @Mock
    private IContaRepositoryPort contaRepository;

    @Mock
    private ContaMapper contaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contaService = new ContaServiceImpl(contaRepository, contaMapper);
    }

    @Test
    void obterTodasContas_DeveRetornarPageDeContaDTO() {
        Pageable pageable = Pageable.ofSize(10);
        Page<ContaDTO> contaDTOPage = mock(Page.class);

        when(contaRepository.obterTodasContas(pageable)).thenReturn(contaDTOPage);


        ResponseEntity<Page<ContaDTO>> response = contaService.obterTodasContas(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTOPage, response.getBody());
        verify(contaRepository, times(1)).obterTodasContas(pageable);
    }

    @Test
    void obterContaPorId_ComIdExistente_DeveRetornarContaDTO() {
        int id = 1;
        Conta conta = new Conta();
        ContaDTO contaDTO = new ContaDTO();

        when(contaRepository.obterContaPorId(id)).thenReturn(Optional.of(conta));
        when(contaMapper.mapModelToDto(conta)).thenReturn(contaDTO);

        ResponseEntity<ContaDTO> response = contaService.obterContaPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTO, response.getBody());
        verify(contaRepository, times(1)).obterContaPorId(id);
    }

    @Test
    void obterContaPorId_ComIdInexistente_DeveLancarExcecao() {
        int id = 1;

        when(contaRepository.obterContaPorId(id)).thenReturn(Optional.empty());

        assertThrows(ContaNaoEncontradaException.class, () -> contaService.obterContaPorId(id));
        verify(contaRepository, times(1)).obterContaPorId(id);
    }

    @Test
    void obterContaPorNomeResponsavel_ComNomeExistente_DeveRetornarContaDTO() {
        String nomeResponsavel = "Responsável";
        Conta conta = new Conta();
        ContaDTO contaDTO = new ContaDTO();

        when(contaRepository.obterContaPorNomeResponsavel(nomeResponsavel)).thenReturn(Optional.of(conta));
        when(contaMapper.mapModelToDto(conta)).thenReturn(contaDTO);

        ResponseEntity<ContaDTO> response = contaService.obterContaPorNomeResponsavel(nomeResponsavel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTO, response.getBody());
        verify(contaRepository, times(1)).obterContaPorNomeResponsavel(nomeResponsavel);
    }

    @Test
    void obterContaPorNomeResponsavel_ComNomeInexistente_DeveLancarExcecao() {
        String nomeResponsavel = "Responsável";

        when(contaRepository.obterContaPorNomeResponsavel(nomeResponsavel)).thenReturn(Optional.empty());

        assertThrows(ContaNaoEncontradaException.class, () -> contaService.obterContaPorNomeResponsavel(nomeResponsavel));
        verify(contaRepository, times(1)).obterContaPorNomeResponsavel(nomeResponsavel);
    }

    @Test
    void salvarConta_ComContaDTOValida_DeveRetornarVoid() {
        ContaDTO contaDTO = new ContaDTO();
        Conta conta = new Conta();

        when(contaMapper.mapDtoToModel(contaDTO)).thenReturn(conta);

        ResponseEntity<Void> response = contaService.salvarConta(contaDTO);

        assertNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(contaRepository, times(1)).salvar(conta);
    }

    @Test
    void deletarConta_ComIdExistente_DeveRetornarVoid() {
        int id = 1;

        ResponseEntity<Void> response = contaService.deletarConta(id);

        assertNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(contaRepository, times(1)).deletar(id);
    }

    @Test
    void deletarConta_ComIdInexistente_DeveLancarExcecao() {
        int id = 1;

        doThrow(DeletarContaException.class).when(contaRepository).deletar(id);

        assertThrows(DeletarContaException.class, () -> contaService.deletarConta(id));
        verify(contaRepository, times(1)).deletar(id);
    }

}

