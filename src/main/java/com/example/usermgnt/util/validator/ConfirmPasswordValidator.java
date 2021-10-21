package com.example.usermgnt.util.validator;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;


@Service
public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    private String passwordField;
    private String confirmPasswordField;
    private String message;

    @Override
    public void initialize(ConfirmPassword data) {
        passwordField = data.passwordField();
        confirmPasswordField = data.confirmPasswordField();
        message = data.message();

        System.out.println("Confirm password field is called");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cxt) {
        if (message == null || "".equals(message) || "{ConfirmPassword.message}".equals(message)) {
            message = "Invalid Password and/or Password and Confirm Password Do Not Match!";
            cxt.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        String passwordFieldValue = Objects.toString(beanWrapper.getPropertyValue(passwordField), "");
        String confirmPasswordFieldValue = Objects.toString(beanWrapper.getPropertyValue(confirmPasswordField), "");

        return confirmPasswordFieldValue.equals(passwordFieldValue);
    }
}
