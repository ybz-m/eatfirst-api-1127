package jp.co.eatfirst.backendapi.middleware.security.open;

import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;
import jp.co.eatfirst.backendapi.app.service.WebUserService;
import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import jp.co.eatfirst.backendapi.middleware.security.core.ApiUser;
import jp.co.eatfirst.backendapi.middleware.security.core.LoginUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class OpenAuthenticationManager implements AuthenticationManager {
    @Autowired
    private WebUserService userService;

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    private static String CONTEXT_PATH_OPENWEB = "open-web";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationToken openWebAuthenticationToken = null;
        if (authentication instanceof AuthenticationToken) {
            openWebAuthenticationToken = (AuthenticationToken) authentication;
        }
        try {
            ApiUser account = loginUserDetailsService.loadWebUserByIdentifier(openWebAuthenticationToken.getOpenid());

//            if (account != null && 7 < account.getUser().getStatus()) {
//                throw new UserLockedException(account.getUser());
//            }

            account.setContextPath(CONTEXT_PATH_OPENWEB);
            return new AuthenticationToken(account, openWebAuthenticationToken.getOpenid());
        } catch (UsernameNotFoundException e) {
            try {
                log.info("account not exist, began to register. openid is [{}]", openWebAuthenticationToken.getOpenid());

                UserInfo user = userService.regsitUserForOpenWeb(openWebAuthenticationToken.getOpenid());
                ApiUser apiUser = new ApiUser(user, getAuthorities(new ArrayList<String>(20)));
                apiUser.setContextPath(CONTEXT_PATH_OPENWEB);
                return new AuthenticationToken(apiUser, openWebAuthenticationToken.getOpenid());
            } catch (Exception e2) {
                log.error(e2.getLocalizedMessage());
                log.error(e2.getMessage());
                throw new BusinessException("regist failed");
            }
        }

    }

    private Collection<GrantedAuthority> getAuthorities(List<String> grants) {
        return AuthorityUtils.createAuthorityList(grants.toArray(new String[grants.size()]));
    }

}
