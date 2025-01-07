package com.tripleseven.frontapi.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private int level;

    public CategoryDTO(Long id, String name, int level) {
        this.id = id;
        this.name = name.trim();
        this.level = level;
    }

    public CategoryDTO(String name, int level) {
        this.name = name.trim();
        this.level = level;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }
}
