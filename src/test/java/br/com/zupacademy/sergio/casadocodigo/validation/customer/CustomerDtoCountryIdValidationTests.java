package br.com.zupacademy.sergio.casadocodigo.validation.customer;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.dto.CustomerDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
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
public class CustomerDtoCountryIdValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoCountryIdValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should not accept null country id")
  void shouldNotAcceptNullCountryId() {

    CustomerDto customerDtoWithNullCountryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      null,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullCountryId).size()
    );
  }

  @Test
  @DisplayName("Should not accept non existent country id")
  void shouldNotAcceptNonExistentCountryId() {

    CustomerDto customerDtoWithNullCountryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      999L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullCountryId).size()
    );
  }

  @Test
  @DisplayName("Should accept existing country id")
  void shouldAcceptExistingCountryId(@Autowired CountryRepository countryRepository) {

    countryRepository.save(new Country("existing country id"));

    CustomerDto customerDtoWithNullCountryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithNullCountryId).size()
    );
  }
}
