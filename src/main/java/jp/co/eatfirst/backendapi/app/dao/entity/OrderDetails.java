package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails extends BaseEntity{
    @Id
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "order_turn")
    private Long orderTurn;

    @Column(name = "adaptive_tax_rate")
    private BigDecimal adaptiveTaxRate;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "price")
    private Long price;

}
