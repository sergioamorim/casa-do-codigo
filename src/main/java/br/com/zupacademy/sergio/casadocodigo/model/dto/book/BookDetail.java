package br.com.zupacademy.sergio.casadocodigo.model.dto.book;

import br.com.zupacademy.sergio.casadocodigo.model.Book;
import br.com.zupacademy.sergio.casadocodigo.model.dto.author.AuthorOnBookDetail;

import java.math.BigDecimal;

public class BookDetail {
  private final String title;
  private final String brief;
  private final String summary;
  private final BigDecimal price;
  private final Integer pageQuantity;
  private final String isbn;
  private final AuthorOnBookDetail author;

  public BookDetail(Book book) {
    this.title = book.getTitle();
    this.brief = book.getBrief();
    this.summary = book.getSummary();
    this.price = book.getPrice();
    this.pageQuantity = book.getPageQuantity();
    this.isbn = book.getIsbn();
    this.author = new AuthorOnBookDetail(book.getAuthor());
  }

  public String getTitle() {
    return this.title;
  }

  public String getBrief() {
    return this.brief;
  }

  public String getSummary() {
    return this.summary;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public Integer getPageQuantity() {
    return this.pageQuantity;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public AuthorOnBookDetail getAuthor() {
    return this.author;
  }
}
