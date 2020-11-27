package jp.co.eatfirst.backendapi.middleware.security.core;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jp.co.eatfirst.backendapi.middleware.security.open.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
    private static final String ROLES = "ROLES";

    @Value("${security.secret-key}")
    private String secretKey = "";

    @Value("${security.token-authorization}")
    private String authorizationKey = "";

    @Value("${security.token-header}")
    private String headerKey = "";

    @Value("${security.token-expiration}")
    private Long expiration = 30L;
    @Value("${security.token-expiration-long}")
    private Long expirationLong = 1440L;

    @Autowired
    CacheManager cacheManager;

    private static final String JWT_TOKENS = "jwtTokens";
    private static final String JWT_REFRESH_TOKENS = "jwtRefreshTokens";
    private static final String APP_CONTEXT_PATH = "appContextPath";

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    public String resolveToken(ServletRequest request) {
        String token = ((HttpServletRequest) request).getHeader(authorizationKey);
        if (token == null || !token.startsWith(headerKey)) {
            return null;
        }
        return token.replaceFirst(headerKey.endsWith(" ") ? headerKey : headerKey + " ", "");
    }

    public String generateToken(Authentication auth, boolean isLongToken) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return this.generateToken(auth, algorithm, isLongToken);
    }

    private String generateToken(Authentication auth, Algorithm algorithm, boolean isLongToken) {
        ApiUser loginUser = (ApiUser) auth.getPrincipal();
        Date issuedAt = new Date();
        Date notBefore = new Date(issuedAt.getTime());
        Date expiresAt = new Date(issuedAt.getTime() + TimeUnit.MINUTES.toMillis(expiration));

        String jwtId = null;
        while (jwtId == null) {
            jwtId = UUID.randomUUID().toString();
        }

        String token = JWT.create()
                // JWT ID
                .withJWTId(jwtId)
                // 発行時刻
                .withIssuedAt(issuedAt)
                // 有効になる日時
                .withNotBefore(notBefore)
                // 失効する日時
                .withExpiresAt(expiresAt)
                // 用途
                .withSubject(loginUser.getUsername())
                // 权限
                .withArrayClaim(ROLES, loginUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                // appContextPath
                .withClaim(APP_CONTEXT_PATH, loginUser.getContextPath()).sign(algorithm);

        cacheManager.getCache(isLongToken? JWT_REFRESH_TOKENS : JWT_TOKENS).put(generateTokenKeyName(loginUser.getContextPath(), loginUser.getUsername(), token), "");
        return token;
    }
    private static String generateTokenKeyName(String contextPath, String userName, String token) {
        return String.join("_", contextPath, userName, token);
    }
    private static String generateTokenKeyName(DecodedJWT jwt, String token) {
        return String.join("_", jwt.getClaim(APP_CONTEXT_PATH).asString(), jwt.getSubject(), token);
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return this.verifyToken(token, algorithm, false);
    }

    public DecodedJWT verifyRefreshToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return this.verifyToken(token, algorithm, true);
    }

    private DecodedJWT verifyToken(String token, Algorithm algorithm, boolean isLongToken) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        if(cacheManager.getCache(isLongToken? JWT_REFRESH_TOKENS : JWT_TOKENS) == null
                || cacheManager.getCache(isLongToken? JWT_REFRESH_TOKENS : JWT_TOKENS).get(generateTokenKeyName(jwt, token)) == null){
            throw new TokenExpiredException("token is expired");
        }
        return jwt;
    }

    public void authentication(DecodedJWT tokenPayload) {
        SecurityContextHolder.getContext().setAuthentication(getAuthenticationFromJwt(tokenPayload));
    }
    public void authenticationForOpenWeb(DecodedJWT tokenPayload) {
        SecurityContextHolder.getContext().setAuthentication(getAuthenticationFromJwtForOpenWeb(tokenPayload));
    }
    public Authentication getAuthenticationFromJwt(DecodedJWT tokenPayload) {
        ApiUser user = loginUserDetailsService.loadUserById(tokenPayload.getSubject());

//        if (user != null && 7 < user.getUser().getStatus()) {
//            throw new UserLockedException(user.getUser());
//        }

        String contextPath = tokenPayload.getClaim(APP_CONTEXT_PATH).asString();
        user.setContextPath(contextPath);
        return new AuthenticationToken(user);
    }
    public Authentication getAuthenticationFromJwtForOpenWeb(DecodedJWT tokenPayload) {
        ApiUser user = loginUserDetailsService.loadWebUserById(tokenPayload.getSubject());

//        if (user != null && 7 < user.getUser().getStatus()) {
//            throw new UserLockedException(user.getUser());
//        }

        String contextPath = tokenPayload.getClaim(APP_CONTEXT_PATH).asString();
        user.setContextPath(contextPath);
        return new AuthenticationToken(user);
    }

    public void evictToken(String token) {
        DecodedJWT jwt = verifyToken(token);
        cacheManager.getCache(JWT_TOKENS).evict(generateTokenKeyName(jwt, token));
    }
    public void evictRefreshToken(String token) {
        DecodedJWT jwt = verifyRefreshToken(token);
        cacheManager.getCache(JWT_REFRESH_TOKENS).evict(generateTokenKeyName(jwt, token));
    }

    public DecodedJWT parseJwtPayload(String token) {
        DecodedJWT result = null;
        try {
            result = JWT.decode(token);
        } catch (JWTDecodeException e) {
            result = null;
        }
        return result;
    }
}
