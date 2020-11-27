package jp.co.eatfirst.backendapi.middleware.security.core;

import java.util.Collection;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;

public class ApiUser extends org.springframework.security.core.userdetails.User {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // Userエンティティ
    private UserInfo user;

    private StoreStaff staff;

    private String contextPath;

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public UserInfo getUser() {
        return user;
    }
    public StoreStaff getStaff() {
        return staff;
    }

    public ApiUser(UserInfo user, Collection<GrantedAuthority> roles) {
        super(String.valueOf(user.getUserId()), "", roles);
        this.user = user;
    }
    public ApiUser(StoreStaff staff, Collection<GrantedAuthority> roles) {
        super(String.valueOf(staff.getStaffId()), staff.getPassword(), roles);
        this.staff = staff;
    }

}
