package com.check24.codingchallenge.bookstore.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;

/**
 * This validator performs additional validations to the provided email. It can't be used alone, it is used along with the @Email
 * Created by Bassem
 */
@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    /**
     * This method makes additional validations in addition the @Email annotation.
     *
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email !=null && !email.contains(".")) {
            return false;
        }
        if (email !=null && email.toLowerCase().contains("test.com")) {
            return false;
        }
        return true;
    }
}
