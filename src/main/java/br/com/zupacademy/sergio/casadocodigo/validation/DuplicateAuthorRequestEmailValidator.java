package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.AuthorRequest;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DuplicateAuthorRequestEmailValidator implements Validator {

  private final AuthorRepository authorRepository;

  @Autowired
  public DuplicateAuthorRequestEmailValidator(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return AuthorRequest.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    this.checkForDuplicateEmail((AuthorRequest) object, errors);
  }

  private void checkForDuplicateEmail(AuthorRequest authorRequest, Errors errors) {
    if (0 != authorRepository.countByEmail(authorRequest.getEmail())) {
      errors.rejectValue("email", "email must be unique");
    }
  }
}