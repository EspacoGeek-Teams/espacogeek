package com.espacogeek.geek.utils;

import java.util.regex.Pattern;

import org.springframework.security.core.Authentication;

public abstract class Utils {
    private static final String REG_EXPN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!*@#$%^&+=])(?=\\S+$).{8,70}$";

    public static Integer getUserID(Authentication authentication) {
        return Integer.valueOf(
                authentication.getAuthorities().stream().filter(
                        (authority) -> authority.getAuthority()
                                .startsWith("ID_"))
                        .toList()
                        .getFirst()
                        .getAuthority()
                        .replace("ID_", ""));
    }

    /**
     * Validate the password.
     * @return <code>true</code> if the given password flow all rules and <code>false</code> if password doesn't flow any rule.
     */
    public static boolean isValidPassword (String password) {
        var pattern = Pattern.compile(REG_EXPN_PASSWORD, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
