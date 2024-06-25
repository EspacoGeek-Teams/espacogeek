package com.espacogeek.geek.types;

/**
 * Record class used in POST REST to create a new user
 */
@Deprecated
public record NewUser(String username, String email, String password) {
}
