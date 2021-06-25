package br.com.zupacademy.sergio.casadocodigo.validation;

public class FieldErrorDto {
  private final String field;
  private final String message;

  public FieldErrorDto(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return this.field;
  }

  public String getMessage() {
    return this.message;
  }
}
