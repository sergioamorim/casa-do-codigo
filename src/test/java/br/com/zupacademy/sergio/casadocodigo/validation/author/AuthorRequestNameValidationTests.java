package br.com.zupacademy.sergio.casadocodigo.validation.author;

import br.com.zupacademy.sergio.casadocodigo.model.dto.author.AuthorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.validation.Validator;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_CLASS)
public class AuthorRequestNameValidationTests {

  private final Validator validator;

  @Autowired
  public AuthorRequestNameValidationTests(Validator validator) {
    this.validator = validator;
  }

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
