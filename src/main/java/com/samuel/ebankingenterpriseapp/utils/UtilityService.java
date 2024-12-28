package com.samuel.ebankingenterpriseapp.utils;

import java.util.regex.Pattern;

public class UtilityService {

    // Email validation method
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
