package jp.co.eatfirst.backendapi.app.service;

import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;
import jp.co.eatfirst.backendapi.app.dao.repository.db.UserInfoRepository;
import jp.co.eatfirst.backendapi.app.domain.DomainFactory;
import jp.co.eatfirst.backendapi.middleware.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WebUserService {
    @Autowired
    DomainFactory domainFactory;

    public Optional<UserInfo> findUserInfoByIdentifier(String identifier){
        return domainFactory.getUser().findUserInfoByIdentifier(identifier);
    }
    public Optional<UserInfo> findByUserId(Long userId){
        return domainFactory.getUser().findByUserId(userId);
    }
    @Transactional(rollbackFor = Exception.class)
    public UserInfo regsitUserForOpenWeb(String openId){
        return domainFactory.getUser().regsitUserForOpenWeb(openId);
    }


}
