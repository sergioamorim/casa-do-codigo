package br.com.zupacademy.sergio.casadocodigo.repository;

import br.com.zupacademy.sergio.casadocodigo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
