package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

@Data
public class ProductCategoryForm {
    
    /**
     * 商品ID.
     */
	private Long productId;
    
    /**
     * カテゴリーID.
     */
	private Long categoryId;
    
    /**
     * 表示順.
     */
	private Integer sortOrder;
}
