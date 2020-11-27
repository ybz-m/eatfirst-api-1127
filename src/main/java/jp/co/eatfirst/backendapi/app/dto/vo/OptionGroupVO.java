package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupVO {
    
    /**
     * オプショングループID.
     */
	private Long optionGroupId;
    
    /**
     * 表示名.
     */
	private String name;
    
    /**
     * 表示名(英語).
     */
	private String nameEn;
    
    /**
     * 表示名(中国語).
     */
	private String nameCn;
    
    /**
     * オプション.
     */
	List<OptionVO> options;
}
