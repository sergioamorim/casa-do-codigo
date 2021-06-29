package br.com.zupacademy.sergio.casadocodigo.validation.book;

import br.com.zupacademy.sergio.casadocodigo.model.dto.BookDto;
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
public class BookDtoSummaryValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoSummaryValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
  }

  @Test
  @DisplayName("Should accept a summary if provided")
  void shouldAcceptASummaryIfProvided() {
    BookDto bookDtoWithSummary = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithSummary).size());
  }

  @Test
  @DisplayName("Should accept an empty summary")
  void shouldAcceptAnEmptySummary() {
    BookDto bookDtoWithEmptySummary = new BookDto(
      "title",
      "brief",
      "",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithEmptySummary).size());
  }

  @Test
  @DisplayName("Should accept a blank summary")
  void shouldAcceptABlankSummary() {
    BookDto bookDtoWithBlankSummary = new BookDto(
      "title",
      "brief",
      " ",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithBlankSummary).size());
  }

  @Test
  @DisplayName("Should accept a huge summary")
  void shouldAcceptAHugeSummary() {
    BookDto bookDtoWithHugeSummary = new BookDto(
      "title",
      "brief",
      "s".repeat(5001),
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithHugeSummary).size());
  }

  @Test
  @DisplayName("Should accept a null summary")
  void shouldAcceptANullSummary() {
    BookDto bookDtoWithNullSummary = new BookDto(
      "title",
      "brief",
      null,
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithNullSummary).size());
  }
}
