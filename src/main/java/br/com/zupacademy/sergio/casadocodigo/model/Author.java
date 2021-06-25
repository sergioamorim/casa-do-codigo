package br.com.zupacademy.sergio.casadocodigo.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String description;

  @Column(nullable = false)
  @CreationTimestamp
  private LocalDateTime createdOn;

  @Deprecated // spring
  public Author() { }

  public Author(String name, String email, String description) {
    this.name = name;
    this.email = email;
    this.description = description;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public String getDescription() {
    return this.description;
  }

  public LocalDateTime getCreatedOn() {
    return this.createdOn;
  }
}
