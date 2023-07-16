package br.com.banco.application.adptadores.controllers;

import br.com.banco.application.dtos.TransferenciaDTO;
import br.com.banco.domain.ports.interfaces.ITransferenciaServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/transferencias")
@Validated
public class TransferenciaController {
    private final ITransferenciaServicePort transferenciaService;

    public TransferenciaController(ITransferenciaServicePort transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<TransferenciaDTO>> obterTodasTransferencias(final Pageable pageable) {
        return transferenciaService.obterTodasTransferencias(pageable);
    }

    @GetMapping("/por-operador-e-data")
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciaPorOperadorEPorData(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
            @RequestParam(required = false) final String nomeOperador,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataFinal
    ) {
        return transferenciaService.obterTransferenciaPorOperadorEPorData(pageable, nomeOperador, dataInicial, dataFinal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> obterTransferenciaPorId(@PathVariable final int id) {
        return transferenciaService.obterTransferenciaPorId(id);
    }

    @PostMapping("/realizar-transferencia")
    public ResponseEntity<TransferenciaDTO> realizarTransferencia(
            @RequestBody @Valid final TransferenciaDTO transferenciaDTO,
            @RequestParam final int numeroContaDestino,
            @RequestParam final int numeroContaOrigem
    ) {
        return transferenciaService.realizarTransferencia(transferenciaDTO, numeroContaDestino, numeroContaOrigem);
    }

    @PostMapping("/realizar-deposito")
    public ResponseEntity<TransferenciaDTO> realizarDeposito(
            @RequestBody @Valid final TransferenciaDTO transferenciaDTO,
            @RequestParam final int numeroContaDestino
    ) {
        return transferenciaService.realizarDeposito(transferenciaDTO, numeroContaDestino);
    }

    @PostMapping("/realizar-saque")
    public ResponseEntity<TransferenciaDTO> realizarSaque(
            @RequestBody @Valid final TransferenciaDTO transferenciaDTO,
            @RequestParam final int numeroContaDestino
    ) {
        return transferenciaService.realizarSaque(transferenciaDTO, numeroContaDestino);
    }

    @GetMapping("/por-periodo")
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorPeriodo(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataFinal
    ) {
        return transferenciaService.obterTransferenciasPorPeriodo(pageable, dataInicial, dataFinal);
    }

    @GetMapping("/por-operador")
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorOperador(
            @RequestParam final String nomeOperador,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        return transferenciaService.obterTransferenciasPorOperador(pageable, nomeOperador);
    }

    @GetMapping("/por-conta/{idConta}")
    public ResponseEntity<Page<TransferenciaDTO>> obterTransferenciasPorConta(@PathVariable int idConta,
                                                                              @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable
    ) {
        return transferenciaService.obterTransferenciasPorConta(pageable, idConta);
    }

    @GetMapping("/saldo-total-por-periodo")
    public ResponseEntity<BigDecimal> obterSaldoTotalPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime dataFinal
    ) {
        return transferenciaService.obterSaldoTotalPorPeriodo(dataInicial, dataFinal);
    }

    @GetMapping("/saldo-total-por-operador")
    public ResponseEntity<BigDecimal> obterSaldoTotalPorOperador(@RequestParam final int numeroConta) {
        return transferenciaService.obterSaldoTotalPorNumeroConta(numeroConta);
    }
}