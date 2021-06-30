package br.com.zupacademy.sergio.casadocodigo.validation.author;

import br.com.zupacademy.sergio.casadocodigo.model.dto.author.AuthorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class AuthorRequestEmailValidationTests {

  private final Validator validator;

  @Autowired
  public AuthorRequestEmailValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should not accept an empty email")
  void shouldNotAcceptAnEmptyEmail() {
    AuthorRequest authorRequestWithEmptyEmail = new AuthorRequest("name", "", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyEmail).size());
  }

  @Test
  @DisplayName("Should not accept a blank email")
  void shouldNotAcceptABlankEmail() {
    AuthorRequest authorRequestWithBlankEmail = new AuthorRequest("name", " ", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankEmail).size());
  }

  @Test
  @DisplayName("Should not accept a null email")
  void shouldNotAcceptANullEmail() {
    AuthorRequest authorRequestWithNullEmail = new AuthorRequest("name", null, "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullEmail).size());
  }

  @Test
  @DisplayName("Should not accept an email without @")
  void shouldNotAcceptAnEmailWithoutAt() {
    AuthorRequest authorRequestWithInvalidEmail = new AuthorRequest("name", "email", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail).size());
  }

  @Test
  @DisplayName("Should not accept an email without a domain")
  void shouldNotAcceptAnEmailWithoutADomain() {
    AuthorRequest authorRequestWithInvalidEmail = new AuthorRequest("name", "email@", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail).size());
  }

  @Test
  @DisplayName("Should not accept an email with a malformed domain")
  void shouldNotAcceptAnEmailWithAMalformedDomain() {
    AuthorRequest authorRequestWithInvalidEmail = new AuthorRequest("name", "email@.", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail).size());
  }

  @Test
  @DisplayName("Should accept a regular email")
  void shouldAcceptARegularEmail() {
    AuthorRequest authorRequestWithRegularEmail = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularEmail).size());
  }
}
