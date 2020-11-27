package jp.co.eatfirst.backendapi.app.dto.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {
	/**
     * カテゴリーID.
     */
	Long categoryId;
	
	/**
     * 色.
     */
    @NotEmpty
    String color;

    /**
     * カテゴリー名.
     */
    @NotEmpty
    String name;
    
    /**
     * カテゴリー名(英語).
     */
    String nameEn;
    
    /**
     * カテゴリー名(中国語).
     */
    String nameCn;

    /**
     * TODO
     */
    @NotEmpty
    String nameAbb;

    /**
     * 表示順.
     */
    @NotNull
    int displayOrder;

    /**
     * 表示Flag.
     */
    @NotNull
    int categoryDisplay;
}
