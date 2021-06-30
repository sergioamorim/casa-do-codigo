package br.com.zupacademy.sergio.casadocodigo.model.dto.author;

import br.com.zupacademy.sergio.casadocodigo.model.Author;

public class AuthorOnBookDetail {
  private final String name;
  private final String description;

  public AuthorOnBookDetail(Author author) {
    this.name = author.getName();
    this.description = author.getDescription();
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }
}
