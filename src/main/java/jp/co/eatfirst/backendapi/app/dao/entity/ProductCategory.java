package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_category")
@IdClass(ProductCategoryPK.class)
public class ProductCategory extends BaseEntity{
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sort_order")
    private Integer sortOrder;

}
