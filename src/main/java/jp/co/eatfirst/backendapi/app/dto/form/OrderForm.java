package jp.co.eatfirst.backendapi.app.dto.form;

import java.util.List;

public class OrderForm {
    
    /**
     * テーブルID.
     */
	String tableId;
    
    /**
     * 店内ORテイクアウト.
     */
	String insideOrTakeout;
    
    /**
     * 携帯電話番号.
     */
	String personalTel;
    
    /**
     * 注文明細.
     */
	List<OrderDetailForm> details;
}
