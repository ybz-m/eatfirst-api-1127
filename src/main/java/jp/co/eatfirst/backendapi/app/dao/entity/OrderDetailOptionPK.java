package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailOptionPK  implements Serializable {
    private Long orderDetailId;

    private Long optionDetailId;
}
