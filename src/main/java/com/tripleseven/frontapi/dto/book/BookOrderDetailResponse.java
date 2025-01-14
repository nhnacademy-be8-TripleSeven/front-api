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

    public BookOrderDetailResponse(Long id, String title, int regularPrice, int salePrice,
                                   String coverUrl, boolean wrappable) {
        this.id = id;
        this.title = title;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.coverUrl = coverUrl;
        this.wrappable = wrappable;
    }

    public void addCategoryList(List<CategoryDTO> categoryList) {
        this.category.addAll(categoryList);
    }
}
