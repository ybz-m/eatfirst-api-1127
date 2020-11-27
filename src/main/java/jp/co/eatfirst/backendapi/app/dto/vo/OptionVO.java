package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionVO {
    
    /**
     * オプションID.
     */
	private Long optionId;
    
    /**
     * 種別1.
     */
	private String type1;
    
    /**
     * 種別1(中国語).
     */
	private String type1Cn;
    
    /**
     * 種別1(英語).
     */
	private String type1Eb;
    
    /**
     * 種別2.
     */
	private String type2;
    
    /**
     * 種別2(中国語).
     */
	private String type2Cn;
    
    /**
     * 種別2(英語).
     */
	private String type2En;
    
    /**
     * 通用税率.
     */
	private BigDecimal taxRate;
    
    /**
     * 価格.
     */
	private Long price;
    
    /**
     * 原価.
     */
	private Long cost;
    
    /**
     * 商品番号.
     */
	private String barcodeNo;
}
