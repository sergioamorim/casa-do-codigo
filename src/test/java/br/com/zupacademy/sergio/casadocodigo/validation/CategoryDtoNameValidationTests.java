package br.com.zupacademy.sergio.casadocodigo.validation;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
public class CategoryDtoNameValidationTests {

  private final Validator validator;

  @Autowired
  public CategoryDtoNameValidationTests(Validator validator) {
    this.validator = validator;
  }

  @Test
  @DisplayName("Should not accept an empty name")
  void shouldNotAcceptAnEmptyName() {
    CategoryDto categoryDtoWithEmptyName = new CategoryDto("");
    Assertions.assertEquals(1, validator.validate(categoryDtoWithEmptyName).size());
  }

  @Test
  @DisplayName("Should not accept a blank name")
  void shouldNotAcceptABlankName() {
    CategoryDto categoryDtoWithBlankName = new CategoryDto(" ");
    Assertions.assertEquals(1, validator.validate(categoryDtoWithBlankName).size());
  }

  @Test
  @DisplayName("Should not accept a null name")
  void shouldNotAcceptANullName() {
    CategoryDto categoryDtoWithNullName = new CategoryDto((String) null);
    Assertions.assertEquals(1, validator.validate(categoryDtoWithNullName).size());
  }

  @Test
  @DisplayName("Should accept a regular name")
  void shouldAcceptARegularName() {
    CategoryDto categoryDtoWithRegularName = new CategoryDto("name");
    Assertions.assertEquals(0, validator.validate(categoryDtoWithRegularName).size());
  }

}
