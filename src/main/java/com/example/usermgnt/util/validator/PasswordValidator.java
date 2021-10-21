package com.example.usermgnt.util.validator;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Okala III
 */

@Service
public class PasswordValidator implements Predicate<String> {

    @Override
    public boolean test(String pwd) {
        return isValidPassword(pwd);
    }


    /**
     * A password is considered valid if all the following constraints are satisfied:
     * It contains at least 8 characters and at most 20 characters.
     * It contains at least one digit.
     * It contains at least one upper case alphabet.
     * It contains at least one lower case alphabet.
     * It contains at least one special character which includes !@#$%&*()-+=^.
     * It doesn’t contain any white space.
     * <p>
     * Input: Str = “Okala@uba16”
     * Output: True.
     * Explanation: This password satisfies all constraints mentioned above.
     * <p>
     * Input: Str = “bashirOkala”
     * Output: False.
     * Explanation: It contains upper case and lower case alphabet but does not contain any digits, and special characters.
     * <p>
     * Input: Str = “Bashir@ portal9”
     * Output: False.
     * Explanation: It contains upper case alphabet, lower case alphabet, special characters, digits along with white space which is not valid.
     *
     * @param password
     * @return true if and only if password satisfies the above criteria else false
     */
    public boolean isValidPassword(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }
}
