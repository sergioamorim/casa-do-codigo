package br.com.zupacademy.sergio.casadocodigo.repository;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
}
