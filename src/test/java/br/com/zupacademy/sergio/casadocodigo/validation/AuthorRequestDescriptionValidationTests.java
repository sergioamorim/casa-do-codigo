package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class AuthorRequestDescriptionValidationTests {

  private final Validator validator;

  @Autowired
  public AuthorRequestDescriptionValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should not accept an empty description")
  void shouldNotAcceptAnEmptyDescription() {
    AuthorRequest authorRequestWithEmptyDescription = new AuthorRequest("name", "email@email.com", "");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyDescription).size());
  }

  @Test
  @DisplayName("Should not accept a blank description")
  void shouldNotAcceptABlankDescription() {
    AuthorRequest authorRequestWithBlankDescription = new AuthorRequest("name", "email@email.com", " ");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankDescription).size());
  }

  @Test
  @DisplayName("Should not accept a null description")
  void shouldNotAcceptANullDescription() {
    AuthorRequest authorRequestWithNullDescription = new AuthorRequest("name", "email@email.com", null);
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullDescription).size());
  }

  @Test
  @DisplayName("Should not accept a description with more than 400 characters")
  void shouldNotAcceptADescriptionWithMoreThan400Characters() {
    AuthorRequest authorRequestWithInvalidDescription = new AuthorRequest("name", "email@email.com", "a".repeat(401));
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidDescription).size());
  }

  @Test
  @DisplayName("Should accept a description with 400 characters")
  void shouldAcceptADescriptionWith400Characters() {
    AuthorRequest authorRequestWithRegularDescription2 = new AuthorRequest("name", "email@email.com", "a".repeat(400));
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularDescription2).size());
  }

  @Test
  @DisplayName("Should accept a regular description")
  void shouldAcceptARegularDescription() {
    AuthorRequest authorRequestWithRegularDescription1 = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularDescription1).size());
  }
}
