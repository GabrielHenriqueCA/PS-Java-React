package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.application.mappers.ContaMapper;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.SalvarContaException;
import br.com.banco.domain.models.Conta;
import br.com.banco.infrastructure.adaptadores.persistence.entities.ContaEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaRepositoryImplTest {

    private ContaRepositoryImpl repositorioConta;

    @Mock
    private SpringContaRepository contaRepository;

    @Mock
    private ContaMapper contaMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        repositorioConta = new ContaRepositoryImpl(contaRepository, contaMapper);
    }

    @Test
    void obterTodasContas_ComPageableValido_RetornaContas() {
        Page<ContaEntity> contaEntityMock = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        Page<ContaDTO> contasMock = mock(Page.class);

        when(contaRepository.findAll(pageable)).thenReturn(contaEntityMock);
        when(contaMapper.mapToDtoPage(contaEntityMock)).thenReturn(contasMock);

        Page<ContaDTO> response = repositorioConta.obterTodasContas(pageable);

        assertEquals(contasMock, response);
        verify(contaRepository, times(1)).findAll(pageable);
        verify(contaMapper, times(1)).mapToDtoPage(contaEntityMock);
    }

    @Test
    void obterContaPorId_ComIdExistente_RetornaConta() {
        int id = 1;
        ContaEntity contaEntityMock = mock(ContaEntity.class);
        Conta contaMock = mock(Conta.class);

        when(contaRepository.findById(id)).thenReturn(Optional.of(contaEntityMock));
        when(contaMapper.mapEntityToModel(contaEntityMock)).thenReturn(Optional.of(contaMock));

        Optional<Conta> result = repositorioConta.obterContaPorId(id);

        assertTrue(result.isPresent());
        assertEquals(contaMock, result.get());
        verify(contaRepository, times(1)).findById(id);
        verify(contaMapper, times(1)).mapEntityToModel(contaEntityMock);
    }

    @Test
    void obterContaPorId_ComIdInexistente_LancaContaNaoEncontradaException() {
        int id = 1;
        Mockito.when(contaRepository.findById(id)).thenReturn(Optional.empty());


        Assertions.assertThrows(ContaNaoEncontradaException.class, () -> {
            repositorioConta.obterContaPorId(id);
        });
    }

    @Test
    void obterContaPorNomeResponsavel_ComNomeResponsavelExistente_RetornaConta() {
        String nomeResponsavel = "Responsável";
        ContaEntity contaEntityMock = mock(ContaEntity.class);
        Conta contaMock = mock(Conta.class);

        when(contaRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel)).thenReturn(contaEntityMock);
        when(contaMapper.mapEntityToModel(contaEntityMock)).thenReturn(Optional.of(contaMock));

        Optional<Conta> result = repositorioConta.obterContaPorNomeResponsavel(nomeResponsavel);

        assertTrue(result.isPresent());
        assertEquals(contaMock, result.get());
        verify(contaRepository, times(1)).findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);
        verify(contaMapper, times(1)).mapEntityToModel(contaEntityMock);
    }

    @Test
    void obterContaPorNomeResponsavel_ComNomeResponsavelInexistente_RetornaContaVazia() {
        String nomeResponsavel = "Responsável";
        Mockito.when(contaRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel)).thenReturn(null);

        Optional<Conta> result = repositorioConta.obterContaPorNomeResponsavel(nomeResponsavel);

        assertFalse(result.isPresent());
        verify(contaRepository, times(1)).findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);
        verify(contaMapper, never()).mapEntityToModel(any());
    }

    @Test
    void obterContaPorNomeResponsavel_ComExcecao_RetornaInternalServerError() {
        String nomeResponsavel = "Responsável";

        when(contaRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel)).thenThrow(RuntimeException.class);

        assertThrows(ContaNaoEncontradaException.class, () -> {
            repositorioConta.obterContaPorNomeResponsavel(nomeResponsavel);
        });

        verify(contaRepository, times(1)).findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);
    }

    @Test
    void salvar_ComContaValida_SalvaConta() {
        Conta contaMock = mock(Conta.class);
        ContaEntity contaEntityMock = mock(ContaEntity.class);

        when(contaMapper.mapModelToEntity(contaMock)).thenReturn(contaEntityMock);

        repositorioConta.salvar(contaMock);

        verify(contaRepository, times(1)).save(contaEntityMock);
    }

    @Test
    void salvar_ComExcecao_LancaSalvarContaException() {
        Conta contaMock = mock(Conta.class);

        when(contaMapper.mapModelToEntity(contaMock)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(SalvarContaException.class, () -> {
            repositorioConta.salvar(contaMock);
        });

        verify(contaRepository, never()).save(any());
    }

    @Test
    void deletar_ComIdExistente_DeletaConta() {
        int id = 1;
        ContaEntity contaEntityMock = mock(ContaEntity.class);

        when(contaRepository.findById(id)).thenReturn(Optional.of(contaEntityMock));

        repositorioConta.deletar(id);

        verify(contaRepository, times(1)).delete(contaEntityMock);
    }

    @Test
    void deletar_ComIdInexistente_LancaContaNaoEncontradaException() {
        int id = 1;
        Mockito.when(contaRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ContaNaoEncontradaException.class, () -> {
            repositorioConta.deletar(id);
        });
    }

    @Test
    void deletar_ComExcecao_RetornaInternalServerError() {
        int id = 1;

        when(contaRepository.findById(id)).thenThrow(RuntimeException.class);

        assertThrows(ContaNaoEncontradaException.class, () -> {
            repositorioConta.deletar(id);
        });

        verify(contaRepository, times(1)).findById(id);
    }
}