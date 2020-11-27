package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreInformation;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreInfoVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreInfoMapper extends EntityVoMapper<StoreInfoVO, StoreInformation> {
}
