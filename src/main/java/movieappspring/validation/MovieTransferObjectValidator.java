package movieappspring.validation;

import movieappspring.entities.dto.MovieTransferObject;
import movieappspring.validation.annotation.ValidMovieTransferObject;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by dsharko on 9/19/2016.
 */
public class MovieTransferObjectValidator implements ConstraintValidator<ValidMovieTransferObject, MovieTransferObject> {

    public void initialize(ValidMovieTransferObject validMovieTransferObject) {
    }

    public boolean isValid(MovieTransferObject movieTransferObject, ConstraintValidatorContext constraintValidatorContext) {

        UrlValidator urlValidator = new UrlValidator();

        return urlValidator.isValid(movieTransferObject.getPosterURL());
    }
}