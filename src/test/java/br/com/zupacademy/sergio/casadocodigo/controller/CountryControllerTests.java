package br.com.zupacademy.sergio.casadocodigo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/countries";

  @Autowired
  public CountryControllerTests(MockMvc mockMvc) {
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
  @DisplayName("Should return ok and the persisted country as JSON when posting with a valid country - test A")
  void shouldReturnOkAndThePersistedCountryAsJsonWhenPostingWithAValidCountryA() throws Exception {
    String countryDtoAsJson = "{ \"name\": \"posting with a valid country - test A\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(countryDtoAsJson)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("posting with a valid country - test A"));
  }

  @Test
  @DisplayName("Should return ok and the persisted country as JSON when posting with a valid country - test B")
  void shouldReturnOkAndThePersistedCountryAsJsonWhenPostingWithAValidCountryB() throws Exception {
    String countryDtoAsJson = "{ \"name\": \"country\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(countryDtoAsJson)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.*", hasSize(1)))
      .andExpect(jsonPath("name").exists())
      .andExpect(jsonPath("name").isString())
      .andExpect(jsonPath("name").isNotEmpty())
      .andExpect(jsonPath("name").value("country"));
  }

  @Test
  @DisplayName("Should return bad request when there is already a country with the same name")
  void shouldReturnBadRequestWhenThereIsAlreadyACountryWithTheSameName() throws Exception {
    String countryDtoUniqueAsJson = "{ \"name\": \"unique\" }";
    String countryDtoDistinctAsJson = "{ \"name\": \"distinct\" }";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(countryDtoUniqueAsJson)
      )
      .andExpect(status().isOk());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(countryDtoUniqueAsJson)
      )
      .andExpect(status().isBadRequest());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(countryDtoDistinctAsJson)
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
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty())
      .andExpect(jsonPath("fieldErrors", hasSize(1)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].field").isString())
      .andExpect(jsonPath("fieldErrors[0].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[0].field").value("name"))
      .andExpect(jsonPath("fieldErrors[0].message").exists())
      .andExpect(jsonPath("fieldErrors[0].message").isString())
      .andExpect(jsonPath("fieldErrors[0].message").isNotEmpty());
  }
}
