package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category extends BaseEntity{
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "sum_type")
    private Integer sumType;

    @Column(name = "sum_amount")
    private Long sumAmount;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_name_en")
    private String categoryNameEn;

    @Column(name = "category_name_cn")
    private String categoryNameCn;

    @Column(name = "category_display")
    private int categoryDisplay;

    @Column(name = "display_order")
    private int displayOrder;

}
