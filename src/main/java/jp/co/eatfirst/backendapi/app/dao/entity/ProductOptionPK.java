package jp.co.eatfirst.backendapi.app.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
public class ProductOptionPK implements Serializable {
    private Long optionGroupId;
    private Long productId;

}
