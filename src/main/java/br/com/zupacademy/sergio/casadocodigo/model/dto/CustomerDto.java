package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.Customer;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.ForeignKeyExists;
import br.com.zupacademy.sergio.casadocodigo.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CustomerDto {

  @NotBlank
  private final String givenName;

  @Email
  @NotEmpty
  @UniqueValue(domainClass = Customer.class, fieldName = "email")
  private final String email;

  @NotBlank
  private final String familyName;

  @NotBlank
  @UniqueValue(domainClass = Customer.class, fieldName = "nationalRegistryId")
  private final String nationalRegistryId;

  @NotBlank
  private final String streetAddressLine1;

  @NotBlank
  private final String streetAddressLine2;

  @NotBlank
  private final String city;

  @ForeignKeyExists(domainClass = Country.class)
  private final Long countryId;

  private final Long stateId;

  @NotBlank
  private final String phoneNumber;

  @NotBlank
  private final String postalCode;

  public CustomerDto(
    String email,
    String givenName,
    String familyName,
    String nationalRegistryId,
    String streetAddressLine1,
    String streetAddressLine2,
    String city,
    Long countryId,
    Long stateId,
    String phoneNumber,
    String postalCode
  ) {
    this.givenName = givenName;
    this.email = email;
    this.familyName = familyName;
    this.nationalRegistryId = nationalRegistryId;
    this.streetAddressLine1 = streetAddressLine1;
    this.streetAddressLine2 = streetAddressLine2;
    this.city = city;
    this.countryId = countryId;
    this.stateId = stateId;
    this.phoneNumber = phoneNumber;
    this.postalCode = postalCode;
  }

  public Customer toCustomer(
    CountryRepository countryRepository, StateRepository stateRepository
  ) {
    return new Customer(
      this.email,
      this.givenName,
      this.familyName,
      this.nationalRegistryId,
      this.streetAddressLine1,
      this.streetAddressLine2,
      this.city,
      countryRepository.findById(this.countryId).get(),
      this.stateId != null ? stateRepository.findById(this.stateId).get() : null,
      this.phoneNumber,
      this.postalCode
    );
  }

  public Long getCountryId() {
    return this.countryId;
  }

  public Long getStateId() {
    return this.stateId;
  }
}
