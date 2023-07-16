package br.com.banco.infrastructure.adaptadores.repositories;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.application.mappers.ContaMapper;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.SalvarContaException;
import br.com.banco.domain.models.Conta;
import br.com.banco.domain.ports.repositories.IContaRepositoryPort;
import br.com.banco.infrastructure.adaptadores.persistence.entities.ContaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContaRepositoryImpl implements IContaRepositoryPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContaRepositoryImpl.class);

    private final SpringContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public ContaRepositoryImpl(SpringContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    @Override
    public Page<ContaDTO> obterTodasContas(final Pageable pageable) {
        try {
            LOGGER.info("Obtendo todas as contas");
            Page<ContaEntity> contaPages = contaRepository.findAll(pageable);

            if (contaPages.isEmpty()) {
                throw new ContaNaoEncontradaException("Contas não encontradas");
            }

            return contaMapper.mapToDtoPage(contaPages);
        } catch (ContaNaoEncontradaException e) {
            LOGGER.error("Erro ao obter todas as contas", e);
            throw new ContaNaoEncontradaException("Erro ao obter todas as contas");
        }
    }

    @Override
    public Optional<Conta> obterContaPorId(final int id) {
        try {
            LOGGER.info("Obtendo conta por ID: {}", id);
            Optional<ContaEntity> contaEntity = contaRepository.findById(id);

            if (contaEntity.isEmpty()) {
                throw new ContaNaoEncontradaException("Conta não encontrada");
            }

            return contaMapper.mapEntityToModel(contaEntity.get());
        } catch (Exception e) {
            LOGGER.error("Erro ao obter conta por ID: {}", id, e);
            throw new ContaNaoEncontradaException("Erro ao obter conta por ID: " + id);
        }
    }

    @Override
    public Optional<Conta> obterContaPorNomeResponsavel(final String nomeResponsavel) {
        try {
            LOGGER.info("Obtendo conta por nome do responsável: {}", nomeResponsavel);
            ContaEntity contaEntity = contaRepository.findByNomeResponsavelContainingIgnoreCase(nomeResponsavel);

            if (contaEntity == null) {
                return Optional.empty();
            }

            return contaMapper.mapEntityToModel(contaEntity);
        } catch (Exception e) {
            LOGGER.error("Erro ao obter conta por nome do responsável: {}", nomeResponsavel, e);
            throw new ContaNaoEncontradaException("Erro ao obter conta por nome do responsável: " + nomeResponsavel);
        }
    }

    @Override
    public void salvar(final Conta conta) {
        try {
            LOGGER.info("Salvando conta");
            ContaEntity contaEntity = contaMapper.mapModelToEntity(conta);
            contaRepository.save(contaEntity);
        } catch (Exception e) {
            LOGGER.error("Erro ao salvar conta", e);
            throw new SalvarContaException("Erro ao salvar conta");
        }
    }

    @Override
    public void deletar(final int id) {
        try {
            LOGGER.info("Deletando conta com ID: {}", id);
            Optional<ContaEntity> contaEntity = contaRepository.findById(id);

            if (contaEntity.isEmpty()) {
                throw new ContaNaoEncontradaException("Conta não encontrada");
            }

            contaRepository.delete(contaEntity.get());
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar conta com ID: {}", id, e);
            throw new ContaNaoEncontradaException("Erro ao deletar conta com ID: " + id);
        }
    }
}