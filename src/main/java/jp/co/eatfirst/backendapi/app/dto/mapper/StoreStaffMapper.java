package jp.co.eatfirst.backendapi.app.dto.mapper;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreStaffVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoreStaffMapper extends EntityVoMapper<StoreStaffVO, StoreStaff> {
}
