package br.com.zupacademy.sergio.casadocodigo.controller.book;

import br.com.zupacademy.sergio.casadocodigo.model.Book;
import br.com.zupacademy.sergio.casadocodigo.model.dto.book.BookDetail;
import br.com.zupacademy.sergio.casadocodigo.model.dto.book.BookDtoTiny;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookReadController {
  private final BookRepository bookRepository;

  @Autowired
  public BookReadController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @GetMapping("/books")
  public ResponseEntity<List<BookDtoTiny>> listBooksTiny() {
    return ResponseEntity.ok(
      this.bookRepository.findAll()
        .stream()
        .map(BookDtoTiny::new)
        .collect(Collectors.toList())
    );
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<BookDetail> readBook(@PathVariable Long id) {
    return bookDetailResponse(this.bookRepository.findById(id));
  }

  private static ResponseEntity<BookDetail> bookDetailResponse(Optional<Book> queriedBook) {
    return queriedBook
      .map(bookFound -> ResponseEntity.ok(new BookDetail(bookFound)))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
