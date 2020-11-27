package jp.co.eatfirst.backendapi.app.domain;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;
import jp.co.eatfirst.backendapi.app.dao.repository.db.StoreStaffRepository;
import jp.co.eatfirst.backendapi.app.dao.repository.db.UserInfoRepository;
import jp.co.eatfirst.backendapi.middleware.IdGenerator;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import jp.co.eatfirst.backendapi.util.PasswordUtil;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Builder
public class User {

    StoreStaffRepository storeStaffRepository;

    UserInfoRepository userInfoRepository;

    public Optional<StoreStaff> findStoreStaffById(Long id){
        return Optional.ofNullable(storeStaffRepository.findByStaffId(id));
    }
    public Optional<StoreStaff> findStoreStaffByName(String name){
        return Optional.ofNullable(storeStaffRepository.findByStaffName(name));
    }

    public Optional<UserInfo> findUserInfoByIdentifier(String identifier){
        return Optional.ofNullable(userInfoRepository.findByIdentifier(identifier));
    }
    public Optional<UserInfo> findByUserId(Long userId){
        return Optional.ofNullable(userInfoRepository.findByUserId(userId));
    }

    public UserInfo regsitUserForOpenWeb(String openId){
        UserInfo user = new UserInfo();
        user.setIdentifier(openId);
        user.setUserId(IdGenerator.IdType.WEBUSER.next());
        user.setCreator("auto");
        user.setUpdater("auto");
        return userInfoRepository.saveAndFlush(user);
    }


    public List<StoreStaff> getAllStaff(Long storeId){
        return storeStaffRepository.findByStoreId(storeId);
    }
    public void saveStaff(Long staffId,
                          Long storeId,
                          String staffNo,
                          String staffName,
                          int displayFlg,
                          int authority,
                          String password){
        StoreStaff staff ;
        if(staffId != null){
            Optional<StoreStaff> hasStaff = storeStaffRepository.findById(staffId);
            if(!hasStaff.isPresent()){
                throw new BusinessException("staffId is not exists");
            }
            staff = hasStaff.get();
        } else {
            staff = new StoreStaff();
            staff.setStaffId(IdGenerator.IdType.STAFF.next());
        }
        staff.setStoreId(storeId);
        staff.setStaffNo(staffNo);
        staff.setStaffName(staffName);
        staff.setDisplayFlg(displayFlg);
        staff.setAuthority(authority);
        staff.setPassword(PasswordUtil.encodepw_BCryptPasswordEncoder(password, ""));
        storeStaffRepository.save(staff);
    }
    public void delStaff(Long storeId, Long staffId){
        Optional<StoreStaff> hasStaff = storeStaffRepository.findById(staffId);
        if(!hasStaff.isPresent()){
            throw new BusinessException("staffId is not exists");
        }
        storeStaffRepository.delete(hasStaff.get());
    }
}
