package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class CountryDto {

  @NotBlank
  @UniqueValue(domainClass = Country.class, fieldName = "name")
  private final String name;

  @JsonCreator
  public CountryDto(String name) {
    this.name = name;
  }

  public CountryDto(Country country) {
    this.name = country.getName();
  }

  public Country toCountry() {
    return new Country(this.getName());
  }

  public String getName() {
    return this.name;
  }
}
