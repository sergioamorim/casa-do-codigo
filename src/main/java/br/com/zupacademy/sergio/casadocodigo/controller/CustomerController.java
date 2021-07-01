package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CustomerDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CustomerRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.CustomerStateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  private final CustomerRepository customerRepository;
  private final StateRepository stateRepository;
  private final CountryRepository countryRepository;
  private final CustomerStateValidator customerStateValidator;

  @Autowired
  public CustomerController(
    CustomerRepository customerRepository,
    StateRepository stateRepository,
    CountryRepository countryRepository,
    CustomerStateValidator customerStateValidator) {
    this.customerRepository = customerRepository;
    this.stateRepository = stateRepository;
    this.countryRepository = countryRepository;
    this.customerStateValidator = customerStateValidator;
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(this.customerStateValidator);
  }

  @PostMapping
  public ResponseEntity<Long> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
    return ResponseEntity.ok(
      this.customerRepository.save(
        customerDto.toCustomer(this.countryRepository, this.stateRepository)).getId()
    );
  }
}
