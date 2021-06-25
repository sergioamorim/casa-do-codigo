package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

public class AuthorRequestNameValidationTests {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("Should not accept an empty name")
  void shouldNotAcceptAnEmptyName() {
    AuthorRequest authorRequestWithEmptyName = new AuthorRequest("", "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyName).size());
  }

  @Test
  @DisplayName("Should not accept a blank name")
  void shouldNotAcceptABlankName() {
    AuthorRequest authorRequestWithBlankName = new AuthorRequest(" ", "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankName).size());
  }

  @Test
  @DisplayName("Should not accept a null name")
  void shouldNotAcceptANullName() {
    AuthorRequest authorRequestWithNullName = new AuthorRequest(null, "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullName).size());
  }

  @Test
  @DisplayName("Should accept a regular name")
  void shouldAcceptARegularName() {
    AuthorRequest authorRequestWithRegularName = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularName).size());
  }
}
