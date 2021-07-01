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
public class CustomerDtoCityValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoCityValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(new Country("customer dto city validation tests"));
  }

  @Test
  @DisplayName("Should not accept null city")
  void shouldNotAcceptNullCity() {

    CustomerDto customerDtoWithNullCity = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      null,
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullCity).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty city")
  void shouldNotAcceptEmptyCity() {

    CustomerDto customerDtoWithEmptyCity = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyCity).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank city")
  void shouldNotAcceptBlankCity() {

    CustomerDto customerDtoWithBlankCity = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      " ",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankCity).size()
    );
  }

  @Test
  @DisplayName("Should accept regular city")
  void shouldAcceptRegularCity() {

    CustomerDto customerDtoWithRegularCity = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "String nationalRegistryId",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "regular city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithRegularCity).size()
    );
  }
}
