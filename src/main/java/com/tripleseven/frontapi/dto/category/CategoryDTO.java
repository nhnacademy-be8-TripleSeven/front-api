package com.tripleseven.frontapi.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private int level;
    private CategoryDTO parent;


    public CategoryDTO(Long id, String name, int level, CategoryDTO parent) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.parent = parent;
    }

    public CategoryDTO(String name, int level, CategoryDTO parent) {
        this.name = name;
        this.level = level;
        this.parent = parent;
    }



    public CategoryDTO(String name, int level) {
        this.name = name.trim();
        this.level = level;
    }

    public CategoryDTO(String name) {
        this.name = name;
    }
}
