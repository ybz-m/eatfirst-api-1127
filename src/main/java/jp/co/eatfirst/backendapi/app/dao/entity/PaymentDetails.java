package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "payment_details")
public class PaymentDetails extends BaseEntity{
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "order_status")
    private Integer orderStatus;

    @Column(name = "accounting_subtotal")
    private Long accountingSubtotal;

    @Column(name = "service_amount")
    private Long serviceAmount;

    @Column(name = "night_amount")
    private Long nightAmount;

    @Column(name = "total_discount")
    private Long totalDiscount;

    @Column(name = "demand_payment")
    private Long demandPayment;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "payment_memo")
    private String paymentMemo;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "changing")
    private Long changing;

}
