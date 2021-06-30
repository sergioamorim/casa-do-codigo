package br.com.zupacademy.sergio.casadocodigo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String title;

  private String brief;
  private String summary;
  private BigDecimal price;
  private Integer pageQuantity;

  @Column(unique = true)
  private String isbn;

  private LocalDate postDate;

  @NotNull
  @ManyToOne
  private Category category;

  @NotNull
  @ManyToOne
  private Author author;

  public Book(
    String title,
    String brief,
    String summary,
    BigDecimal price,
    Integer pageQuantity,
    String isbn,
    LocalDate postDate,
    Category category,
    Author author
  ) {
    this.title = title;
    this.brief = brief;
    this.summary = summary;
    this.price = price;
    this.pageQuantity = pageQuantity;
    this.isbn = isbn;
    this.postDate = postDate;
    this.category = category;
    this.author = author;
  }

  @Deprecated  // jpa
  protected Book() {
  }

  public Long getId() {
    return this.id;
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

  public Author getAuthor() {
    return this.author;
  }

  public Long getCategoryId() {
    return this.category.getId();
  }

  public Long getAuthorId() {
    return this.author.getId();
  }
}
