package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "receipt_details")
public class ReceiptDetails extends BaseEntity{
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "table_id")
    private Long tableId;

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "receipt_price")
    private Long receiptPrice;

    @Column(name = "number_of_receipts")
    private Long numberOfReceipts;

    @Column(name = "receipt_proviso")
    private String receiptProviso;

    @Column(name = "receipt_issuance_type")
    private Long receiptIssuanceType;

}
