package jp.co.eatfirst.backendapi.app.service;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreInformation;
import jp.co.eatfirst.backendapi.app.domain.DomainFactory;
import jp.co.eatfirst.backendapi.app.dto.form.StoreInfoForm;
import jp.co.eatfirst.backendapi.app.dto.mapper.StoreInfoMapper;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreInfoVO;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StoreService {

    private static final String STORE_CACHE = "Store_Cache";
    @Autowired
    DomainFactory domainFactory;

    @Autowired
    StoreInfoMapper storeInfoMapper;


    @Cacheable(cacheNames=STORE_CACHE, key = "#storeId")
    public StoreInfoVO getStoreInfo(Long storeId){
        Optional<StoreInformation> hasInfo = domainFactory.getStore(storeId).getStoteInfo();
        if(!hasInfo.isPresent()){
            throw new BusinessException("storeId is not exists");
        }
        return storeInfoMapper.toVo(hasInfo.get());
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames=STORE_CACHE, key = "#storeId")
    public void saveStoreInfo(Long storeId, StoreInfoForm form){
        domainFactory.getStore(storeId).saveStoteInfo(
                form.getStoreNo(),
                form.getStoreName(),
                form.getTradeDetails(),
                form.getStoreTel(),
                form.getStoreZip(),
                form.getStoreAddress1(),
                form.getStoreAddress2(),
                form.getStoreAddress3(),
                form.getStoreAddress4(),
                form.getDescreption(),
                form.getStoreLogo(),
                form.getStoreImageUrl()
        );
    }

}
