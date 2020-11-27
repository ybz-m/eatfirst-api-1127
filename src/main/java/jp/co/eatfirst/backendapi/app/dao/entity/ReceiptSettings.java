package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "receipt_settings")
public class ReceiptSettings extends BaseEntity{
    @Column(name = "store_logo")
    private String storeLogo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "address_upper")
    private String addressUpper;

    @Column(name = "address_bottom")
    private String addressBottom;

    @Column(name = "store_tel")
    private Long storeTel;

    @Column(name = "people_display")
    private Long peopleDisplay;

    @Column(name = "slip_name_display")
    private Long slipNameDisplay;

    @Column(name = "comment")
    private String comment;

    @Column(name = "tax_display")
    private Long taxDisplay;

    @Column(name = "tax_stamp_display")
    private Long taxStampDisplay;

    @Column(name = "receipt_proviso")
    private String receiptProviso;

    @Id
    @Column(name = "store_id")
    private String storeId;

}
