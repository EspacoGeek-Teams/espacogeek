package com.espacogeek.geek.types;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Class used to GraphQL Input Type, used principally to search user
 */
public class UserInput implements Serializable {
    private Integer id;
    private String username;
    private String email;
}
