package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "tax_rate_selection_setting")
public class TaxRateSelectionSetting extends BaseEntity{
    @Id
    @Column(name = "tax_rate_select_timing")
    private Long taxRateSelectTiming;

    @Column(name = "tax_rate_std_name")
    private String taxRateStdName;

    @Column(name = "tax_rate_red_name")
    private String taxRateRedName;

    @Column(name = "tax_rate_order_def")
    private Long taxRateOrderDef;

}
