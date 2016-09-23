package movieappspring.validation;

import movieappspring.validation.annotation.ValidMovieTransferObjectURL;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class for validating <code>MovieTransferObject's</code> URL fields. If user leaves URL field empty during
 * movie creation, validation completes successfully. Otherwise validator checks for URL validity.
 */
public class MovieTransferObjectValidator implements ConstraintValidator<ValidMovieTransferObjectURL, String> {

    public void initialize(ValidMovieTransferObjectURL validMovieTransferObjectURL) {
    }

    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {

        UrlValidator urlValidator = new UrlValidator();
        StringBuilder response = new StringBuilder();

        if (url != null) {
            if (url.isEmpty()) {
                return true;
            } else {
                if (url.length() < 3 || url.length() > 255) {
                    response.append("{movie.url.size}\n");
                } else if (!urlValidator.isValid(url)) {
                    response.append("{movie.url}\n");
                }
            }
        } else {
            return true;
        }

        if (response.toString().isEmpty()) {
            return true;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(response.toString())
                    .addConstraintViolation();
        }

        return false;

    }

}