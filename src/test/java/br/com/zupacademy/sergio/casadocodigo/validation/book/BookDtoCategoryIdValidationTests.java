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
public class BookDtoCategoryIdValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoCategoryIdValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept null category id")
  void shouldNotAcceptNullCategoryId() {
    BookDto bookDtoWithNullCategoryId = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      null,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullCategoryId).size());
  }

  @Test
  @DisplayName("Should not accept non existent category id X")
  void shouldNotAcceptNonExistentCategoryIdX() {
    BookDto bookDtoWithNonExistentCategoryIdX = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 3,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNonExistentCategoryIdX).size());
  }

  @Test
  @DisplayName("Should not accept non existent category id Y")
  void shouldNotAcceptNonExistentCategoryIdY() {
    BookDto bookDtoWithNonExistentCategoryIdY = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 4,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNonExistentCategoryIdY).size());
  }

  @Test
  @DisplayName("Should accept existent category id A")
  void shouldAcceptExistentCategoryIdA() {
    BookDto bookDtoWithExistentCategoryIdA = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithExistentCategoryIdA).size());
  }

  @Test
  @DisplayName("Should accept existent category id B")
  void shouldAcceptExistentCategoryIdB() {
    BookDto bookDtoWithExistentCategoryIdB = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 2,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithExistentCategoryIdB).size());
  }
}
