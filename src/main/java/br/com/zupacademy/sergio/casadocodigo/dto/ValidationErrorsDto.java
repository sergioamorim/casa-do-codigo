package br.com.zupacademy.sergio.casadocodigo.dto;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsDto {
  private List<String> globalErrors;
  private List<FieldErrorDto> fieldErrors;

  public ValidationErrorsDto(
    List<ObjectError> objectErrors,
    List<FieldError> fieldErrors,
    MessageSource messageSource
  ) {
    this.globalErrors = new ArrayList<>();
    this.fieldErrors = new ArrayList<>();
    this.build(objectErrors, fieldErrors, messageSource);
  }

  public List<String> getGlobalErrors() {
    return this.globalErrors;
  }

  public List<FieldErrorDto> getFieldErrors() {
    return this.fieldErrors;
  }

  private void build(
    List<ObjectError> objectErrors,
    List<FieldError> fieldErrors,
    MessageSource messageSource
  ) {
    objectErrors.forEach(
      objectError -> addGlobalError(objectError, messageSource)
    );

    fieldErrors.forEach(
      fieldError -> addFieldError(fieldError, messageSource)
    );
  }

  private void addGlobalError(
    ObjectError objectError, MessageSource messageSource
  ) {
    this.globalErrors.add(getErrorMessage(objectError, messageSource));
  }

  private void addFieldError(
    FieldError fieldError, MessageSource messageSource
  ) {
    this.fieldErrors.add(
      new FieldErrorDto(
        fieldError.getField(), getErrorMessage(fieldError, messageSource)
      )
    );
  }

  private static String getErrorMessage(
    ObjectError objectError, MessageSource messageSource
  ) {
    return messageSource.getMessage(
      objectError, LocaleContextHolder.getLocale()
    );
  }
}
