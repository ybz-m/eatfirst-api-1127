package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_discount_premium")
@IdClass(OrderDiscountPremiumPK.class)
public class OrderDiscountPremium extends BaseEntity{
    @Id
    @Column(name = "discount_premium_id")
    private Long discountPremiumId;

    @Id
    @Column(name = "order_id")
    private Long orderId;
}
