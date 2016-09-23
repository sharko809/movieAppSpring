package movieappspring.validation;

import movieappspring.validation.annotation.ValidUserTransferObjectPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Class for validating <code>UserTransferObject's</code> password field. If user leaves password field empty during
 * account info update, password will stay the same and validation completes successfully. Otherwise validator checks
 * for password length.
 */
public class UserTransferObjectValidator implements ConstraintValidator<ValidUserTransferObjectPassword, String> {

    @Override
    public void initialize(ValidUserTransferObjectPassword validUserTransferObjectPassword) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return s == null || s.isEmpty() || !(s.length() < 3 || s.length() > 15);

    }

}