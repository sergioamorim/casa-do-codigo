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
public class CustomerDtoStreetAddressLine2ValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoStreetAddressLine2ValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto street address line 2 validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null street address line 2")
  void shouldNotAcceptNullStreetAddressLine2() {

    CustomerDto customerDtoWithNullStreetAddressLine2 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      null,
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullStreetAddressLine2).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty street address line 2")
  void shouldNotAcceptEmptyStreetAddressLine2() {

    CustomerDto customerDtoWithEmptyStreetAddressLine2 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyStreetAddressLine2).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank street address line 2")
  void shouldNotAcceptBlankStreetAddressLine2() {

    CustomerDto customerDtoWithBlankStreetAddressLine2 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      " ",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankStreetAddressLine2).size()
    );
  }

  @Test
  @DisplayName("Should accept regular street address line 2")
  void shouldAcceptRegularStreetAddressLine2() {

    CustomerDto customerDtoWithRegularStreetAddressLine2 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine2",
      "street address line 1",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithRegularStreetAddressLine2).size()
    );
  }
}
