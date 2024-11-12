package com.espacogeek.geek.types;

import java.util.List;

import com.espacogeek.geek.models.MediaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaPage {
    private List<MediaModel> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
}
