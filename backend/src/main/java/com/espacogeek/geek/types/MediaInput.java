package com.espacogeek.geek.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Class used to GraphQL Input Type, used principally to search media
 */
public class MediaInput {
    private Integer id;
    private String name;
}
