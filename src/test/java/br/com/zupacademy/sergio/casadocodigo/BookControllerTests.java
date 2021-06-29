package br.com.zupacademy.sergio.casadocodigo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_CLASS)
public class BookControllerTests {

  private final MockMvc mockMvc;
  private final String urlTemplate = "/books";

  @Autowired
  public BookControllerTests(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
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
      .andExpect(jsonPath("fieldErrors").isNotEmpty());
  }

  @Test
  @DisplayName("Should return ok and the persisted book as JSON when posting with a valid book - test A")
  void shouldReturnOkAndThePersistedCategoryAsJsonWhenPostingWithAValidCategoryA() throws Exception {
    String bookDtoAsJson =
      "{" +
        " 'title': 'titleA', " +
        " 'brief': 'brief', " +
        " 'summary': 'summary', " +
        " 'price': '20.00', " +
        " 'pageQuantity': '100', " +
        " 'isbn': 'isbnA', " +
        " 'postDate': '2052-07-30', " +
        " 'categoryId': '1', " +
        " 'authorId': '2' " +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(bookDtoAsJson.replace("'", "\""))
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("title").value("titleA"))
      .andExpect(jsonPath("brief").value("brief"))
      .andExpect(jsonPath("summary").value("summary"))
      .andExpect(jsonPath("price").value("20.0"))
      .andExpect(jsonPath("pageQuantity").value("100"))
      .andExpect(jsonPath("isbn").value("isbnA"))
      .andExpect(jsonPath("postDate").value("2052-07-30"))
      .andExpect(jsonPath("categoryId").value("1"))
      .andExpect(jsonPath("authorId").value("2"));
  }

  @Test
  @DisplayName("Should return ok and the persisted book as JSON when posting with a valid book - test B")
  void shouldReturnOkAndThePersistedCategoryAsJsonWhenPostingWithAValidCategoryB() throws Exception {
    String bookDtoAsJson =
      "{" +
        " 'title': 'book', " +
        " 'brief': 'book brief', " +
        " 'summary': 'book summary', " +
        " 'price': '20.01', " +
        " 'pageQuantity': '101', " +
        " 'isbn': 'isbnB', " +
        " 'postDate': '2051-06-29', " +
        " 'categoryId': '2', " +
        " 'authorId': '1' " +
        "}";

    this.mockMvc
      .perform(
        post(this.urlTemplate)
          .contentType("application/json")
          .content(bookDtoAsJson.replace("'", "\""))
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("title").value("book"))
      .andExpect(jsonPath("brief").value("book brief"))
      .andExpect(jsonPath("summary").value("book summary"))
      .andExpect(jsonPath("price").value("20.01"))
      .andExpect(jsonPath("pageQuantity").value("101"))
      .andExpect(jsonPath("isbn").value("isbnB"))
      .andExpect(jsonPath("postDate").value("2051-06-29"))
      .andExpect(jsonPath("categoryId").value("2"))
      .andExpect(jsonPath("authorId").value("1"));
  }
}
