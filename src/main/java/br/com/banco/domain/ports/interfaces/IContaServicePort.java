package br.com.banco.domain.ports.interfaces;

import br.com.banco.application.dtos.ContaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface IContaServicePort {

    /**
     * Obtém todas as contas.
     * @param pageable faz a paginacao dos dados
     * @return Uma lista contendo todas as contas existentes.
     */
    ResponseEntity<Page<ContaDTO>> obterTodasContas(final Pageable pageable);

    /**
     * Obtém uma conta por ID.
     *
     * @param id O ID da conta a ser obtida.
     * @return A conta correspondente ao ID informado, ou {@code null} se não encontrada.
     * @throws IllegalArgumentException se o ID fornecido for nulo.
     */
    ResponseEntity<ContaDTO> obterContaPorId(final int id);

    /**
     * Obtém uma conta pelo nome do responsável.
     *
     * @param nomeResponsavel O nome do responsável da conta.
     * @return A conta correspondente ao nome do responsável, ou {@code null} se não encontrada.
     * @throws IllegalArgumentException se o nome do responsável fornecido for nulo ou vazio.
     */
    ResponseEntity<ContaDTO> obterContaPorNomeResponsavel(final String nomeResponsavel);

    /**
     * Salva uma conta no banco de dados.
     *
     * @param contaDTO O objeto ContaDTO contendo as informações da conta a ser salva.
     */
    ResponseEntity<Void> salvarConta(final ContaDTO contaDTO);

    /**
     * Deleta uma conta do banco de dados pelo ID.
     *
     * @param id O ID da conta a ser deletada.
     * @return
     */
    ResponseEntity<Void> deletarConta(final int id);
}