package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.OptionGroup;
import jp.co.eatfirst.backendapi.app.dto.vo.OptionGroupVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OptionGroupMapper extends EntityVoMapper<OptionGroupVO, OptionGroup> {
    OptionGroupMapper INSTANCE = Mappers.getMapper(OptionGroupMapper.class);
}
