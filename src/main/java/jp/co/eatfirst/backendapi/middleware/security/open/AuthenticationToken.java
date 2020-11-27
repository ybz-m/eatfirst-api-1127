package jp.co.eatfirst.backendapi.middleware.security.open;

import jp.co.eatfirst.backendapi.middleware.security.core.ApiUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String openid;
    private ApiUser user;

    public AuthenticationToken(String openid) {
        super(null);
        this.openid = openid;

    }
    public AuthenticationToken(ApiUser user, String openid) {
        super(user.getAuthorities());
        this.user = user;
        this.openid = openid;
        super.setAuthenticated(true);
    }
    public AuthenticationToken(ApiUser user) {
        super(user.getAuthorities());
        this.user = user;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return this.user;
    }

    public Object getPrincipal() {
        return this.user;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}