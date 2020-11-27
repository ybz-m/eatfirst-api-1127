package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "discount")
public class Discount extends BaseEntity{
    @Id
    @Column(name = "discount_id")
    private Long discountId;

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Long quantity;

}
