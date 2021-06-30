package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.dto.CategoryDto;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
    return ResponseEntity.ok(
      new CategoryDto(this.categoryRepository.save(categoryDto.asCategory()))
    );
  }
}
