package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "store_information")
public class StoreInformation  extends BaseEntity{
    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "store_no")
    private String storeNo;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "descreption")
    private String descreption;

    @Column(name = "store_logo")
    private String storeLogo;

    @Column(name = "store_image_url")
    private String storeImageUrl;

    @Column(name = "trade_details")
    private Integer tradeDetails;

    @Column(name = "store_tel")
    private String storeTel;

    @Column(name = "store_zip")
    private String storeZip;

    @Column(name = "store_address1")
    private Integer storeAddress1;

    @Column(name = "store_address2")
    private String storeAddress2;

    @Column(name = "store_address3")
    private String storeAddress3;

    @Column(name = "store_address4")
    private String storeAddress4;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "store_campaign_info")
    private String storeCampaignInfo;

}

