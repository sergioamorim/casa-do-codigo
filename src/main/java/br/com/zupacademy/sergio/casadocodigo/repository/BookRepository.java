package br.com.zupacademy.sergio.casadocodigo.repository;

import br.com.zupacademy.sergio.casadocodigo.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
