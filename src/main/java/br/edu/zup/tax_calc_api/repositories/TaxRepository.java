package br.edu.zup.tax_calc_api.repositories;

import br.edu.zup.tax_calc_api.models.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, Long> {
    Optional<TaxEntity> findByName(String name);
    Boolean existsByName(String name);
}
