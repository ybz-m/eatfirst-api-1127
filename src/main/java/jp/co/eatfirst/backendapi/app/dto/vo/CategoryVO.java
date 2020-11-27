package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryVO {
    
    /**
     * カテゴリーID.
     */
	private String categoryId;
    
    /**
     * 色.
     */
	private String colorCode;
    
    /**
     * カテゴリー名.
     */
	private String categoryName;
    
    /**
     * ++
     * カテゴリー名(英語).
     */
	private String categoryNameEn;
    
    /**
     * ++
     * カテゴリー名(中国語).
     */
	private String categoryNameCn;
        
    /**
     * TODO.
     */
	private String categoryNameAbb;
    
    /**
     * 表示Flag.
     */
	private Long categoryDisplay;
}
