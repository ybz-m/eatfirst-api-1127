package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "discount_premium_setting")
public class DiscountPremiumSetting extends BaseEntity{
    @Id
    @Column(name = "discount_premium_id")
    private Long discountPremiumId;

    @Column(name = "discount_premium_name")
    private String discountPremiumName;

    @Column(name = "discount_premium_type")
    private Long discountPremiumType;

    @Column(name = "discount_premium_amount")
    private Long discountPremiumAmount;

    @Column(name = "ratio")
    private BigDecimal ratio;

    @Column(name = "display_flg")
    private Long displayFlg;

    @Id
    @Column(name = "store_id")
    private Long storeId;

}
