package br.com.zupacademy.sergio.casadocodigo.validation.book;

import br.com.zupacademy.sergio.casadocodigo.model.dto.book.BookDto;
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
public class BookDtoBriefValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoBriefValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept an empty brief")
  void shouldNotAcceptAnEmptyBrief() {
    BookDto bookDtoWithEmptyBrief = new BookDto(
      "title",
      "",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithEmptyBrief).size());
  }

  @Test
  @DisplayName("Should not accept a null brief")
  void shouldNotAcceptANullBrief() {
    BookDto bookDtoWithNullBrief = new BookDto(
      "title",
      null,
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullBrief).size());
  }

  @Test
  @DisplayName("Should not accept a blank brief")
  void shouldNotAcceptABlankBrief() {
    BookDto bookDtoWithBlankBrief = new BookDto(
      "title",
      " ",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithBlankBrief).size());
  }

  @Test
  @DisplayName("Should not accept a brief with 501 characters")
  void shouldNotAcceptABriefWith501Characters() {
    BookDto bookDtoWithHugeBrief = new BookDto(
      "title",
      "b".repeat(501),
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithHugeBrief).size());
  }

  @Test
  @DisplayName("Should accept a brief with 500 characters")
  void shouldAcceptABriefWith500Characters() {
    BookDto bookDtoWithHugeBrief = new BookDto(
      "title",
      "b".repeat(500),
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithHugeBrief).size());
  }

  @Test
  @DisplayName("Should accept a regular brief")
  void shouldAcceptARegularBrief() {
    BookDto bookDtoWithRegularBrief = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithRegularBrief).size());
  }
}
