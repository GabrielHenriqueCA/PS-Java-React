package br.com.banco.application.adaptadores.controllers;

import br.com.banco.application.adptadores.controllers.ContaController;
import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.domain.ports.interfaces.IContaServicePort;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContaControllerTest {
    private ContaController contaController;

    @Mock
    private IContaServicePort contaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contaController = new ContaController(contaService);
    }

    @Test
    void obterTodasAsContas_ComPageableValido_RetornaPageDeContaDTO() {
        Sort sort = Sort.by("id").ascending();
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        List<ContaDTO> contaDTOList = new ArrayList<>();
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1);
        contaDTO.setNomeResponsavel("Nome1");
        contaDTOList.add(contaDTO);
        Page<ContaDTO> contaDTOPage = new PageImpl<>(contaDTOList, pageRequest, 1);

        when(contaService.obterTodasContas(pageRequest)).thenReturn(ResponseEntity.ok(contaDTOPage));


        ResponseEntity<Page<ContaDTO>> response = contaController.obterTodasAsContas(pageRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTOPage, response.getBody());
        verify(contaService, times(1)).obterTodasContas(pageRequest);
    }

    @Test
    void obterContaPorId_ComIdValido_RetornaContaDTO() {
        int id = 1;
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(id);
        contaDTO.setNomeResponsavel("Nome1");

        when(contaService.obterContaPorId(id)).thenReturn(ResponseEntity.ok(contaDTO));

        ResponseEntity<ContaDTO> response = contaController.obterContaPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTO, response.getBody());
        verify(contaService, times(1)).obterContaPorId(id);
    }

    @Test
    void obterContaPorNomeResponsavel_ComNomeValido_RetornaContaDTO() {
        String nomeResponsavel = "Nome1";
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1);
        contaDTO.setNomeResponsavel(nomeResponsavel);

        when(contaService.obterContaPorNomeResponsavel(nomeResponsavel)).thenReturn(ResponseEntity.ok(contaDTO));

        ResponseEntity<ContaDTO> response = contaController.obterContaPorNomeResponsavel(nomeResponsavel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contaDTO, response.getBody());
        verify(contaService, times(1)).obterContaPorNomeResponsavel(nomeResponsavel);
    }

    @Test
    void salvarConta_ComContaDTOValido_RetornaStatusCriado() {
        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setId(1);
        contaDTO.setNomeResponsavel("Gabriel");

        when(contaService.salvarConta(contaDTO)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        ResponseEntity<Void> response = contaController.salvarConta(contaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(contaService, times(1)).salvarConta(contaDTO);
    }

    @Test
    void deletarConta_ComIdValido_RetornaStatusSemConteudo() {
        int id = 1;

        when(contaService.deletarConta(id)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = contaController.deletarConta(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contaService, times(1)).deletarConta(id);
    }
}