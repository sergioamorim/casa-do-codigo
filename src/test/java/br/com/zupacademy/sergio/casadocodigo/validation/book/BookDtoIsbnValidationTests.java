package br.com.zupacademy.sergio.casadocodigo.validation.book;

import br.com.zupacademy.sergio.casadocodigo.model.dto.book.BookDto;
import br.com.zupacademy.sergio.casadocodigo.repository.AuthorRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.BookRepository;
import br.com.zupacademy.sergio.casadocodigo.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@SpringBootTest
@DirtiesContext(classMode = BEFORE_CLASS)
public class BookDtoIsbnValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoIsbnValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept null ISBN")
  void shouldNotAcceptNullIsbn() {
    BookDto bookDtoWithNullIsbn = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      null,
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullIsbn).size());
  }

  @Test
  @DisplayName("Should not accept empty ISBN")
  void shouldNotAcceptEmptyIsbn() {
    BookDto bookDtoWithEmptyIsbn = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithEmptyIsbn).size());
  }

  @Test
  @DisplayName("Should not accept blank ISBN")
  void shouldNotAcceptBlankIsbn() {
    BookDto bookDtoWithBlankIsbn = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      " ",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithBlankIsbn).size());
  }

  @Test
  @DisplayName("Should accept regular ISBN")
  void shouldAcceptRegularIsbn() {
    BookDto bookDtoWithRegularIsbn = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithRegularIsbn).size());
  }

  @Test
  @DisplayName("Should not accept duplicate ISBN")
  void shouldNotAcceptDuplicateIsbn(
    @Autowired CategoryRepository categoryRepository,
    @Autowired AuthorRepository authorRepository,
    @Autowired BookRepository bookRepository
  ) {
    String isbn = "unique isbn of test Should not accept duplicate ISBN";

    BookDto bookDto = new BookDto(
      "first unique title of test Should not accept duplicate ISBN",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      isbn,
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );

    BookDto bookDtoWithDuplicateTitle = new BookDto(
      "second unique title of test Should not accept duplicate ISBN",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      isbn,
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );

    Assertions.assertEquals(0, this.validator.validate(bookDto).size());

    bookRepository.save(bookDto.asBook(categoryRepository, authorRepository));

    Assertions.assertEquals(1, this.validator.validate(bookDtoWithDuplicateTitle).size());
  }
}
