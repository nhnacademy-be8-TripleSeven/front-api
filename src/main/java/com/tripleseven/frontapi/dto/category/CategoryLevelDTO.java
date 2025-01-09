package com.tripleseven.frontapi.dto.category;

import java.util.List;
import lombok.Getter;

@Getter
public class CategoryLevelDTO {

    private List<CategoryDTO> level1;
    private List<CategoryDTO> level2;
    private List<CategoryDTO> level3;
    private List<CategoryDTO> level4;
    private List<CategoryDTO> level5;



}