package com.espacogeek.geek.utils;

import java.util.regex.Pattern;

/**
 * This is a util class to validate password.
 */
public class PasswordValidator {
    private String password;
    private String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,70}$";

    /**
     * Constructor of the class {@link PasswordValidator}.
     * @param password to validate.
     */
    public PasswordValidator(String password) {
        this.password = password;
    }

    /**
     * Validate the password.
     * @return <code>true</code> if the given password flow all rules and <code>false</code> if password doesn't flow any rule. 
     */
    public boolean check () {
        var pattern = Pattern.compile(this.regExpn, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(this.password);

        return matcher.matches();
    }
}
