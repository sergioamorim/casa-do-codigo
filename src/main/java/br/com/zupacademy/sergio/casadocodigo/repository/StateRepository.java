package br.com.zupacademy.sergio.casadocodigo.repository;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
  int countByNameAndCountry(String name, Country country);
}
