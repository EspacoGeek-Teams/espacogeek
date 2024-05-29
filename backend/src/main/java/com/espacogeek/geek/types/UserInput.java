package com.espacogeek.geek.types;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput implements Serializable {
    private Integer id;
    private String username;
    private String email;
}
