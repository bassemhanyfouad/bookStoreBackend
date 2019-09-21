package com.check24.codingchallenge.bookstore.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

/**
 * Created by Bassem
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {
    @Mock
    ValidEmail validEmail;

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    EmailValidator emailValidator;

    /**
     * This test validates the anomalies of the email
     */
    @Test
    public void testEmailAnomalies() {
        Assert.assertEquals(emailValidator.isValid("aa@test.com", constraintValidatorContext), false);
        Assert.assertEquals(emailValidator.isValid("aa@testcom", constraintValidatorContext), false);

    }
}
