package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CategoryDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import br.com.zupacademy.sergio.casadocodigo.validation.DuplicateCategoryNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryRepository categoryRepository;
  private final DuplicateCategoryNameValidator duplicateCategoryNameValidator;

  @Autowired
  public CategoryController(CategoryRepository categoryRepository, DuplicateCategoryNameValidator duplicateCategoryNameValidator) {
    this.categoryRepository = categoryRepository;
    this.duplicateCategoryNameValidator = duplicateCategoryNameValidator;
  }

  @InitBinder
  public void initBinder(WebDataBinder webDataBinder) {
    webDataBinder.addValidators(this.duplicateCategoryNameValidator);
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
    return ResponseEntity.ok(new CategoryDto(this.categoryRepository.save(categoryDto.asCategory())));
  }
}
