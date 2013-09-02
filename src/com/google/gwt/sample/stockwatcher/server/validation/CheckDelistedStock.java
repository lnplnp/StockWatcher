package com.google.gwt.sample.stockwatcher.server.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
//import javax.validation.ConstraintPayload;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckDelistedStockValidator.class)
@Documented
public @interface CheckDelistedStock {
  String message() default "{com.google.constraints.delistedstock}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  // String value();

}
