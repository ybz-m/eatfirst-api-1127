package jp.co.eatfirst.backendapi.middleware.exception;


import jp.co.eatfirst.backendapi.app.dao.entity.UserInfo;

public class UserLockedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    UserInfo lockedUser;

    public UserInfo getLockedUser() {
        return lockedUser;
    }

    public UserLockedException(UserInfo lockedUser) {
        this.lockedUser = lockedUser;
    }

    public UserLockedException() {
        super();
    }
}
