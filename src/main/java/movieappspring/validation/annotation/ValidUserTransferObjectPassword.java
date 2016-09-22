package movieappspring.validation.annotation;

import movieappspring.validation.UserTransferObjectValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be placed onto <code>UserTransferObject</code> password field.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserTransferObjectValidator.class})
public @interface ValidUserTransferObjectPassword {

    String message() default "{user.invalid}";

    int min() default 0;

    int max() default 2147483647;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}