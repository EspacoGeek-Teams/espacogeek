package com.espacogeek.geek.types;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUser implements Serializable {
    private String username;
    private String email;
    private String password;
}
