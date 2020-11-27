package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "option")
public class Option extends BaseEntity{
    @Id
    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "option_group_id")
    private Long optionGroupId;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "type1")
    private String type1;

    @Column(name = "type2")
    private String type2;

    @Column(name = "type1_en")
    private String type1En;

    @Column(name = "type2_en")
    private String type2En;

    @Column(name = "type1_cn")
    private String type1Cn;

    @Column(name = "type2_cn")
    private String type2Cn;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Column(name = "price")
    private Long price;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "barcode_no")
    private String barcodeNo;

}
