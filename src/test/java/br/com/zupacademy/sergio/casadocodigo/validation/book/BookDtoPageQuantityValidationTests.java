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
public class BookDtoPageQuantityValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoPageQuantityValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept page quantity below one hundred")
  void shouldNotAcceptPageQuantityBelowOneHundred() {
    BookDto bookDtoWithPageQuantityOfNinetyNine = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      99,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithPageQuantityOfNinetyNine).size());
  }

  @Test
  @DisplayName("Should not accept page quantity of zero")
  void shouldNotAcceptPageQuantityOfZero() {
    BookDto bookDtoWithPageQuantityOfZero = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      0,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithPageQuantityOfZero).size());
  }

  @Test
  @DisplayName("Should not accept negative page quantity")
  void shouldNotAcceptNegativePageQuantity() {
    BookDto bookDtoWithNegativePageQuantity = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      -101,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNegativePageQuantity).size());
  }

  @Test
  @DisplayName("Should not accept null page quantity")
  void shouldNotAcceptNullPageQuantity() {
    BookDto bookDtoWithNegativePageQuantity = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      null,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNegativePageQuantity).size());
  }

  @Test
  @DisplayName("Should accept page quantity of one hundred")
  void shouldAcceptPageQuantityOfOneHundred() {
    BookDto bookDtoWithPageQuantityOfOneHundred = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithPageQuantityOfOneHundred).size());
  }

  @Test
  @DisplayName("Should accept huge page number")
  void shouldAcceptHugePageNumber() {
    BookDto bookDtoWithHugePageQuantity = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      50000,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithHugePageQuantity).size());
  }
}
