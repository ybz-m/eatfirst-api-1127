package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductForm {
    
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
	private int taxType;
    
    /**
     * レシート印字.
     */
	private int receiptPrinting;
    
    /**
     * コメント.
     */
	private String comment;
    
    /**
     * 商品カテゴリー.
     */
	private List<ProductCategoryForm> categorys;
    
    /**
     * 表示Flag.
     */
	private int displayFlg;
}
