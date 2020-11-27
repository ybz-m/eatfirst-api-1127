package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.Product;
import jp.co.eatfirst.backendapi.app.dto.vo.ProductVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityVoMapper<ProductVO, Product> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
}
