package br.com.controller;

import br.com.model.Veiculo;
import br.com.service.LojaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veiculos")
public class LojaController {
    private final LojaService service;

    public LojaController(LojaService service) {
        this.service = service;
    }

    @GetMapping("/comprar")
    public ResponseEntity<Veiculo> comprar() {
        Veiculo veiculo = this.service.vender();
        return ResponseEntity.ok(veiculo);
    }
}
