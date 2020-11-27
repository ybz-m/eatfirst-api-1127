package jp.co.eatfirst.backendapi.app.dto.dto;

import lombok.Data;

@Data
public class ProductCategoryDto {
    private Long productId;
    private Long categoryId;
    private Integer sortOrder;
}
