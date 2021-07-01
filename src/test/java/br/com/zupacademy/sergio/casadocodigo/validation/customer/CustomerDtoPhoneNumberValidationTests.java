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
public class CustomerDtoPhoneNumberValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoPhoneNumberValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto phone number validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null phone number")
  void shouldNotAcceptNullPhoneNumber() {

    CustomerDto customerDtoWithNullPhoneNumber = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      null,
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullPhoneNumber).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty phone number")
  void shouldNotAcceptEmptyPhoneNumber() {

    CustomerDto customerDtoWithEmptyPhoneNumber = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyPhoneNumber).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank phone number")
  void shouldNotAcceptBlankPhoneNumber() {

    CustomerDto customerDtoWithBlankPhoneNumber = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      " ",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankPhoneNumber).size()
    );
  }

  @Test
  @DisplayName("Should accept regular phone number")
  void shouldAcceptRegularPhoneNumber() {

    CustomerDto customerDtoWithBlankPhoneNumber = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "phone number",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithBlankPhoneNumber).size()
    );
  }
}
