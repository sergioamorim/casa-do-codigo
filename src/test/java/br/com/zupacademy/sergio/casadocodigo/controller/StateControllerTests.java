package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import org.junit.jupiter.api.BeforeAll;
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
public class StateControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/states";

  @Autowired
  public StateControllerTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @BeforeAll
  static void setUp(@Autowired CountryRepository countryRepository) {
    countryRepository.save(new Country("state controller tests country A"));
    countryRepository.save(new Country("state controller tests country B"));
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
      .andExpect(jsonPath("fieldErrors", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].message").exists())
      .andExpect(jsonPath("fieldErrors[1].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[1].field").exists())
      .andExpect(jsonPath("fieldErrors[1].message").exists());
  }

  @Test
  @DisplayName("Should return ok with the state persisted when posting with valid state")
  void shouldReturnOkWithTheStatePersistedWhenPostingWithValidState(
    @Autowired CountryRepository countryRepository
  ) throws Exception {
    String stateDtoAsJson = "{ \"name\": \"valid state\", \"countryId\": \"1\" }";

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJson))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.*", hasSize(2)))
      .andExpect(jsonPath("name").exists())
      .andExpect(jsonPath("name").isString())
      .andExpect(jsonPath("name").isNotEmpty())
      .andExpect(jsonPath("name").value("valid state"))
      .andExpect(jsonPath("countryId").exists())
      .andExpect(jsonPath("countryId").isNotEmpty())
      .andExpect(jsonPath("countryId").value("1"));
  }

  @Test
  @DisplayName("Should not accept a duplicate state name for the same country")
  void shouldNotAcceptADuplicateCountryName() throws Exception {
    String stateDtoAsJson =
      "{" +
        " \"name\": \"duplicate state name for the same country\"," +
        " \"countryId\": \"1\" " +
        "}";

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJson))
      .andExpect(status().isOk());

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJson))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty())
      .andExpect(jsonPath("fieldErrors", hasSize(1)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].field").value("name"))
      .andExpect(jsonPath("fieldErrors[0].message").exists());
  }

  @Test
  @DisplayName("Should allow a duplicate state name for different countries")
  void shouldAllowADuplicateStateNameForDifferentCountries() throws Exception {
    String stateDtoAsJsonA =
      "{" +
        " \"name\": \"duplicate state name for different countries\"," +
        " \"countryId\": \"1\" " +
        "}";
    String stateDtoAsJsonB =
      "{" +
        " \"name\": \"duplicate state name for different countries\"," +
        " \"countryId\": \"2\" " +
        "}";

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJsonA))
      .andExpect(status().isOk());

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJsonB))
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Should allow different state names for the same country")
  void shouldAllowDifferentStateNamesForTheSameCountry() throws Exception {
    String stateDtoAsJsonA =
      "{" +
        " \"name\": \"different state names for the same country A\"," +
        " \"countryId\": \"1\" " +
        "}";
    String stateDtoAsJsonB =
      "{" +
        " \"name\": \"different state names for the same country B\"," +
        " \"countryId\": \"1\" " +
        "}";

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJsonA))
      .andExpect(status().isOk());

    this.mockMvc
      .perform(post(this.urlTemplate).contentType("application/json").content(stateDtoAsJsonB))
      .andExpect(status().isOk());
  }
}
