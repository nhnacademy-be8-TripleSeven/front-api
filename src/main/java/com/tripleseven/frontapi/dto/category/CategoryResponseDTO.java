package com.tripleseven.frontapi.dto.category;

import java.util.List;

public class CategoryResponseDTO {
    private Long id;
    private String name;
    private int level;
    private List<CategoryResponseDTO> children;

    // 생성자
    public CategoryResponseDTO(Long id, String name, int level, List<CategoryResponseDTO> children) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.children = children;
    }

    // Getter, Setter 등
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public List<CategoryResponseDTO> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryResponseDTO> children) {
        this.children = children;
    }
}
