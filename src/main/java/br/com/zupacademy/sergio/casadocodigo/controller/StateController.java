package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.StateDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.DuplicateStateOnCountryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/states")
public class StateController {
  private final StateRepository stateRepository;
  private final CountryRepository countryRepository;
  private final DuplicateStateOnCountryValidator duplicateStateOnCountryValidator;


  @Autowired
  public StateController(
    StateRepository stateRepository,
    CountryRepository countryRepository,
    DuplicateStateOnCountryValidator duplicateStateOnCountryValidator
  ) {
    this.stateRepository = stateRepository;
    this.countryRepository = countryRepository;
    this.duplicateStateOnCountryValidator = duplicateStateOnCountryValidator;
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(this.duplicateStateOnCountryValidator);
  }

  @PostMapping
  public ResponseEntity<StateDto> createState(@RequestBody @Valid StateDto stateDto) {
    return ResponseEntity.ok(
      new StateDto(this.stateRepository.save(stateDto.toState(this.countryRepository)))
    );
  }
}
