package br.com.zupacademy.sergio.casadocodigo.validation.customer;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.State;
import br.com.zupacademy.sergio.casadocodigo.model.dto.CustomerDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CustomerRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class CustomerDtoEmailValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoEmailValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(new Country("customer dto email validation tests"));
  }

  @Test
  @DisplayName("Should not accept null email")
  void shouldNotAcceptNullEmail() {

    CustomerDto customerDtoWithNullEmail = new CustomerDto(
      null,
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
      1, this.validator.validate(customerDtoWithNullEmail).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty email")
  void shouldNotAcceptEmptyEmail() {

    CustomerDto customerDtoWithEmptyEmail = new CustomerDto(
      "",
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
      1, this.validator.validate(customerDtoWithEmptyEmail).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank email")
  void shouldNotAcceptBlankEmail() {

    CustomerDto customerDtoWithBlankEmail = new CustomerDto(
      " ",
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
      1, this.validator.validate(customerDtoWithBlankEmail).size()
    );
  }

  @Test
  @DisplayName("Should not accept invalid email format")
  void shouldNotAcceptInvalidEmailFormat() {

    CustomerDto customerDtoWithInvalidEmailFormat = new CustomerDto(
      "email@.com",
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
      1, this.validator.validate(customerDtoWithInvalidEmailFormat).size()
    );
  }

  @Test
  @DisplayName("Should accept valid email format")
  void shouldAcceptValidEmailFormat() {

    CustomerDto customerDtoWithValidEmailFormat = new CustomerDto(
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
      0, this.validator.validate(customerDtoWithValidEmailFormat).size()
    );
  }

  @Test
  @DisplayName("Should not accept duplicate email")
  void shouldNotAcceptDuplicateEmail(
    @Autowired StateRepository stateRepository,
    @Autowired CountryRepository countryRepository,
    @Autowired CustomerRepository customerRepository
  ) {
    stateRepository.save(
      new State("duplicate email", countryRepository.findById(1L).get())
    );

    CustomerDto customerDtoWithDuplicateEmailA = new CustomerDto(
      "duplicateemail@example.com",
      "String givenName",
      "String familyName",
      "duplicate national registry id A",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    CustomerDto customerDtoWithDuplicateEmailB = new CustomerDto(
      "duplicateemail@example.com",
      "String givenName",
      "String familyName",
      "duplicate national registry id B",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    customerRepository.save(
      customerDtoWithDuplicateEmailA.toCustomer(
        countryRepository, stateRepository
      )
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithDuplicateEmailB).size()
    );
  }
}
