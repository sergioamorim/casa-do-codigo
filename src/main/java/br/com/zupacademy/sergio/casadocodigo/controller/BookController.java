package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.BookDto;
import br.com.zupacademy.sergio.casadocodigo.model.dto.BookDtoTiny;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;
  private final AuthorRepository authorRepository;

  @Autowired
  public BookController(
    BookRepository bookRepository,
    CategoryRepository categoryRepository,
    AuthorRepository authorRepository
  ) {
    this.bookRepository = bookRepository;
    this.categoryRepository = categoryRepository;
    this.authorRepository = authorRepository;
  }

  @GetMapping
  public ResponseEntity<List<BookDtoTiny>> listBooksTiny() {
    return ResponseEntity.ok(
      this.bookRepository.findAll()
        .stream()
        .map(BookDtoTiny::new)
        .collect(Collectors.toList())
    );
  }

  @PostMapping
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
