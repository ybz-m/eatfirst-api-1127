package jp.co.eatfirst.backendapi.app.dao.entity.dto;

import jp.co.eatfirst.backendapi.app.dao.entity.OptionGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@AllArgsConstructor
public class ProductOptionDto {

    private Long productId;
    private Long optionGroupId;
    private String name;
    private String nameEn;
    private String nameCn;
}
