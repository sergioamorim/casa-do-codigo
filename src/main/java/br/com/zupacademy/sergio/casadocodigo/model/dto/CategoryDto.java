package br.com.zupacademy.sergio.casadocodigo.model.dto;

import br.com.zupacademy.sergio.casadocodigo.model.Category;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
  @NotBlank
  private String name;

  @Deprecated  // jackson
  public CategoryDto() {
  }

  public CategoryDto(String name) {
    this.name = name;
  }

  public CategoryDto(Category category) {
    this.name = category.getName();
  }

  public String getName() {
    return this.name;
  }

  @Deprecated  // jackson
  public void setName(String name) {
    this.name = name;
  }

  public Category asCategory() {
    return new Category(this.getName());
  }
}
