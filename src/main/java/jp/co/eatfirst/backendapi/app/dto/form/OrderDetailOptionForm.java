package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

@Data
public class OrderDetailOptionForm {
    
    /**
     * オプショングループID.
     */
	String optionGroupId;
    
    /**
     * オプションID.
     */
	Integer optionId;
}
