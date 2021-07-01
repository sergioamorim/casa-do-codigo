package br.com.zupacademy.sergio.casadocodigo.validation.customer;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.dto.CustomerDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class CustomerDtoGivenNameValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoGivenNameValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto given name validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null given name")
  void shouldNotAcceptNullGivenName() {

    CustomerDto customerDtoWithNullGivenName = new CustomerDto(
      "email@example.com",
      null,
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
      1, this.validator.validate(customerDtoWithNullGivenName).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty given name")
  void shouldNotAcceptEmptyGivenName() {

    CustomerDto customerDtoWithEmptyGivenName = new CustomerDto(
      "email@example.com",
      "",
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
      1, this.validator.validate(customerDtoWithEmptyGivenName).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank given name")
  void shouldNotAcceptBlankGivenName() {
    CustomerDto customerDtoWithBlankGivenName = new CustomerDto(
      "email@example.com",
      " ",
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
      1, this.validator.validate(customerDtoWithBlankGivenName).size()
    );
  }

  @Test
  @DisplayName("Should accept regular given name")
  void shouldAcceptRegularGivenName() {

    CustomerDto customerDtoWithRegularGivenName = new CustomerDto(
      "email@example.com",
      "given name",
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
      0, this.validator.validate(customerDtoWithRegularGivenName).size()
    );
  }
}
