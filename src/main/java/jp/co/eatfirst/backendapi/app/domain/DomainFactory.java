package jp.co.eatfirst.backendapi.app.domain;

import jp.co.eatfirst.backendapi.app.dao.repository.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainFactory {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StoreStaffRepository storeStaffRepository;
    @Autowired
    StoreInfomationRepository storeInfomationRepository;
    @Autowired
    OptionRepository optionRepository;

    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    ProductOptionRepository productOptionRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    OptionGroupRepository optionGroupRepository;

    public Store getStore(Long storeId){
        return Store.builder().categoryRepository(categoryRepository)
                .productRepository(productRepository)
                .storeInfomationRepository(storeInfomationRepository)
                .optionRepository(optionRepository)
                .productCategoryRepository(productCategoryRepository)
                .productOptionRepository(productOptionRepository)
                .optionGroupRepository(optionGroupRepository)
                .storeId(storeId)
                .build();
    }
    public User getUser(){
        return User.builder()
                .storeStaffRepository(storeStaffRepository)
                .userInfoRepository(userInfoRepository)
                .build();
    }
}
