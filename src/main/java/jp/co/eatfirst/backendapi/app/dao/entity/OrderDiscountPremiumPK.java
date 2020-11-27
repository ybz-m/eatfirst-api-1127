package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDiscountPremiumPK  implements Serializable {
    private Long discountPremiumId;

    private Long orderId;
}
