package br.com.banco.domain.ports.repositories;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.domain.models.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface IContaRepositoryPort {

    Page<ContaDTO> obterTodasContas(final Pageable pageable);

    Optional<Conta> obterContaPorId(final int id);

    Optional<Conta> obterContaPorNomeResponsavel(final String nomeResponsavel);

    void salvar(final Conta conta);

    void deletar(final int id);

}