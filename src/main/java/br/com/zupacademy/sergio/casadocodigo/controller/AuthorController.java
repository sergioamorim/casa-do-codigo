package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.dto.AuthorDto;
import br.com.zupacademy.sergio.casadocodigo.dto.AuthorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorController(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @PostMapping
  public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
    return ResponseEntity.ok(new AuthorDto(this.authorRepository.save(authorRequest.asAuthor())));
  }

}
