package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_detail_option")
@IdClass(OrderDetailOptionPK.class)
public class OrderDetailOption extends BaseEntity{
    @Id
    @Column(name = "option_detail_id")
    private Long optionDetailId;

    @Id
    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "sort_order")
    private Integer sortOrder;

}
