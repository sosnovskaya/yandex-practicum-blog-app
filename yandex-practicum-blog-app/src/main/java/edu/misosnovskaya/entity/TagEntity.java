package edu.misosnovskaya.entity;

import lombok.Data;

@Data
public class TagEntity {

    private Long id;

    private String name;

    public TagEntity(String name) {
        this.name = name;
    }

    public TagEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
