package br.edu.zup.tax_calc_api.controllers;

import br.edu.zup.tax_calc_api.dtos.CalcRequestDTO;
import br.edu.zup.tax_calc_api.dtos.CalcResponseDTO;
import br.edu.zup.tax_calc_api.services.CalcService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo @PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/calculo")
public class CalcController {
    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @PostMapping
    ResponseEntity<CalcResponseDTO> calculateTax(@Valid @RequestBody CalcRequestDTO calcRequestDTO) {
        return ResponseEntity.ok().body(calcService.calculateTax(calcRequestDTO));
    }
}
