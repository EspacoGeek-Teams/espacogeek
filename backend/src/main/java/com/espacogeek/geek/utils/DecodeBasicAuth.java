package com.espacogeek.geek.utils;

import java.util.Base64;
<<<<<<< HEAD
=======
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)

/**
 * Utility class to decode Basic Auth credentials and retrieve a UserModal object.
 */
public class DecodeBasicAuth {
    private String[] credentials;

    /**
     * Constructor to initialize the DecodeBasicAuth Util with a Basic Auth string.
     * <p>
     * Exemple:
     * <ul>
     * <li> username: vitor@gmail.com
     * <li> password: vitor1234
     * </ul>
     * BasicAuth with Base64 Encode will be something like <code> Basic dml0b3JAZ21haWwuY29tOnZpdG9yMTIzNA== </code>
     * 
     * @param basicAuth the Basic Auth string to decode
     */
    public DecodeBasicAuth(String basicAuth) {
        decode(basicAuth);
    }

    /**
     * Decode Basic Auth Encoded with Base 64 and split in username and password
     */
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
     * get User password from basic auth provide.
     * 
     * @return an Optional containing the UserModal object if found, otherwise empty
     */
    public String getPassword() {
        return credentials[1];
    }

    /**
     * get User email from basic auth provide.
     * 
     * @return an Optional containing the UserModal object if found, otherwise empty
     */
    public String getEmail() {
        return credentials[0];
    }
}
