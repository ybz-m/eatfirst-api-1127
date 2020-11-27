package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;
import java.util.List;

@Data
public class OrderDetailForm {
    
    /**
     * 商品ID.
     */
	String productId;
    
    /**
     * 注文ID.
     */
	Integer ordreNum;
    
    /**
     * 金額精算種別.
     */
	Integer priceSumType;
    
    /**
     * 注文明細オプション.
     */
	List<OrderDetailOptionForm> options;
}
