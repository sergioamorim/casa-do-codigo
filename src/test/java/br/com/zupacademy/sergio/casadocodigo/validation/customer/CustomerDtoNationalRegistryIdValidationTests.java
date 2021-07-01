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
public class CustomerDtoNationalRegistryIdValidationTests {
  private final Validator validator;

  @Autowired
  public CustomerDtoNationalRegistryIdValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(
      new Country("customer dto national registry id validation tests")
    );
  }

  @Test
  @DisplayName("Should not accept null national registry id")
  void shouldNotAcceptNullNationalRegistryId() {

    CustomerDto customerDtoWithNullNationalRegistryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      null,
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithNullNationalRegistryId).size()
    );
  }

  @Test
  @DisplayName("Should not accept empty national registry id")
  void shouldNotAcceptEmptyNationalRegistryId() {

    CustomerDto customerDtoWithEmptyNationalRegistryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithEmptyNationalRegistryId).size()
    );
  }

  @Test
  @DisplayName("Should not accept blank national registry id")
  void shouldNotAcceptBlankNationalRegistryId() {

    CustomerDto customerDtoWithBlankNationalRegistryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      " ",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithBlankNationalRegistryId).size()
    );
  }

  @Test
  @DisplayName("Should accept regular national registry id")
  void shouldAcceptRegularNationalRegistryId() {

    CustomerDto customerDtoWithRegularNationalRegistryId = new CustomerDto(
      "email@example.com",
      "String givenName",
      "String familyName",
      "regular national registry id",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    Assertions.assertEquals(
      0, this.validator.validate(customerDtoWithRegularNationalRegistryId).size()
    );
  }

  @Test
  @DisplayName("Should not accept duplicate national registry id")
  void shouldNotAcceptDuplicateNationalRegistryId(
    @Autowired CustomerRepository customerRepository,
    @Autowired CountryRepository countryRepository,
    @Autowired StateRepository stateRepository
  ) {

    stateRepository.save(
      new State("duplicate national registry id", countryRepository.findById(1L).get())
    );

    CustomerDto customerDtoWithDuplicateNationalRegistryIdA = new CustomerDto(
      "duplicatenationalregistryida@example.com",
      "String givenName",
      "String familyName",
      "duplicate national registry id",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    CustomerDto customerDtoWithDuplicateNationalRegistryIdB = new CustomerDto(
      "duplicatenationalregistryidb@example.com",
      "String givenName",
      "String familyName",
      "duplicate national registry id",
      "String streetAddressLine1",
      "String streetAddressLine2",
      "String city",
      1L,
      1L,
      "String phoneNumber",
      "String postalCode"
    );

    customerRepository.save(
      customerDtoWithDuplicateNationalRegistryIdA.toCustomer(
        countryRepository, stateRepository
      )
    );

    Assertions.assertEquals(
      1, this.validator.validate(customerDtoWithDuplicateNationalRegistryIdB).size()
    );
  }
}
