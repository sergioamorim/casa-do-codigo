package br.com.zupacademy.sergio.casadocodigo.repository;

import br.com.zupacademy.sergio.casadocodigo.model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
