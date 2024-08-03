package com.espacogeek.geek.utils;

import org.springframework.security.core.Authentication;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.impl.UserDetailsServiceImpl;

/**
 * Get User Id in UserDetails.
 */
public class GetIdInUserDetailsAuthority {
    private Authentication authentication;

    /**
     * Get {@link UserModel#getId()} setted in {@link UserDetailsServiceImpl#loadUserByUsername(String)}.
     * @param authentication
     */
    public GetIdInUserDetailsAuthority(Authentication authentication) {
        this.authentication = authentication;
    }

    public Integer getUserID() {
        return Integer.valueOf(
            authentication.getAuthorities().stream().filter(
                    (authority) -> 
                        authority.getAuthority()
                        .startsWith("ID_"))
                        .toList()
                        .getFirst()
                        .getAuthority()
                        .replace("ID_", "")
            );
    }
}
