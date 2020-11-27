package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_option")
@IdClass(ProductOptionPK.class)
public class ProductOption extends BaseEntity{
    @Id
    @Column(name = "option_group_id")
    private Long optionGroupId;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sort_order")
    private Integer sortOrder;

}
