package jp.co.eatfirst.backendapi.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.eatfirst.backendapi.middleware.exception.BusinessException;
import jp.co.eatfirst.backendapi.middleware.security.core.ApiUser;

public class SecurityUtil {
    /**
     * @return
     */
    public static Long getLoginUserIdForApi() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ApiUser) {
            return Long.parseLong(((UserDetails) principal).getUsername());
        } else {
            throw new BusinessException("unknow Authentication type");
        }
    }

    /**
     * @return
     */
    public static Object getLoginUserApi() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ApiUser) {
            return ((ApiUser) principal);
        } else {
            throw new BusinessException("unknow Authentication type");
        }
    }
}
