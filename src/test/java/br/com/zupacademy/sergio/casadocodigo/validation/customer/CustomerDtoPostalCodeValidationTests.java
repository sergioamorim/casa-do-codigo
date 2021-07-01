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
public class CustomerDtoPostalCodeValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoPostalCodeValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto postal code validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null postal code")
  void shouldNotAcceptNullPostalCode() {
    CustomerDto customerDtoWithNullPostalCode = new CustomerDto(
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
      null
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullPostalCode).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty postal code")
  void shouldNotAcceptEmptyPostalCode() {
    CustomerDto customerDtoWithEmptyPostalCode = new CustomerDto(
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
      ""
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyPostalCode).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank postal code")
  void shouldNotAcceptBlankPostalCode() {
    CustomerDto customerDtoWithBlankPostalCode = new CustomerDto(
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
      " "
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankPostalCode).size()
    );
  }

  @Test
  @DisplayName("Should accept regular postal code")
  void shouldAcceptRegularPostalCode() {
    CustomerDto customerDtoWithRegularPostalCode = new CustomerDto(
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
      "postal code"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithRegularPostalCode).size()
    );
  }
}
