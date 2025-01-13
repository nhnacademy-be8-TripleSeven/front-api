package com.tripleseven.frontapi.dto.book;

import com.tripleseven.frontapi.dto.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDetailResponse {

    private Long id;
    private String title;
    private int regularPrice;
    private int salePrice;
    private String coverUrl;
    private boolean wrappable;
    private List<CategoryDTO> category;

}
