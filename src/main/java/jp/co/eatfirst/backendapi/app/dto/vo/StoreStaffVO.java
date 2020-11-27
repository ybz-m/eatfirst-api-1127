package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreStaffVO {
    
    /**
     * スタッフID.
     */
	private String staffId;
    
    /**
     * 店舗ID.
     */
	private String storeId;
    
    /**
     * スタッフ番号.
     */
	private String staffNo;
    
    /**
     * スタッフ名.
     */
	private String staffName;
    
    /**
     * 表示Flag.
     */
	private int displayFlg;
    
    /**
     * 権限.
     */
	private int authority;
}
