package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.Option;
import jp.co.eatfirst.backendapi.app.dao.entity.dto.ProductOptionDto;
import jp.co.eatfirst.backendapi.app.dto.vo.OptionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OptionMapper extends EntityVoMapper<OptionVO, Option> {
    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);
    public OptionVO toVo(ProductOptionDto entity);
}
