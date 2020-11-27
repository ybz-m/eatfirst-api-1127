package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.Category;
import jp.co.eatfirst.backendapi.app.dto.vo.CategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityVoMapper<CategoryVO, Category> {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
}
