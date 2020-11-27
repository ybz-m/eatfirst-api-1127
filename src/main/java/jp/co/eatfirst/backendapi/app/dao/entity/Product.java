package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product extends BaseEntity{
    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_display_position")
    private String categoryDisplayPosition;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_name_en")
    private String productNameEn;

    @Column(name = "product_name_cn")
    private String productNameCn;

    @Column(name = "product_name_receipt")
    private String productNameReceipt;

    @Column(name = "tax_type")
    private Integer taxType;

    @Column(name = "receipt_printing")
    private Integer receiptPrinting;

    @Column(name = "display_flg")
    private Integer displayFlg;

    @Column(name = "comment")
    private String comment;

}
