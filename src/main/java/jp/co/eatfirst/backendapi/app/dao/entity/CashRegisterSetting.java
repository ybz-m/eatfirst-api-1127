package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "cash_register_setting")
public class CashRegisterSetting extends BaseEntity{
    @Id
    @Column(name = "store_id")
    private String storeId;

    @Column(name = "tax_type")
    private Long taxType;

    @Column(name = "fraction_discount")
    private Long fractionDiscount;

    @Column(name = "order_of_display")
    private Long orderOfDisplay;

    @Column(name = "notification_flg")
    private Long notificationFlg;

    @Column(name = "business_day_change_time")
    private Long businessDayChangeTime;

    @Column(name = "kitchen_printer")
    private String kitchenPrinter;

    @Column(name = "slip_print")
    private String slipPrint;

    @Column(name = "deshap_using")
    private String deshapUsing;

    @Column(name = "oes")
    private String oes;

    @Column(name = "timer")
    private Long timer;

}
