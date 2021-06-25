package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Author;

import java.time.LocalDateTime;

public class AuthorDto {

  private final String name;
  private final String email;
  private final String description;
  private final LocalDateTime createdOn;

  public AuthorDto(Author author) {
    this.name = author.getName();
    this.email = author.getEmail();
    this.description = author.getDescription();
    this.createdOn = author.getCreatedOn();
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
