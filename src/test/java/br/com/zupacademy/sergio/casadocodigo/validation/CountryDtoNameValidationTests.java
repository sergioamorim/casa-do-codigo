package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CountryDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class CountryDtoNameValidationTests {
  private final Validator validator;

  @Autowired
  public CountryDtoNameValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should not accept an empty name")
  void shouldNotAcceptAnEmptyName() {
    CountryDto countryDtoWithEmptyName = new CountryDto("");
    Assertions.assertEquals(1, validator.validate(countryDtoWithEmptyName).size());
  }

  @Test
  @DisplayName("Should not accept a blank name")
  void shouldNotAcceptABlankName() {
    CountryDto countryDtoWithBlankName = new CountryDto(" ");
    Assertions.assertEquals(1, validator.validate(countryDtoWithBlankName).size());
  }

  @Test
  @DisplayName("Should not accept a null name")
  void shouldNotAcceptANullName() {
    CountryDto countryDtoWithNullName = new CountryDto((String) null);
    Assertions.assertEquals(1, validator.validate(countryDtoWithNullName).size());
  }

  @Test
  @DisplayName("Should accept a regular name")
  void shouldAcceptARegularName() {
    CountryDto countryDtoWithRegularName = new CountryDto("name");
    Assertions.assertEquals(0, validator.validate(countryDtoWithRegularName).size());
  }

  @Test
  @DisplayName("Should not accept a duplicate country name")
  void shouldNotAcceptADuplicateCountryName(@Autowired CountryRepository countryRepository) {
    CountryDto countryDto = new CountryDto("duplicate test");
    Assertions.assertEquals(0, validator.validate(countryDto).size());

    countryRepository.save(countryDto.toCountry());
    Assertions.assertEquals(1, validator.validate(countryDto).size());
  }
}
