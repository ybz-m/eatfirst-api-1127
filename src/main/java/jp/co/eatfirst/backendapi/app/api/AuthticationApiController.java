package jp.co.eatfirst.backendapi.app.api;


import jp.co.eatfirst.backendapi.util.SecurityUtil;

public class AuthticationApiController {
    /**
     * @return
     */
    public Long getLoginUserId() {
        return SecurityUtil.getLoginUserIdForApi();
    }

}
