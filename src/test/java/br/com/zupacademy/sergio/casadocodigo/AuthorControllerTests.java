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
public class AuthorControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/authors";

  @Autowired
  public AuthorControllerTests(MockMvc mockMvc) {
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
  @DisplayName("Should return bad request when posting an invalid author")
  void shouldReturnBadRequestWhenPostingAnInvalidAuthor() throws Exception {
    String authorRequestAsJson =
      "{" +
        " 'name': 'author'," +
        " 'email': 'email@email.com'," +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson.replace("'", "\""))
      ).andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Should return ok and the persisted author as JSON when posting with a valid author - test A")
  void shouldReturnOkAndThePersistedAuthorAsJsonWhenPostingWithAValidAuthorA() throws Exception {
    String authorRequestAsJson =
      "{" +
        " 'name': 'author'," +
        " 'email': 'email@email.com'," +
        " 'description': 'description' " +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson.replace("'", "\""))
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("name").value("author"))
      .andExpect(jsonPath("email").value("email@email.com"))
      .andExpect(jsonPath("description").value("description"))
      .andExpect(jsonPath("createdOn").isNotEmpty())
      .andExpect(jsonPath("createdOn").isString());
  }

  @Test
  @DisplayName("Should return ok and the persisted author as JSON when posting with a valid author - test B")
  void shouldReturnOkAndThePersistedAuthorAsJsonWhenPostingWithAValidAuthorB() throws Exception {
    String authorRequestAsJson =
      "{" +
        " 'name': 'kat'," +
        " 'email': 'kat@u.com'," +
        " 'description': 'soo' " +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson.replace("'", "\""))
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("name").value("kat"))
      .andExpect(jsonPath("email").value("kat@u.com"))
      .andExpect(jsonPath("description").value("soo"))
      .andExpect(jsonPath("createdOn").isNotEmpty())
      .andExpect(jsonPath("createdOn").isString());
  }

  @Test
  @DisplayName("Should return bad request when the posted author has a duplicate email")
  void shouldReturnBadRequestWhenThePostedAuthorHasADuplicateEmail() throws Exception {
    String authorRequestAsJson1 =
      "{" +
        " 'name': 'distinct name'," +
        " 'email': 'unique@email.com'," +
        " 'description': 'distinct description' " +
        "}";

    String authorRequestAsJson2 =
      "{" +
        " 'name': 'new name'," +
        " 'email': 'unique@email.com'," +
        " 'description': 'new description' " +
        "}";

    String authorRequestAsJson3 =
      "{" +
        " 'name': 'third name'," +
        " 'email': 'distinct@email.com'," +
        " 'description': 'third description' " +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson1.replace("'", "\""))
      )
      .andExpect(status().isOk());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson2.replace("'", "\""))
      )
      .andExpect(status().isBadRequest());

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(authorRequestAsJson3.replace("'", "\""))
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
      .andExpect(jsonPath("fieldErrors").isNotEmpty());
  }


}
