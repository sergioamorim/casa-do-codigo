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
public class BookDtoPriceValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoPriceValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept a price below 20")
  void shouldNotAcceptAPriceBelow20() {
    BookDto bookDtoWithCheapPrice = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(19.99),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithCheapPrice).size());
  }

  @Test
  @DisplayName("Should not accept price zero")
  void shouldNotAcceptPriceZero() {
    BookDto bookDtoWithPriceZero = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithPriceZero).size());
  }

  @Test
  @DisplayName("Should not accept negative price")
  void shouldNotAcceptNegativePrice() {
    BookDto bookDtoWithNegativePrice = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(-21),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNegativePrice).size());
  }

  @Test
  @DisplayName("Should accept price equals twenty")
  void shouldAcceptPriceEqualsTwenty() {
    BookDto bookDtoWithPriceEqualsTwenty = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithPriceEqualsTwenty).size());
  }

  @Test
  @DisplayName("Should accept huge price")
  void shouldAcceptHugePrice() {
    BookDto bookDtoWithHugePrice = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(9999999.999),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithHugePrice).size());
  }

  @Test
  @DisplayName("Should not accept null price")
  void shouldNotAcceptNullPrice() {
    BookDto bookDtoWithNullPrice = new BookDto(
      "title",
      "brief",
      "summary",
      null,
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullPrice).size());
  }
}
