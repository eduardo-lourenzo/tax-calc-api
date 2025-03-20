package br.edu.zup.tax_calc_api.services;

import br.edu.zup.tax_calc_api.dtos.TaxRequestDTO;
import br.edu.zup.tax_calc_api.exceptions.TaxAlreadyExistsException;
import br.edu.zup.tax_calc_api.exceptions.TaxNotFoundException;
import br.edu.zup.tax_calc_api.models.TaxEntity;
import br.edu.zup.tax_calc_api.repositories.TaxRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {
    private final TaxRepository taxRepository;

    public TaxServiceImpl(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    @Override
    public List<TaxEntity> getAllTax() {
        return taxRepository.findAll();
    }

    @Override
    public TaxEntity createTax(TaxRequestDTO taxRequestDTO) {
        if (taxRepository.existsByName(taxRequestDTO.getName())) {
            throw new TaxAlreadyExistsException("O imposto " + taxRequestDTO.getName() + " já existe.");
        }

        TaxEntity newTax = new TaxEntity();

        newTax.setName(taxRequestDTO.getName());
        newTax.setDescription(taxRequestDTO.getDescription());
        newTax.setAliquot(taxRequestDTO.getAliquot());

        return taxRepository.save(newTax);
    }

    @Override
    public TaxEntity getTaxById(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() ->
                        new TaxNotFoundException("O imposto com id " + id + " não foi encontrado.")
                );
    }

    @Override
    public void deleteTax(Long id) {
        if (!taxRepository.existsById(id)) {
            throw new TaxNotFoundException("O imposto com id " + id + " não foi encontrado.");
        }

        taxRepository.deleteById(id);
    }
}
