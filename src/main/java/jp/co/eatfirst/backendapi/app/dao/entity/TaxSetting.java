package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tax_setting")
public class TaxSetting extends BaseEntity{
    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "tax_rate_std")
    private BigDecimal taxRateStd;

    @Column(name = "tax_rate_red")
    private BigDecimal taxRateRed;

    @Column(name = "tax_rate_default")
    private Long taxRateDefault;

    @Column(name = "tax_rate_select_timing")
    private Long taxRateSelectTiming;

    @Column(name = "tax_rate_std_name")
    private String taxRateStdName;

    @Column(name = "tax_rate_red_name")
    private String taxRateRedName;

    @Column(name = "tax_rate_order_def")
    private Long taxRateOrderDef;

    @Column(name = "custom_product_name")
    private String customProductName;

}
