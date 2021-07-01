package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.model.Country;
import br.com.zupacademy.sergio.casadocodigo.model.State;
import br.com.zupacademy.sergio.casadocodigo.repository.CountryRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.StateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/customers";

  @Autowired
  public CustomerControllerTests(MockMvc mockMvc) {
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
  @DisplayName("Should return ok and the persisted customer id on body when posting with a valid customer")
  void shouldReturnOkAndThePersistedCustomerIdOnBodyWhenPostingWithAValidCustomer(
    @Autowired CountryRepository countryRepository,
    @Autowired StateRepository stateRepository
  ) throws Exception {
    Country country = countryRepository.save(new Country("ok and id on body"));
    stateRepository.save(new State("ok and id on body", country));
    String customerDtoAsJson =
      "{" +
        " 'email': 'okandidonbody@example.com', " +
        " 'givenName': 'given name', " +
        " 'familyName': 'family name', " +
        " 'nationalRegistryId': 'national registry id ok and id on body', " +
        " 'streetAddressLine1': 'street address line 1', " +
        " 'streetAddressLine2': 'street address line 2', " +
        " 'city': 'customer city', " +
        " 'countryId': '1', " +
        " 'stateId': '1', " +
        " 'phoneNumber': 'phone number', " +
        " 'postalCode': 'postal code' " +
        "}";

    this.mockMvc.perform(
      post(this.urlTemplate)
        .contentType("application/json")
        .content(customerDtoAsJson.replace("'", "\""))
    )
      .andExpect(status().isOk())
      .andExpect(content().string(matchesPattern("[0-9]+")));
  }

  @Test
  @DisplayName("Should accept null state when country has no states")
  void shouldAcceptNullStateWhenCountryHasNoStates(
    @Autowired CountryRepository countryRepository
  ) throws Exception {

    Long countryId = countryRepository.save(
      new Country("country without states")
    ).getId();

    String customerDtoAsJson =
      "{" +
        " 'email': 'countryhasnostates@example.com', " +
        " 'givenName': 'given name', " +
        " 'familyName': 'family name', " +
        " 'nationalRegistryId': 'national registry id country has no states', " +
        " 'streetAddressLine1': 'street address line 1', " +
        " 'streetAddressLine2': 'street address line 2', " +
        " 'city': 'customer city', " +
        " 'countryId': '" + countryId + "', " +
        " 'stateId': null, " +
        " 'phoneNumber': 'phone number', " +
        " 'postalCode': 'postal code' " +
        "}";

    this.mockMvc.perform(
      post(this.urlTemplate)
        .contentType("application/json")
        .content(customerDtoAsJson.replace("'", "\""))
    )
      .andExpect(status().isOk())
      .andExpect(content().string(matchesPattern("[0-9]+")));
  }

  @Test
  @DisplayName("Should not accept null state when country has states")
  void shouldNotAcceptNullStateWhenCountryHasStates(
    @Autowired CountryRepository countryRepository,
    @Autowired StateRepository stateRepository
  ) throws Exception {

    Country country = countryRepository.save(new Country("country with state"));
    stateRepository.save(new State("state on country with state", country));

    String customerDtoAsJson =
      "{" +
        " 'email': 'countrywithstate@example.com', " +
        " 'givenName': 'given name', " +
        " 'familyName': 'family name', " +
        " 'nationalRegistryId': 'national registry id country with state', " +
        " 'streetAddressLine1': 'street address line 1', " +
        " 'streetAddressLine2': 'street address line 2', " +
        " 'city': 'customer city', " +
        " 'countryId': '" + country.getId() + "', " +
        " 'stateId': null, " +
        " 'phoneNumber': 'phone number', " +
        " 'postalCode': 'postal code' " +
        "}";

    this.mockMvc.perform(
      post(this.urlTemplate)
        .contentType("application/json")
        .content(customerDtoAsJson.replace("'", "\""))
    )
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.*", hasSize(2)))
      .andExpect(jsonPath("globalErrors").exists())
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").exists())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty())
      .andExpect(jsonPath("fieldErrors.*", hasSize(1)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].field").isString())
      .andExpect(jsonPath("fieldErrors[0].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[0].field").value("stateId"))
      .andExpect(jsonPath("fieldErrors[0].message").exists())
      .andExpect(jsonPath("fieldErrors[0].message").isString())
      .andExpect(jsonPath("fieldErrors[0].message").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("Should not accept a state id from another country id")
  void shouldNotAcceptAStateIdFromAnotherCountryId(
    @Autowired CountryRepository countryRepository,
    @Autowired StateRepository stateRepository
  ) throws Exception {

    Country countryA = countryRepository.save(
      new Country("country state id from another country id A")
    );

    Country countryB = countryRepository.save(
      new Country("country state id from another country id B")
    );

    State state = stateRepository.save(
      new State("country state id from another country id", countryA)
    );

    String customerDtoAsJson =
      "{" +
        " 'email': 'countrywithstate@example.com', " +
        " 'givenName': 'given name', " +
        " 'familyName': 'family name', " +
        " 'nationalRegistryId': 'national registry id country with state', " +
        " 'streetAddressLine1': 'street address line 1', " +
        " 'streetAddressLine2': 'street address line 2', " +
        " 'city': 'customer city', " +
        " 'countryId': '" + countryB.getId() + "', " +
        " 'stateId': " + state.getId() + ", " +
        " 'phoneNumber': 'phone number', " +
        " 'postalCode': 'postal code' " +
        "}";

    this.mockMvc.perform(
      post(this.urlTemplate)
        .contentType("application/json")
        .content(customerDtoAsJson.replace("'", "\""))
    )
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.*", hasSize(2)))
      .andExpect(jsonPath("globalErrors").exists())
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").exists())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty())
      .andExpect(jsonPath("fieldErrors.*", hasSize(1)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].field").isString())
      .andExpect(jsonPath("fieldErrors[0].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[0].field").value("stateId"))
      .andExpect(jsonPath("fieldErrors[0].message").exists())
      .andExpect(jsonPath("fieldErrors[0].message").isString())
      .andExpect(jsonPath("fieldErrors[0].message").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("Should return bad request with the validation errors")
  void shouldReturnBadRequestWithTheValidationErrors() {

  }

  @Test
  @DisplayName("Should return bad request with the validation errors when posting with empty object")
  void shouldReturnBadRequestWithTheValidationErrorsWhenPostingWithEmptyObject() throws Exception {

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content("{ }")
      )
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.*", hasSize(2)))
      .andExpect(jsonPath("globalErrors").exists())
      .andExpect(jsonPath("globalErrors").isArray())
      .andExpect(jsonPath("globalErrors").isEmpty())
      .andExpect(jsonPath("fieldErrors").exists())
      .andExpect(jsonPath("fieldErrors").isArray())
      .andExpect(jsonPath("fieldErrors").isNotEmpty())
      .andExpect(jsonPath("fieldErrors.*", hasSize(10)))
      .andExpect(jsonPath("fieldErrors[0].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[0].field").exists())
      .andExpect(jsonPath("fieldErrors[0].field").isString())
      .andExpect(jsonPath("fieldErrors[0].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[0].message").exists())
      .andExpect(jsonPath("fieldErrors[0].message").isString())
      .andExpect(jsonPath("fieldErrors[0].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[1].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[1].field").exists())
      .andExpect(jsonPath("fieldErrors[1].field").isString())
      .andExpect(jsonPath("fieldErrors[1].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[1].message").exists())
      .andExpect(jsonPath("fieldErrors[1].message").isString())
      .andExpect(jsonPath("fieldErrors[1].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[2].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[2].field").exists())
      .andExpect(jsonPath("fieldErrors[2].field").isString())
      .andExpect(jsonPath("fieldErrors[2].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[2].message").exists())
      .andExpect(jsonPath("fieldErrors[2].message").isString())
      .andExpect(jsonPath("fieldErrors[2].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[3].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[3].field").exists())
      .andExpect(jsonPath("fieldErrors[3].field").isString())
      .andExpect(jsonPath("fieldErrors[3].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[3].message").exists())
      .andExpect(jsonPath("fieldErrors[3].message").isString())
      .andExpect(jsonPath("fieldErrors[3].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[4].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[4].field").exists())
      .andExpect(jsonPath("fieldErrors[4].field").isString())
      .andExpect(jsonPath("fieldErrors[4].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[4].message").exists())
      .andExpect(jsonPath("fieldErrors[4].message").isString())
      .andExpect(jsonPath("fieldErrors[4].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[5].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[5].field").exists())
      .andExpect(jsonPath("fieldErrors[5].field").isString())
      .andExpect(jsonPath("fieldErrors[5].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[5].message").exists())
      .andExpect(jsonPath("fieldErrors[5].message").isString())
      .andExpect(jsonPath("fieldErrors[5].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[6].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[6].field").exists())
      .andExpect(jsonPath("fieldErrors[6].field").isString())
      .andExpect(jsonPath("fieldErrors[6].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[6].message").exists())
      .andExpect(jsonPath("fieldErrors[6].message").isString())
      .andExpect(jsonPath("fieldErrors[6].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[7].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[7].field").exists())
      .andExpect(jsonPath("fieldErrors[7].field").isString())
      .andExpect(jsonPath("fieldErrors[7].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[7].message").exists())
      .andExpect(jsonPath("fieldErrors[7].message").isString())
      .andExpect(jsonPath("fieldErrors[7].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[8].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[8].field").exists())
      .andExpect(jsonPath("fieldErrors[8].field").isString())
      .andExpect(jsonPath("fieldErrors[8].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[8].message").exists())
      .andExpect(jsonPath("fieldErrors[8].message").isString())
      .andExpect(jsonPath("fieldErrors[8].message").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[9].*", hasSize(2)))
      .andExpect(jsonPath("fieldErrors[9].field").exists())
      .andExpect(jsonPath("fieldErrors[9].field").isString())
      .andExpect(jsonPath("fieldErrors[9].field").isNotEmpty())
      .andExpect(jsonPath("fieldErrors[9].message").exists())
      .andExpect(jsonPath("fieldErrors[9].message").isString())
      .andExpect(jsonPath("fieldErrors[9].message").isNotEmpty())
    ;
  }
}
