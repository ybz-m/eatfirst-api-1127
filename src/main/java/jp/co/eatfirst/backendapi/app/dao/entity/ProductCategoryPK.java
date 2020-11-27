package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductCategoryPK implements Serializable {
    private Long categoryId;
    private Long productId;

}
