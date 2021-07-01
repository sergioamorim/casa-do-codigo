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
public class CustomerDtoFamilyNameValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoFamilyNameValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto family name validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null family name")
  void shouldNotAcceptNullFamilyName() {

    CustomerDto customerDtoWithNullFamilyName = new CustomerDto(
      "email@example.com",
      "String givenName",
      null,
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
      1, this.validator.validate(customerDtoWithNullFamilyName).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty family name")
  void shouldNotAcceptEmptyFamilyName() {

    CustomerDto customerDtoWithEmptyFamilyName = new CustomerDto(
      "email@example.com",
      "String givenName",
      "",
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
      1, this.validator.validate(customerDtoWithEmptyFamilyName).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank family name")
  void shouldNotAcceptBlankFamilyName() {

    CustomerDto customerDtoWithBlankFamilyName = new CustomerDto(
      "email@example.com",
      "String givenName",
      " ",
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
      1, this.validator.validate(customerDtoWithBlankFamilyName).size()
    );
  }

  @Test
  @DisplayName("Should accept regular family name")
  void shouldAcceptRegularFamilyName() {

    CustomerDto customerDtoWithRegularFamilyName = new CustomerDto(
      "email@example.com",
      "String givenName",
      "family name",
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
      0, this.validator.validate(customerDtoWithRegularFamilyName).size()
    );
  }
}
