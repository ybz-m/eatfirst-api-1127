package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVO {
    
    /**
     * 商品ID.
     */
	private Long productId;
    
    /**
     * TODO.
     */
	private String categoryDisplayPosition;
    
    /**
     * 商品画像.
     */
	private String productImage;
    
    /**
     * 商品名.
     */
	private String productName;
    
    /**
     * 商品名(英語).
     */
	private String productNameEn;
    
    /**
     * 商品名(中国語).
     */
	private String productNameCn;
    
    /**
     * 商品名レシート用.
     */
	private String productNameReceipt;
    
    /**
     * 税設定.
     */
	private Integer taxType;
    
    /**
     * レシート印字.
     */
	private Integer receiptPrinting;
    
    /**
     * 表示Flag.
     */
	private Integer displayFlg;
    
    /**
     * コメント.
     */
	private String comment;
    
    /**
     * オプショングループ.
     */
	List<OptionGroupVO> optionGroups;
}
