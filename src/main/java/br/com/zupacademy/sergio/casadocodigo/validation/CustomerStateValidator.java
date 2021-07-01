package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.State;
import br.com.zupacademy.sergio.casadocodigo.model.dto.CustomerDto;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CustomerStateValidator implements Validator {

  private final StateRepository stateRepository;

  @Autowired
  public CustomerStateValidator(StateRepository stateRepository) {
    this.stateRepository = stateRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return CustomerDto.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    this.checkForStateInCountry((CustomerDto) object, errors);
  }

  private void checkForStateInCountry(CustomerDto customerDto, Errors errors) {
    if (null == customerDto.getStateId()) {
      this.checkIfCountryHasStates(customerDto, errors);
    } else {
      this.checkIfStateIdIsValid(
        this.stateRepository.findById(customerDto.getStateId()),
        customerDto.getCountryId(),
        errors
      );
    }
  }

  private void checkIfCountryHasStates(CustomerDto customerDto, Errors errors) {
    if (0 != this.stateRepository.countByCountry_Id(customerDto.getCountryId())) {
      errors.rejectValue(
        "stateId", "NullStateId", "state id is required for country with states"
      );
    }
  }

  private void checkIfStateIdIsValid(
    Optional<State> state, Long countryId, Errors errors
  ) {
    if (state.isEmpty()) {
      errors.rejectValue("stateId", "InvalidStateId", "invalid state id");
      return;
    }
    this.checkIfStateIsFromCountry(state.get(), countryId, errors);
  }

  private void checkIfStateIsFromCountry(State state, Long countryId, Errors errors) {
    if (!state.isFromCountryId(countryId)) {
      errors.rejectValue(
        "stateId", "StateIdMismatch", "state id is from another country"
      );
    }
  }
}
