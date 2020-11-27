package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

import javax.persistence.Column;

@Data
public class StaffForm {
    
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
    
    /**
     * パスワード.
     */
	private String password;
}
