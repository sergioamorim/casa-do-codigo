package br.com.zupacademy.sergio.casadocodigo.validation.book;

import br.com.zupacademy.sergio.casadocodigo.model.dto.BookDto;
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
public class BookDtoAuthorIdValidationTests {
  private final Validator validator;

  @Autowired
  public BookDtoAuthorIdValidationTests(Validator validator) {
    this.validator = validator;
  }

  @BeforeAll
  static void setUp(@Autowired DataSource dataSource) throws SQLException {
    ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("book_dto_validation_tests.sql"));
  }

  @Test
  @DisplayName("Should not accept null author id")
  void shouldNotAcceptNullAuthorId() {
    BookDto bookDtoWithNullAuthorId = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      null
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNullAuthorId).size());
  }

  @Test
  @DisplayName("Should not accept non existent author id X")
  void shouldNotAcceptNonExistentAuthorIdX() {
    BookDto bookDtoWithNonExistentAuthorIdX = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 3
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNonExistentAuthorIdX).size());
  }

  @Test
  @DisplayName("Should not accept non existent author id Y")
  void shouldNotAcceptNonExistentAuthorIdY() {
    BookDto bookDtoWithNonExistentAuthorIdY = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 4
    );
    Assertions.assertEquals(1, this.validator.validate(bookDtoWithNonExistentAuthorIdY).size());
  }

  @Test
  @DisplayName("Should accept existent author id A")
  void shouldAcceptExistentAuthorIdA() {
    BookDto bookDtoWithExistentAuthorIdA = new BookDto(
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
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithExistentAuthorIdA).size());
  }

  @Test
  @DisplayName("Should accept existent author id B")
  void shouldAcceptExistentAuthorIdB() {
    BookDto bookDtoWithExistentAuthorIdB = new BookDto(
      "title",
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "isbn",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 2
    );
    Assertions.assertEquals(0, this.validator.validate(bookDtoWithExistentAuthorIdB).size());
  }

  @Test
  @DisplayName("Should not accept duplicate title")
  void shouldNotAcceptDuplicateTitle(
    @Autowired CategoryRepository categoryRepository,
    @Autowired AuthorRepository authorRepository,
    @Autowired BookRepository bookRepository
  ) {
    String title = "unique title of test Should not accept duplicate title";

    BookDto bookDto = new BookDto(
      title,
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "first unique isbn of test Should not accept duplicate title",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );

    BookDto bookDtoWithDuplicateTitle = new BookDto(
      title,
      "brief",
      "summary",
      BigDecimal.valueOf(20.0),
      100,
      "second isbn title of test Should not accept duplicate title",
      LocalDate.now().plusDays(1),
      (long) 1,
      (long) 1
    );

    Assertions.assertEquals(0, this.validator.validate(bookDto).size());

    bookRepository.save(bookDto.asBook(categoryRepository, authorRepository));

    Assertions.assertEquals(1, this.validator.validate(bookDtoWithDuplicateTitle).size());
  }
}
