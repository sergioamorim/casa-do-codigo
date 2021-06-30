package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.State;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.ForeignKeyExists;

import javax.validation.constraints.NotBlank;

public class StateDto {

  @NotBlank
  private final String name;

  @ForeignKeyExists(domainClass = Country.class)
  private final Long countryId;

  public StateDto(String name, Long countryId) {
    this.name = name;
    this.countryId = countryId;
  }

  public StateDto(State state) {
    this.name = state.getName();
    this.countryId = state.getCountryId();
  }

  public State toState(CountryRepository countryRepository) {
    return new State(
      this.getName(), countryRepository.findById(this.getCountryId()).get()
    );
  }

  public String getName() {
    return this.name;
  }

  public Long getCountryId() {
    return this.countryId;
  }
}
