package com.espacogeek.geek.utils;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.User.UserService;

/**
 * Utility class to decode Basic Auth credentials and retrieve a UserModal object.
 */
public class DecodeBasicAuth {
    private String[] credentials;

    /**
     * Constructor to initialize the DecodeBasicAuth object with a Basic Auth string.
     * 
     * @param basicAuth the Basic Auth string to decode
     */
    public DecodeBasicAuth(String basicAuth) {
        decode(basicAuth);
    }

    private void decode(String authorization) {
        var authEncoded = authorization.substring(5).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        var authString = new String(authDecoded);
        this.credentials = authString.split(":");
    }

    /**
     * Returns the decoded credentials as an array of strings.
     * 
     * @return the credentials array
     */
    public String[] getCredentialsDecoded() {
        return this.credentials;
    }

    /**
     * get User password from basic auth provied.
     * 
     * @return an Optional containing the UserModal object if found, otherwise empty
     */
    public String getPassword() {
        return credentials[1];
    }

    /**
     * get User email from basic auth provied.
     * 
     * @return an Optional containing the UserModal object if found, otherwise empty
     */
    public String getEmail() {
        return credentials[0];
    }
}
