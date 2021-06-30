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
public class BookDtoPostDateValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoPostDateValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept null post date")
  void shouldNotAcceptNullPostDate() {
    BookDto bookDtoWithNullPostDate = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      null,
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullPostDate).size());
  }

  @Test
  @DisplayName("Should not accept post date in the past")
  void shouldNotAcceptPostDateInThePast() {
    BookDto bookDtoWithPostDateInThePast = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().minusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithPostDateInThePast).size());
  }

  @Test
  @DisplayName("Should not accept post date of current date")
  void shouldNotAcceptPostDateOfCurrentDate() {
    BookDto bookDtoWithPostDateOfCurrentDate = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now(),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithPostDateOfCurrentDate).size());
  }

  @Test
  @DisplayName("Should accept post date in the future")
  void shouldAcceptPostDateInTheFuture() {
    BookDto bookDtoWithPostDateInTheFuture = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithPostDateInTheFuture).size());
  }
}
