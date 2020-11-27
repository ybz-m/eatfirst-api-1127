package jp.co.eatfirst.backendapi.app.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadVO {
    /**
     * 画像URL.
     */
    String url;
}
