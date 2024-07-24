package com.espacogeek.geek.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Deprecated(forRemoval = true)
/**
 * Class used to GraphQL Input Type, used principally to search media
 */
public class MediaInput {
    private Integer id;
    private String name;
}
