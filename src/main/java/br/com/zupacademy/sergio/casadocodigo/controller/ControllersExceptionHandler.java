package br.com.zupacademy.sergio.casadocodigo.controller;

import br.com.zupacademy.sergio.casadocodigo.dto.ValidationErrorsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllersExceptionHandler {

  private final MessageSource messageSource;

  @Autowired
  public ControllersExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ValidationErrorsDto handleMethodArgumentNotValidException(
    MethodArgumentNotValidException methodArgumentNotValidException
  ) {
    return new ValidationErrorsDto(
      methodArgumentNotValidException.getGlobalErrors(),
      methodArgumentNotValidException.getFieldErrors(),
      messageSource
    );
  }

}
