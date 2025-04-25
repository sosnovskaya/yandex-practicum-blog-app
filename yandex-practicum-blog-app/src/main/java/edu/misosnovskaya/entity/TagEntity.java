package edu.misosnovskaya.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {

    private Long id;

    private String name;

    public TagEntity(String name) {
        this.name = name;
    }
}
