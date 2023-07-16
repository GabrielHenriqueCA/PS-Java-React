package br.com.banco.domain.ports.interfaces;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.exception.ContaNaoEncontradaException;
import br.com.banco.domain.exception.SaldoInsuficienteException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public interface ITransferenciaServicePort {
    /**
     * Obtém todas as transferencias
     * @param pageable informações de paginação.
     * @return Uma lista de todas as transferencias
     */
    ResponseEntity<Page<TransferenciaDTO>> obterTodasTransferencias(final Pageable pageable);


    /**
     * Obtém uma transferência por ID.
     *
     * @param nomeOperador nome de quem fez a transferencia
     * @param dataInicial  A data inicial do período.
     * @param dataFinal    A data final do período.
     * @param pageable informações de paginação.
     * @return Uma lista de transferências realizadas dentro do período especificado.
     * @throws IllegalArgumentException se a data inicial for posterior à data final.
     */
    ResponseEntity<Page<TransferenciaDTO>> obterTransferenciaPorOperadorEPorData(final Pageable pageable, final String nomeOperador, final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    /**
     * Obtém uma transferência por ID.
     *
     * @param id O ID da transferência a ser obtida.
     * @return A transferência correspondente ao ID informado, ou {@code null} se não encontrada.
     */
    ResponseEntity<TransferenciaDTO> obterTransferenciaPorId(final int id);

    /**
     * Realiza uma transferência de fundos entre contas.
     *
     * @param transferenciaDTO possui as informacoes necessarias para realizar a transferencias
     * @param numeroConta  numero da conta que vai receber a transferencia
     * @return A transferência realizada.
     * @throws IllegalArgumentException    se algum dos IDs fornecidos for nulo.
     * @throws ContaNaoEncontradaException se a conta de origem ou a conta de destino não for encontrada com os IDs fornecidos.
     * @throws SaldoInsuficienteException  se o saldo da conta de origem não for suficiente para a transferência.
     */
    ResponseEntity<TransferenciaDTO> realizarTransferencia(final TransferenciaDTO transferenciaDTO, final int numeroConta, final int numeroContaOrigem );


    /**
     * Realiza um depósito em uma conta.
     *
     * @param transferenciaDTO O objeto TransferenciaDTO contendo as informações da transferência, como a conta de destino.
     * @param numeroConta  numero da conta que vai receber o deposito
     * @return O objeto TransferenciaDTO atualizado com os dados da transferência realizada.
     * @throws IllegalArgumentException se o TransferenciaDTO for nulo ou se o valor for negativo.
     */
    ResponseEntity<TransferenciaDTO> realizarDeposito(final TransferenciaDTO transferenciaDTO, final int numeroConta);

    /**
     * Realiza um saque em uma conta.
     *
     * @param transferenciaDTO O objeto TransferenciaDTO contendo as informações da transferência, como a conta de origem
     * @param numeroConta  numero da conta que vai realizar o saque
     * @return O objeto TransferenciaDTO atualizado com os dados da transferência realizada.
     * @throws IllegalArgumentException se o TransferenciaDTO for nulo, se o valor for negativo ou se a conta não possuir saldo suficiente.
     */
    ResponseEntity<TransferenciaDTO> realizarSaque(final TransferenciaDTO transferenciaDTO, final int numeroConta);

    /**
     * Obtém todas as transferências relacionadas a um determinado período de tempo.
     *
     * @param pageable informações de paginação.
     * @param dataInicial A data inicial do período.
     * @param dataFinal   A data final do período.
     * @return Uma lista de transferências realizadas dentro do período especificado.
     * @throws IllegalArgumentException se a data inicial for posterior à data final.
     */
    ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorPeriodo(final Pageable pageable, final LocalDateTime dataInicial, final LocalDateTime dataFinal);

    /**
     * Obtém todas as transferências relacionadas a um determinado operador.
     *
     * @param pageable informações de paginação.
     * @param nomeOperador O nome do operador.
     * @return Uma lista de transferências realizadas pelo operador especificado.
     */
    ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorOperador(final Pageable pageable, final String nomeOperador);

    /**
     * Obtém todas as transferências realizadas em uma conta específica.
     *
     * @param pageable informações de paginação.
     * @param idConta O ID da conta.
     * @return Uma lista de transferências realizadas na conta especificada.
     * @throws IllegalArgumentException    se o ID da conta fornecido for nulo.
     * @throws ContaNaoEncontradaException se a conta não for encontrada com o ID fornecido.
     */
    ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorConta(final Pageable pageable, final int idConta);

    /**
     * Obtém o saldo total das transferências realizadas dentro de um determinado período.
     *
     * @param dataInicial A data inicial do período.
     * @param dataFinal   A data final do período.
     * @return O saldo total das transferências realizadas dentro do período especificado.
     * @throws IllegalArgumentException se a data inicial for posterior à data final.
     */
    ResponseEntity<BigDecimal> obterSaldoTotalPorPeriodo(final LocalDateTime dataInicial, final LocalDateTime dataFinal);


    /**
     * Obtém o saldo total das transferências relacionadas a um determinado operador.
     *
     * @param numeroCOnta numero da conta
     * @return O saldo total das transferências relacionadas ao operador especificado.
     */
    ResponseEntity<BigDecimal> obterSaldoTotalPorNumeroConta(final int numeroCOnta);
}