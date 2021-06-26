package br.com.zupacademy.sergio.casadocodigo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/categories";

  @Autowired
  public CategoryControllerTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Test
  @DisplayName("Should return bad request with empty body when posting with empty body")
  void shouldReturnBadRequestWithEmptyBodyWhenPostingWithEmptyBody() throws Exception {
    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json"))
      .andExpect(status().isBadRequest())
      .andExpect(content().string(""));
  }

  @Test
  @DisplayName("Should return bad request when posting an invalid category")
  void shouldReturnBadRequestWhenPostingAnInvalidCategory() throws Exception {
    String categoryDtoAsJson = "{ \"nam\": \"a\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoAsJson)
      ).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return ok and the persisted category as JSON when posting with a valid category - test A")
  void shouldReturnOkAndThePersistedCategoryAsJsonWhenPostingWithAValidCategoryA() throws Exception {
    String categoryDtoAsJson = "{ \"name\": \"name\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoAsJson)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("name"));
  }

  @Test
  @DisplayName("Should return ok and the persisted category as JSON when posting with a valid category - test B")
  void shouldReturnOkAndThePersistedCategoryAsJsonWhenPostingWithAValidCategoryB() throws Exception {
    String categoryDtoAsJson = "{ \"name\": \"category\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoAsJson)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("category"));
  }

  @Test
  @DisplayName("Should return bad request when there is already a category with the same name")
  void shouldReturnBadRequestWhenThereIsAlreadyACategoryWithTheSameName() throws Exception {
    String categoryDtoUniqueAsJson = "{ \"name\": \"unique\" }";
    String categoryDtoDistinctAsJson = "{ \"name\": \"distinct\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoUniqueAsJson)
      )
      .andExpect(status().isOk());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoUniqueAsJson)
      )
      .andExpect(status().isBadRequest());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(categoryDtoDistinctAsJson)
      )
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Should return bad request and a JSON with the validation problems")
  void shouldReturnBadRequestAndAJsonWithTheValidationProblems() throws Exception {
    String emptyObject = "{ }";
    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(emptyObject)
      )
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty());
  }
}
