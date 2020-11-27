package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenForm{
    
    /**
     * 更新トークン.
     */
	@NotEmpty
    String refreshToken;
}
