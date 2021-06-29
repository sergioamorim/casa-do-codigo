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
public class BookDtoTitleValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoTitleValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept an empty title")
  void shouldNotAcceptAnEmptyTitle() {
    BookDto bookDtoWithEmptyTitle = new BookDto(
      "",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithEmptyTitle).size());
  }

  @Test
  @DisplayName("Should not accept a null title")
  void shouldNotAcceptANullTitle() {
    BookDto bookDtoWithNullTitle = new BookDto(
      null,
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullTitle).size());
  }

  @Test
  @DisplayName("Should not accept a blank title")
  void shouldNotAcceptABlankTitle() {
    BookDto bookDtoWithBlankTitle = new BookDto(
      " ",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithBlankTitle).size());
  }

  @Test
  @DisplayName("Should accept a regular title")
  void shouldAcceptARegularTitle() {
    BookDto bookDtoWithRegularTitle = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithRegularTitle).size());
  }
}
