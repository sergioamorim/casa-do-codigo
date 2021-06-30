package br.com.zupacademy.sergio.casadocodigo.validation.state;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.dto.StateDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class StateDtoCountryIdValidationTests {
  private final Validator validator;

  @Autowired
  public StateDtoCountryIdValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should accept existing country id")
  void shouldAcceptAExistingCountryId(@Autowired CountryRepository countryRepository) {
    countryRepository.save(new Country("state country id validation tests"));
    StateDto stateDtoWithRegularName = new StateDto("existing country id", (long) 1);
    Assertions.assertEquals(0, validator.validate(stateDtoWithRegularName).size());
  }

  @Test
  @DisplayName("Should not accept non existent country id")
  void shouldAcceptARegularName() {
    StateDto stateDtoWithRegularName = new StateDto("non existent country id", (long) 999);
    Assertions.assertEquals(1, validator.validate(stateDtoWithRegularName).size());
  }
}
