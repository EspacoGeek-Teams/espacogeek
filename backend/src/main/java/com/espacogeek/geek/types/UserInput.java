package com.espacogeek.geek.types;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated(forRemoval = true)
/**
 * Class used to GraphQL Input Type, used principally to search user
 * @deprecated
 */
public class UserInput implements Serializable {
    private Integer id;
    private String username;
    private String email;
}
