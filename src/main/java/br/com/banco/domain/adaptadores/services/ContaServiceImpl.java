package br.com.banco.domain.adaptadores.services;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.application.mappers.ContaMapper;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.DeletarContaException;
import br.com.banco.domain.exception.SalvarContaException;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.ports.interfaces.IContaServicePort;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Optional;


public class ContaServiceImpl implements IContaServicePort {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContaServiceImpl.class);

    private final IContaRepositoryPort contaRepository;
    private final ContaMapper contaMapper;

    public ContaServiceImpl(IContaRepositoryPort contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    @Override
    public ResponseEntity<Page<ContaDTO>> obterTodasContas(final Pageable pageable) {
        try {
            LOGGER.info("Obtendo todas as contas");
            Page<ContaDTO> contaDTOPage = contaRepository.obterTodasContas(pageable);

            return ResponseEntity.ok(contaDTOPage);
        } catch (ContaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter todas as contas", e);
            throw new ContaNaoEncontradaException("Erro ao obter todas as contas");
        }
    }

    @Override
    public ResponseEntity<ContaDTO> obterContaPorId(final int id) {
        validarId(id);
        try {
            LOGGER.info("Obtendo conta por ID: {}", id);
            Optional<Conta> conta = contaRepository.obterContaPorId(id);

            if (conta.isEmpty()) {
                throw new ContaNaoEncontradaException("Conta nao encontrada para o ID informado.");
            }

            ContaDTO contaDTO = contaMapper.mapModelToDto(conta.get());

            return ResponseEntity.ok(contaDTO);
        } catch (ContaNaoEncontradaException e) {
            LOGGER.error("Conta nao encontrada para o ID: {}", id, e);
            throw new ContaNaoEncontradaException("Conta nao encontrada com id: " + id);
        }
    }

    @Override
    public ResponseEntity<ContaDTO> obterContaPorNomeResponsavel(final String nomeResponsavel) {
        validarNomeResponsavel(nomeResponsavel);
        try {
            LOGGER.info("Obtendo conta por nome do responsável: {}", nomeResponsavel);
            Optional<Conta> conta = contaRepository.obterContaPorNomeResponsavel(nomeResponsavel);

            if (conta.isEmpty()) {
                throw new ContaNaoEncontradaException("Conta nao encontrada para o nome do responsavel informado.");
            }

            ContaDTO contaDTO = contaMapper.mapModelToDto(conta.get());

            return ResponseEntity.ok(contaDTO);
        } catch (ContaNaoEncontradaException e) {
            LOGGER.error("Conta nao encontrada para o nome do responsavel: {}", nomeResponsavel, e);
            throw new ContaNaoEncontradaException("Conta nao encontrada para o nome do responsavel: " + nomeResponsavel);
        }
    }

    @Override
    public ResponseEntity<Void> salvarConta(@Valid final ContaDTO contaDTO) {
        try {
            LOGGER.info("Salvando conta");
            contaRepository.salvar(contaMapper.mapDtoToModel(contaDTO));
            return ResponseEntity.ok().build();
        } catch (SalvarContaException e) {
            LOGGER.error("Erro ao salvar conta", e);
            throw new SalvarContaException("Erro ao salvar conta");
        }
    }

    @Override
    public ResponseEntity<Void> deletarConta(final int id) {
        validarId(id);
        try {
            LOGGER.info("Deletando conta com ID: {}", id);
            contaRepository.deletar(id);
        } catch (DeletarContaException e) {
            LOGGER.error("Erro ao deletar conta com ID: {}", id, e);
            throw new DeletarContaException("Erro ao deletar conta com ID: " + id);
        }
        return ResponseEntity.ok().build();
    }

    private void validarId(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID da conta nao pode ser nulo.");
        }
    }

    private void validarNomeResponsavel(final String nomeResponsavel) {
        if (nomeResponsavel == null || nomeResponsavel.isEmpty()) {
            throw new IllegalArgumentException("O nome do responsavel da conta nao pode ser nulo ou vazio.");
        }
    }
}