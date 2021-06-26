package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CategoryDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DuplicateCategoryNameValidator implements Validator {

  private final CategoryRepository categoryRepository;

  @Autowired
  public DuplicateCategoryNameValidator(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return CategoryDto.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object object, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    this.checkForDuplicateName((CategoryDto) object, errors);
  }

  private void checkForDuplicateName(CategoryDto categoryDto, Errors errors) {
    if (0 != categoryRepository.countByName(categoryDto.getName())) {
      errors.rejectValue("name", "name must be unique");
    }
  }
}
