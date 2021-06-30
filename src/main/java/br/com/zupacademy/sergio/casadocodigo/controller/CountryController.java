package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CountryDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/countries")
public class CountryController {
  private final CountryRepository countryRepository;

  @Autowired
  public CountryController(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  @PostMapping
  public ResponseEntity<CountryDto> createCountry(@RequestBody @Valid CountryDto countryDto) {
    return ResponseEntity.ok(
      new CountryDto(this.countryRepository.save(countryDto.toCountry()))
    );
  }
}
