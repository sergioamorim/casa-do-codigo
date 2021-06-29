package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Author;
import br.com.zupacademy.sergio.casadocodigo.model.Book;
import br.com.zupacademy.sergio.casadocodigo.model.Category;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.ForeignKeyExists;
import br.com.zupacademy.sergio.casadocodigo.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BookDto {
  @NotBlank
  @UniqueValue(domainClass = Book.class, fieldName = "title")
  private final String title;

  @NotBlank
  @Length(max = 500)
  private final String brief;

  private final String summary;

  @NotNull
  @Min(20)
  private final BigDecimal price;

  @NotNull
  @Min(100)
  private final Integer pageQuantity;

  @NotBlank
  @UniqueValue(domainClass = Book.class, fieldName = "isbn")
  private final String isbn;

  @Future
  @NotNull
  private final LocalDate postDate;

  @ForeignKeyExists(domainClass = Category.class)
  private final Long categoryId;

  @ForeignKeyExists(domainClass = Author.class)
  private final Long authorId;

  @JsonCreator
  public BookDto(
    String title,
    String brief,
    String summary,
    BigDecimal price,
    Integer pageQuantity,
    String isbn,
    LocalDate postDate,
    Long categoryId,
    Long authorId
  ) {
    this.title = title;
    this.brief = brief;
    this.summary = summary;
    this.price = price;
    this.pageQuantity = pageQuantity;
    this.isbn = isbn;
    this.postDate = postDate;
    this.categoryId = categoryId;
    this.authorId = authorId;
  }

  public BookDto(Book book) {
    this.title = book.getTitle();
    this.brief = book.getBrief();
    this.summary = book.getSummary();
    this.price = book.getPrice();
    this.pageQuantity = book.getPageQuantity();
    this.isbn = book.getIsbn();
    this.postDate = book.getPostDate();
    this.categoryId = book.getCategoryId();
    this.authorId = book.getAuthorId();
  }

  public Book asBook(
    CategoryRepository categoryRepository,
    AuthorRepository authorRepository
  ) {
    return new Book(
      this.getTitle(),
      this.getBrief(),
      this.getSummary(),
      this.getPrice(),
      this.getPageQuantity(),
      this.getIsbn(),
      this.getPostDate(),
      categoryRepository.findById(this.getCategoryId()).get(),
      authorRepository.findById(this.getAuthorId()).get()
    );
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

  public LocalDate getPostDate() {
    return this.postDate;
  }

  public Long getCategoryId() {
    return this.categoryId;
  }

  public Long getAuthorId() {
    return this.authorId;
  }
}
