package br.com.zupacademy.sergio.casadocodigo.model.dto.book;

import br.com.zupacademy.sergio.casadocodigo.model.Book;

public class BookDtoTiny {
  private final Long id;
  private final String title;

  public BookDtoTiny(Book book) {
    this.id = book.getId();
    this.title = book.getTitle();
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }
}
