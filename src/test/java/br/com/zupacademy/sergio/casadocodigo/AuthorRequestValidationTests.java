package br.com.zupacademy.sergio.casadocodigo;

import br.com.zupacademy.sergio.casadocodigo.dto.AuthorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

public class AuthorRequestValidationTests {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  @DisplayName("Should have a name")
  void shouldHaveAName() {
    AuthorRequest authorRequestWithEmptyName = new AuthorRequest("", "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyName).size());

    AuthorRequest authorRequestWithBlankName = new AuthorRequest(" ", "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankName).size());

    AuthorRequest authorRequestWithNullName = new AuthorRequest(null, "email@email.com", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullName).size());

    AuthorRequest authorRequestWithRegularName = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularName).size());
  }

  @Test
  @DisplayName("Should have a valid email")
  void shouldHaveAValidEmail() {
    AuthorRequest authorRequestWithEmptyEmail = new AuthorRequest("name", "", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyEmail).size());

    AuthorRequest authorRequestWithBlankEmail = new AuthorRequest("name", " ", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankEmail).size());

    AuthorRequest authorRequestWithNullEmail = new AuthorRequest("name", null, "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullEmail).size());

    AuthorRequest authorRequestWithInvalidEmail1 = new AuthorRequest("name", "email", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail1).size());

    AuthorRequest authorRequestWithInvalidEmail2 = new AuthorRequest("name", "email@", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail2).size());

    AuthorRequest authorRequestWithInvalidEmail3 = new AuthorRequest("name", "email@.", "description");
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidEmail3).size());

    AuthorRequest authorRequestWithRegularEmail = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularEmail).size());
  }

  @Test
  @DisplayName("Should have a description and it should not have more than 400 characters")
  void shouldHaveADescriptionAndItShouldNotHaveMoreThan400Characters() {
    AuthorRequest authorRequestWithEmptyDescription = new AuthorRequest("name", "email@email.com", "");
    Assertions.assertEquals(1, validator.validate(authorRequestWithEmptyDescription).size());

    AuthorRequest authorRequestWithBlankDescription = new AuthorRequest("name", "email@email.com", " ");
    Assertions.assertEquals(1, validator.validate(authorRequestWithBlankDescription).size());

    AuthorRequest authorRequestWithNullDescription = new AuthorRequest("name", "email@email.com", null);
    Assertions.assertEquals(1, validator.validate(authorRequestWithNullDescription).size());

    AuthorRequest authorRequestWithInvalidDescription = new AuthorRequest("name", "email@email.com", "a".repeat(401));
    Assertions.assertEquals(1, validator.validate(authorRequestWithInvalidDescription).size());

    AuthorRequest authorRequestWithRegularDescription1 = new AuthorRequest("name", "email@email.com", "description");
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularDescription1).size());

    AuthorRequest authorRequestWithRegularDescription2 = new AuthorRequest("name", "email@email.com", "a".repeat(400));
    Assertions.assertEquals(0, validator.validate(authorRequestWithRegularDescription2).size());
  }
}
