package br.com.zupacademy.sergio.casadocodigo.controller.book;

import br.com.zupacademy.sergio.casadocodigo.model.Author;
import br.com.zupacademy.sergio.casadocodigo.model.Book;
import br.com.zupacademy.sergio.casadocodigo.model.Category;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_CLASS)
public class BookControllerGetTests {
  private final MockMvc mockMvc;
  private final String urlTemplate = "/books";
  private final BookRepository bookRepository;

  @Autowired
  public BookControllerGetTests(
    MockMvc mockMvc, BookRepository bookRepository
  ) {
    this.mockMvc = mockMvc;
    this.bookRepository = bookRepository;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
  }


  @Test
  @DisplayName("Should return empty list when there is no book in the database")
  void shouldReturnEmptyListWhenThereIsNoBookInTheDatabase() throws Exception {
    this.bookRepository.deleteAll();
    this.mockMvc.perform(get(this.urlTemplate).accept("application/json"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  @DisplayName("Should return a JSON array with the only book in the database")
  void shouldReturnAJsonArrayWithTheOnlyBookInTheDatabase(
    @Autowired CategoryRepository categoryRepository,
    @Autowired AuthorRepository authorRepository
  ) throws Exception {
    this.bookRepository.deleteAll();
    Author author = authorRepository.findById((long) 1).get();
    Category category = categoryRepository.findById((long) 1).get();
    this.bookRepository.save(new Book(
      "book title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      category,
      author
    ));

    this.mockMvc.perform(get(this.urlTemplate).accept("application/json"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(1)))
      .andExpect(jsonPath("$[0].*", hasSize(2)))
      .andExpect(jsonPath("$[0]", hasKey("id")))
      .andExpect(jsonPath("$[0].id").isNotEmpty())
      .andExpect(jsonPath("$[0]", hasKey("title")))
      .andExpect(jsonPath("$[0].title", is("book title")));
  }

  @Test
  @DisplayName("Should return two books in the JSON array when there is two books in the database")
  void shouldReturnTwoBooksInTheJsonArrayWhenThereIsTwoBooksInTheDatabase(
    @Autowired CategoryRepository categoryRepository,
    @Autowired AuthorRepository authorRepository
  ) throws Exception {
    this.bookRepository.deleteAll();
    Author author = authorRepository.findById((long) 1).get();
    Category category = categoryRepository.findById((long) 1).get();
    this.bookRepository.save(new Book(
      "book title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbnA",
      LocalDate.now().plusDays(1),
      category,
      author
    ));
    this.bookRepository.save(new Book(
      "second book",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbnB",
      LocalDate.now().plusDays(1),
      category,
      author
    ));

    this.mockMvc.perform(get(this.urlTemplate).accept("application/json"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$[0].*", hasSize(2)))
      .andExpect(jsonPath("$[0]", hasKey("id")))
      .andExpect(jsonPath("$[0].id").isNotEmpty())
      .andExpect(jsonPath("$[0]", hasKey("title")))
      .andExpect(jsonPath("$[0].title", is("book title")))
      .andExpect(jsonPath("$[1].*", hasSize(2)))
      .andExpect(jsonPath("$[1]", hasKey("id")))
      .andExpect(jsonPath("$[1].id").isNotEmpty())
      .andExpect(jsonPath("$[1]", hasKey("title")))
      .andExpect(jsonPath("$[1].title", is("second book")));
  }
}
