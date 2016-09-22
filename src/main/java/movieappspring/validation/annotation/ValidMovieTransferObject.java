package movieappspring.validation.annotation;

import movieappspring.validation.MovieTransferObjectValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dsharko on 9/19/2016.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MovieTransferObjectValidator.class})
public @interface ValidMovieTransferObject {

    String message() default "{movie.poster.pattern}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}