package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@Entity
@Table(name = "order_info")
public class OrderInfo extends BaseEntity{
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "inside_or_takeout")
    private Integer insideOrTakeout;

    @Column(name = "receipt_time")
    private Timestamp receiptTime;

    @Column(name = "store_tel")
    private String storeTel;

    @Column(name = "personal_tel")
    private String personalTel;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "order_barcode")
    private String orderBarcode;

    @Column(name = "number_of_male")
    private Long numberOfMale;

    @Column(name = "number_of_female")
    private Long numberOfFemale;

    @Column(name = "era")
    private Integer era;

    @Column(name = "reservation_site")
    private Integer reservationSite;

    @Column(name = "use_scene")
    private Integer useScene;

    @Column(name = "time_limit_flg")
    private Integer timeLimitFlg;

    @Column(name = "time_limit")
    private String timeLimit;

    @Column(name = "end_call_flg")
    private Integer endCallFlg;

    @Column(name = "end_call")
    private String endCall;

    @Column(name = "starting_time")
    private Integer startingTime;

    @Column(name = "eat_buffet_flg")
    private Integer eatBuffetFlg;

    @Column(name = "drink_buffet_flg")
    private Integer drinkBuffetFlg;

    @Column(name = "receipt_issue_flg")
    private Integer receiptIssueFlg;

    @Column(name = "receipt_issuance_flg")
    private Integer receiptIssuanceFlg;

    @Column(name = "order_staff_id")
    private String orderStaffId;

    @Column(name = "accounting_staff_id")
    private String accountingStaffId;


}
