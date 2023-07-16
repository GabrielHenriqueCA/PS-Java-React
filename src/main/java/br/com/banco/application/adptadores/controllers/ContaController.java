package br.com.banco.application.adptadores.controllers;

import br.com.banco.application.dtos.ContaDTO;
import br.com.banco.domain.ports.interfaces.IContaServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/contas")
@Validated
public class ContaController {
    private final IContaServicePort contaService;

    public ContaController(IContaServicePort contaService) {
        this.contaService = contaService;
    }


    @GetMapping("/obter")
    public ResponseEntity<Page<ContaDTO>> obterTodasAsContas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return contaService.obterTodasContas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> obterContaPorId(@PathVariable(value = "id") final int id) {
        return contaService.obterContaPorId(id);
    }

    @GetMapping
    public ResponseEntity<ContaDTO> obterContaPorNomeResponsavel(@RequestParam final String nomeResponsavel) {
        return contaService.obterContaPorNomeResponsavel(nomeResponsavel);
    }

    @PostMapping
    public ResponseEntity<Void> salvarConta(@RequestBody final ContaDTO contaDTO) {
        return contaService.salvarConta(contaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable final int id) {
        return contaService.deletarConta(id);
    }
}