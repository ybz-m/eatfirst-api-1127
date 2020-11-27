package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class StoreInfoForm {
    
    /**
     * 店舗ID.
     */
	@NotEmpty
    private Long storeId;
    
    /**
     * 店舗番号.
     */
	private String storeNo;
    
    /**
     * 店舗名.
     */
	private String storeName;
    
    /**
     * 業種.
     */
	private Integer tradeDetails;
    
    /**
     * 電話番号.
     */
	private String storeTel;
    
    /**
     * 郵便番号.
     */
	private String storeZip;
    
    /**
     * 都道府県.
     */
	private Integer storeAddress1;
    
    /**
     * 市区村郡.
     */
	private String storeAddress2;
    
    /**
     * 町名番地.
     */
	private String storeAddress3;
    
    /**
     * ビル名等.
     */
	private String storeAddress4;
    
    /**
     * 店舗ロゴ.
     */
	private String storeLogo;
    
    /**
     * 店舗画像.
     */
	private String storeImageUrl;
    
    /**
     * 説明文.
     */
	private String descreption;
}
