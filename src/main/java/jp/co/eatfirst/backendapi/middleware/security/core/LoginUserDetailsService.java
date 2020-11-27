package jp.co.eatfirst.backendapi.middleware.security.core;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;
import jp.co.eatfirst.backendapi.app.service.StaffService;
import jp.co.eatfirst.backendapi.middleware.log.Log;
import jp.co.eatfirst.backendapi.app.service.WebUserService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Userエンティティ
 */
@Service
@Log
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private WebUserService userService;
    @Autowired
    private StaffService storeStaffService;

    @Override
    public ApiUser loadUserByUsername(final String userName) {
        StoreStaff staff = storeStaffService.findStaffId(Long.parseLong(userName)).orElseThrow(() -> new UsernameNotFoundException("User is Not Found!"));

        return new ApiUser(staff, getAuthorities(Lists.newArrayList(!ObjectUtils.equals(staff.getAuthority(), 1L) ? "ROLE_ADMIN": "ROLE_USER")));
    }
    public ApiUser loadUserById(final String userId) {

        StoreStaff staff = storeStaffService.findStaffId(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("User is Not Found!"));

        return new ApiUser(staff, getAuthorities(Lists.newArrayList(!ObjectUtils.equals(staff.getAuthority(), 1L) ? "ROLE_ADMIN": "ROLE_USER")));
    }
    public ApiUser loadWebUserByIdentifier(final String identifier) {

        UserInfo user = userService.findUserInfoByIdentifier(identifier).orElseThrow(() -> new UsernameNotFoundException("User is Not Found!"));

        return new ApiUser(user, getAuthorities(Lists.newArrayList("ROLE_Anonymous")));
    }
    public ApiUser loadWebUserById(final String id) {

        UserInfo user = userService.findByUserId(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException("User is Not Found!"));

        return new ApiUser(user, getAuthorities(Lists.newArrayList("ROLE_Anonymous")));
    }

    private Collection<GrantedAuthority> getAuthorities(List<String> grants) {
        return AuthorityUtils.createAuthorityList(grants.toArray(new String[grants.size()]));
    }

}
