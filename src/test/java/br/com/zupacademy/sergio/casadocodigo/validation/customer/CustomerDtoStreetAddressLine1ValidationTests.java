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
public class CustomerDtoStreetAddressLine1ValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoStreetAddressLine1ValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto street address line 1 validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null street address line 1")
  void shouldNotAcceptNullStreetAddressLine1() {

    CustomerDto customerDtoWithNullStreetAddressLine1 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      null,
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullStreetAddressLine1).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty street address line 1")
  void shouldNotAcceptEmptyStreetAddressLine1() {

    CustomerDto customerDtoWithEmptyStreetAddressLine1 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyStreetAddressLine1).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank street address line 1")
  void shouldNotAcceptBlankStreetAddressLine1() {

    CustomerDto customerDtoWithBlankStreetAddressLine1 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      " ",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankStreetAddressLine1).size()
    );
  }

  @Test
  @DisplayName("Should accept regular street address line 1")
  void shouldAcceptRegularStreetAddressLine1() {

    CustomerDto customerDtoWithRegularStreetAddressLine1 = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "street address line 1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithRegularStreetAddressLine1).size()
    );
  }
}
