package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorDto;
import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorRequest;
import br.com.zupacademy.sergio.casadocodigo.validation.DuplicateEmailAuthorRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorRepository authorRepository;
  private final DuplicateEmailAuthorRequestValidator duplicateEmailAuthorRequestValidator;

  @Autowired
  public AuthorController(
    AuthorRepository authorRepository,
    DuplicateEmailAuthorRequestValidator duplicateEmailAuthorRequestValidator
  ) {
    this.authorRepository = authorRepository;
    this.duplicateEmailAuthorRequestValidator = duplicateEmailAuthorRequestValidator;
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(this.duplicateEmailAuthorRequestValidator);
  }

  @PostMapping
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
    return ResponseEntity.ok(new AuthorDto(this.authorRepository.save(authorRequest.asAuthor())));
  }

}
