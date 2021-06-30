package br.com.zupacademy.sergio.casadocodigo.controller.book;

import br.com.zupacademy.sergio.casadocodigo.model.Author;
import br.com.zupacademy.sergio.casadocodigo.model.Book;
import br.com.zupacademy.sergio.casadocodigo.model.Category;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_CLASS)
public class BookReadControllerDetailTests {
  private final MockMvc mockMvc;
  private final String urlTemplate = "/books";
  private final BookRepository bookRepository;

  @Autowired
  public BookReadControllerDetailTests(MockMvc mockMvc, BookRepository bookRepository) {
    this.mockMvc = mockMvc;
    this.bookRepository = bookRepository;
  }

  @Test
  @DisplayName("Should return not found when the book id does not exist")
  void shouldReturnNotFoundWhenTheBookIdDoesNotExist() throws Exception {
    this.bookRepository.deleteAll();
    this.mockMvc
      .perform(get(this.urlTemplate + "/1"))
      .andExpect(status().isNotFound())
      .andExpect(content().string(""));
  }

  @Test
  @DisplayName("Should return the book details when the book id exists")
  void shouldReturnTheBookDetailsWhenTheBookIdExists(
    @Autowired CategoryRepository categoryRepository,
    @Autowired AuthorRepository authorRepository
  ) throws Exception {
    this.bookRepository.deleteAll();

    Author authorA = authorRepository.save(
      new Author("Author A", "a", "author a description")
    );

    Author authorB = authorRepository.save(
      new Author("Author B", "b", "author b description")
    );

    Category category = categoryRepository.save(
      new Category("category")
    );

    this.bookRepository.save(new Book(
      "first book",
      "brief A",
      "summary A",
      BigDecimal.valueOf(20.00),
      100,
      "isbn A",
      LocalDate.now().plusDays(1),
      category,
      authorA
    ));

    this.bookRepository.save(new Book(
      "second book",
      "brief B",
      "summary B",
      BigDecimal.valueOf(20.01),
      101,
      "isbn B",
      LocalDate.now().plusDays(2),
      category,
      authorB
    ));

    this.mockMvc
      .perform(get(this.urlTemplate + "/1").accept("application/json"))
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.*", hasSize(7)))
      .andExpect(jsonPath("title").exists())
      .andExpect(jsonPath("title").isString())
      .andExpect(jsonPath("title").isNotEmpty())
      .andExpect(jsonPath("title").value("first book"))
      .andExpect(jsonPath("brief").exists())
      .andExpect(jsonPath("brief").isString())
      .andExpect(jsonPath("brief").isNotEmpty())
      .andExpect(jsonPath("brief").value("brief A"))
      .andExpect(jsonPath("summary").exists())
      .andExpect(jsonPath("summary").isString())
      .andExpect(jsonPath("summary").isNotEmpty())
      .andExpect(jsonPath("summary").value("summary A"))
      .andExpect(jsonPath("price").exists())
      .andExpect(jsonPath("price").isNotEmpty())
      .andExpect(jsonPath("price").value("20.0"))
      .andExpect(jsonPath("pageQuantity").exists())
      .andExpect(jsonPath("pageQuantity").isNotEmpty())
      .andExpect(jsonPath("pageQuantity").value("100"))
      .andExpect(jsonPath("isbn").exists())
      .andExpect(jsonPath("isbn").isString())
      .andExpect(jsonPath("isbn").isNotEmpty())
      .andExpect(jsonPath("isbn").value("isbn A"))
      .andExpect(jsonPath("author").exists())
      .andExpect(jsonPath("author").isNotEmpty())
      .andExpect(jsonPath("author.*", hasSize(2)))
      .andExpect(jsonPath("author.name").exists())
      .andExpect(jsonPath("author.name").isString())
      .andExpect(jsonPath("author.name").isNotEmpty())
      .andExpect(jsonPath("author.name").value("Author A"))
      .andExpect(jsonPath("author.description").exists())
      .andExpect(jsonPath("author.description").isString())
      .andExpect(jsonPath("author.description").isNotEmpty())
      .andExpect(jsonPath("author.description").value("author a description"))
    ;

    this.mockMvc
      .perform(get(this.urlTemplate + "/2").accept("application/json"))
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$").exists())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.*", hasSize(7)))
      .andExpect(jsonPath("title").exists())
      .andExpect(jsonPath("title").isString())
      .andExpect(jsonPath("title").isNotEmpty())
      .andExpect(jsonPath("title").value("second book"))
      .andExpect(jsonPath("brief").exists())
      .andExpect(jsonPath("brief").isString())
      .andExpect(jsonPath("brief").isNotEmpty())
      .andExpect(jsonPath("brief").value("brief B"))
      .andExpect(jsonPath("summary").exists())
      .andExpect(jsonPath("summary").isString())
      .andExpect(jsonPath("summary").isNotEmpty())
      .andExpect(jsonPath("summary").value("summary B"))
      .andExpect(jsonPath("price").exists())
      .andExpect(jsonPath("price").isNotEmpty())
      .andExpect(jsonPath("price").value("20.01"))
      .andExpect(jsonPath("pageQuantity").exists())
      .andExpect(jsonPath("pageQuantity").isNotEmpty())
      .andExpect(jsonPath("pageQuantity").value("101"))
      .andExpect(jsonPath("isbn").exists())
      .andExpect(jsonPath("isbn").isString())
      .andExpect(jsonPath("isbn").isNotEmpty())
      .andExpect(jsonPath("isbn").value("isbn B"))
      .andExpect(jsonPath("author").exists())
      .andExpect(jsonPath("author").isNotEmpty())
      .andExpect(jsonPath("author.*", hasSize(2)))
      .andExpect(jsonPath("author.name").exists())
      .andExpect(jsonPath("author.name").isString())
      .andExpect(jsonPath("author.name").isNotEmpty())
      .andExpect(jsonPath("author.name").value("Author B"))
      .andExpect(jsonPath("author.description").exists())
      .andExpect(jsonPath("author.description").isString())
      .andExpect(jsonPath("author.description").isNotEmpty())
      .andExpect(jsonPath("author.description").value("author b description"))
    ;
  }
}
