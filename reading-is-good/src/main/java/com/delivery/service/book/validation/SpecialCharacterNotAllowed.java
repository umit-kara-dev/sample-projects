package com.delivery.service.book.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SpecialCharacterValidator.class)
@Target({ ElementType.METHOD, ElementType.TYPE_PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
		ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.TYPE_USE, ElementType.LOCAL_VARIABLE,
		ElementType.PACKAGE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialCharacterNotAllowed {
	public String message() default "Illegal pattern.";

	public int length() default 200;
	
	public ValidationPattern regex();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
