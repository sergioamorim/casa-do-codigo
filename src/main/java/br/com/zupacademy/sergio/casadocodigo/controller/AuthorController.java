package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorDto;
import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorRequest;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.DuplicateAuthorRequestEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorRepository authorRepository;
  private final DuplicateAuthorRequestEmailValidator duplicateAuthorRequestEmailValidator;

  @Autowired
  public AuthorController(
    AuthorRepository authorRepository,
    DuplicateAuthorRequestEmailValidator duplicateAuthorRequestEmailValidator
  ) {
    this.authorRepository = authorRepository;
    this.duplicateAuthorRequestEmailValidator = duplicateAuthorRequestEmailValidator;
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(this.duplicateAuthorRequestEmailValidator);
  }

  @PostMapping
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
    return ResponseEntity.ok(new AuthorDto(this.authorRepository.save(authorRequest.asAuthor())));
  }

}
