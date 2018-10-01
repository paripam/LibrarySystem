package com.paripa.by.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {

    static boolean isEmailValid(String email) {
        boolean isValid = false;
        if (email != null) {
            final String emailRegex = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            isValid = matcher.matches();
        }
        return isValid;
    }

    static boolean isPasswordValid(String password) {
        boolean isValid = false;
        if (password != null) {
            final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(password);
            isValid = matcher.matches();
        }
        return isValid;
    }

    static boolean isTextValid(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isIntegerValid(String stringInteger) {
        boolean isValid;
        try {
            Integer.parseInt(stringInteger);
            isValid = true;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    static boolean isEqualValid(String text1, String text2) {
        return text1 != null && text1.equals(text2);
    }
}