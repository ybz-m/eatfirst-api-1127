package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "method_of_payment")
public class MethodOfPayment extends BaseEntity{
    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_name")
    private String paymentName;

    @Column(name = "payment_type")
    private Long paymentType;

    @Column(name = "drawer_using")
    private Long drawerUsing;

    @Column(name = "display_flg")
    private Long displayFlg;

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "deleteability_flg")
    private Long deleteabilityFlg;

}
