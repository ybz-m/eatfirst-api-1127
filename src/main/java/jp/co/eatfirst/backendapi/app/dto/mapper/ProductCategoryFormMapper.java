package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dto.dto.ProductCategoryDto;
import jp.co.eatfirst.backendapi.app.dto.form.ProductCategoryForm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductCategoryFormMapper extends FormDtoMapper<ProductCategoryForm, ProductCategoryDto>{
}
