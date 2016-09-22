package movieappspring.validation.annotation;

import movieappspring.validation.MovieTransferObjectValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be placed onto <code>MovieTransferObject</code> URL fields.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MovieTransferObjectValidator.class})
public @interface ValidMovieTransferObjectURL {

    String message() default "{movie.url}";

    int min() default 0;

    int max() default 2147483647;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}