package br.edu.zup.tax_calc_api.controllers;

import br.edu.zup.tax_calc_api.dtos.TaxRequestDTO;
import br.edu.zup.tax_calc_api.models.TaxEntity;
import br.edu.zup.tax_calc_api.services.TaxService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos")
public class TaxController {
    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping
    public ResponseEntity<List<TaxEntity>> getAllTax() {
        return ResponseEntity.ok().body(taxService.getAllTax());
    }

    // todo @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaxEntity> createTax(@Valid @RequestBody TaxRequestDTO taxRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taxService.createTax(taxRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxEntity> getTaxById(@PathVariable Long id) {
        return ResponseEntity.ok().body(taxService.getTaxById(id));
    }

    // todo @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.noContent().build();
    }
}
