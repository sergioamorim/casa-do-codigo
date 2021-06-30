package br.com.zupacademy.sergio.casadocodigo.controller.book;

import br.com.zupacademy.sergio.casadocodigo.model.dto.book.BookDto;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BookCreateController {
  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;
  private final AuthorRepository authorRepository;

  @Autowired
  public BookCreateController(
    BookRepository bookRepository,
    CategoryRepository categoryRepository,
    AuthorRepository authorRepository
  ) {
    this.bookRepository = bookRepository;
    this.categoryRepository = categoryRepository;
    this.authorRepository = authorRepository;
  }

  @PostMapping("/books")
  public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
    return ResponseEntity.ok(
      new BookDto(
        this.bookRepository.save(
          bookDto.asBook(this.categoryRepository, this.authorRepository)
        )
      )
    );
  }
}
