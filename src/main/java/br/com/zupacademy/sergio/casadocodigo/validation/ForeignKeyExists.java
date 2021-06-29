package br.com.zupacademy.sergio.casadocodigo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {ForeignKeyExistsValidator.class})
public @interface ForeignKeyExists {
  Class<?> domainClass();
  String message() default "does not exist";
  Class<?>[] groups() default  { };
  Class<? extends Payload>[] payload() default { };
}
