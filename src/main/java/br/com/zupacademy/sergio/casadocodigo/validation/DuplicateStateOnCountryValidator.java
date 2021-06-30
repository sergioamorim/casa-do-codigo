package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.StateDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DuplicateStateOnCountryValidator implements Validator {
  private final StateRepository stateRepository;
  private final CountryRepository countryRepository;

  @Autowired
  public DuplicateStateOnCountryValidator(
    StateRepository stateRepository,
    CountryRepository countryRepository
  ) {
    this.stateRepository = stateRepository;
    this.countryRepository = countryRepository;
  }


  @Override
  public boolean supports(Class<?> aClass) {
    return StateDto.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    this.checkForDuplicateStateOnCountry((StateDto) object, errors);
  }

  private void checkForDuplicateStateOnCountry(StateDto stateDto, Errors errors) {
    if (this.isStateNameUniqueForCountry(stateDto)) {
      errors.rejectValue("name", "DuplicateStateOnCountry", "state name must be unique on each country");
    }
  }

  private boolean isStateNameUniqueForCountry(StateDto stateDto) {
    return (
      0 != this.stateRepository.countByNameAndCountry(
        stateDto.getName(), this.countryRepository.findById(stateDto.getCountryId()).get()
      )
    );
  }
}
