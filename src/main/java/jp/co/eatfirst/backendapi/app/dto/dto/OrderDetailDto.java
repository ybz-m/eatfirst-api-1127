package jp.co.eatfirst.backendapi.app.dto.dto;

import jp.co.eatfirst.backendapi.app.dto.form.OrderDetailOptionForm;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailDto {
    String productId;
    Integer ordreNum;
    Integer priceSumType;
    List<OrderDetailOptionDto> options;
}
