package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Category;
import br.com.zupacademy.sergio.casadocodigo.validation.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
  @NotBlank
  @UniqueValue(domainClass = Category.class, fieldName = "name")
  private final String name;

  @JsonCreator
  public CategoryDto(String name) {
    this.name = name;
  }

  public CategoryDto(Category category) {
    this.name = category.getName();
  }

  public String getName() {
    return this.name;
  }

  public Category asCategory() {
    return new Category(this.getName());
  }
}
