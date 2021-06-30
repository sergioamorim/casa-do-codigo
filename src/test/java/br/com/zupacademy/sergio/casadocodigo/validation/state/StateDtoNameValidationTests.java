package br.com.zupacademy.sergio.casadocodigo.validation.state;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.dto.StateDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class StateDtoNameValidationTests {
  private final Validator validator;

  @Autowired
  public StateDtoNameValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(new Country("state validation tests A"));
    countryRepository.save(new Country("state validation tests B"));
  }

  @Test
  @DisplayName("Should not accept an empty name")
  void shouldNotAcceptAnEmptyName() {
    StateDto stateDtoWithEmptyName = new StateDto("", (long) 1);
    Assertions.assertEquals(1, validator.validate(stateDtoWithEmptyName).size());
  }

  @Test
  @DisplayName("Should not accept a blank name")
  void shouldNotAcceptABlankName() {
    StateDto stateDtoWithBlankName = new StateDto(" ", (long) 1);
    Assertions.assertEquals(1, validator.validate(stateDtoWithBlankName).size());
  }

  @Test
  @DisplayName("Should not accept a null name")
  void shouldNotAcceptANullName() {
    StateDto stateDtoWithNullName = new StateDto(null, (long) 1);
    Assertions.assertEquals(1, validator.validate(stateDtoWithNullName).size());
  }

  @Test
  @DisplayName("Should accept a regular name")
  void shouldAcceptARegularName() {
    StateDto stateDtoWithRegularName = new StateDto("name", (long) 1);
    Assertions.assertEquals(0, validator.validate(stateDtoWithRegularName).size());
  }
}
