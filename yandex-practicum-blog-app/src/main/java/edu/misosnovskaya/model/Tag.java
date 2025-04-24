package edu.misosnovskaya.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Tag {

    private Long id;

    @NonNull
    private String name;
}
