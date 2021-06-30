package br.com.zupacademy.sergio.casadocodigo.model.dto.author;

import br.com.zupacademy.sergio.casadocodigo.model.Author;
import br.com.zupacademy.sergio.casadocodigo.validation.UniqueValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AuthorRequest {

  @NotBlank
  private final String name;

  @Email
  @NotEmpty
  @UniqueValue(domainClass = Author.class, fieldName = "email")
  private final String email;

  @NotBlank
  @Length(max = 400)
  private final String description;

  public AuthorRequest(String name, String email, String description) {
    this.email = email;
    this.name = name;
    this.description = description;
  }

  public Author asAuthor() {
    return new Author(this.name, this.getEmail(), this.description);
  }

  public String getEmail() {
    return this.email;
  }

}
