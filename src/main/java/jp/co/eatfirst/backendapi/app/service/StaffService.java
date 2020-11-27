package jp.co.eatfirst.backendapi.app.service;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dao.repository.db.StoreStaffRepository;
import jp.co.eatfirst.backendapi.app.domain.DomainFactory;
import jp.co.eatfirst.backendapi.app.dto.form.StaffForm;
import jp.co.eatfirst.backendapi.app.dto.mapper.StoreStaffMapper;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreStaffVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private static final String STORE_STAFFS_CACHE = "Store_Staffs_Cache";
    @Autowired
    DomainFactory domainFactory;
    @Autowired
    StoreStaffMapper storeStaffMapper;

    public Optional<StoreStaff> findStaffId(Long id){
        return domainFactory.getUser().findStoreStaffById(id);
    }
    public Optional<StoreStaff> findStaffName(String name){
        return domainFactory.getUser().findStoreStaffByName(name);
    }

    @Cacheable(cacheNames=STORE_STAFFS_CACHE, key = "#storeId")
    public List<StoreStaffVO> getAllStaff(Long storeId){
        return storeStaffMapper.toVo(domainFactory.getUser().getAllStaff(storeId));
    }
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames=STORE_STAFFS_CACHE, key = "#storeId")
    public void saveStaff(Long storeId, Long staffId, StaffForm form){
        domainFactory.getUser().saveStaff(staffId, storeId, form.getStaffNo(), form.getStaffName(), form.getDisplayFlg(), form.getAuthority(), form.getPassword());
    }
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames=STORE_STAFFS_CACHE, key = "#storeId")
    public void delStaff(Long storeId, Long staffId){
        domainFactory.getUser().delStaff(storeId, staffId);
    }
}
