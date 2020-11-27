package jp.co.eatfirst.backendapi.app.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jp.co.eatfirst.backendapi.app.dto.form.RefreshTokenForm;
import jp.co.eatfirst.backendapi.middleware.security.core.JwtService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/token/")
public class TokenController {
    @Autowired
    JwtService jwtService;

    @Value("${security.token-authorization}")
    private String authorizationKey = "";

    @Value("${security.token-refresh}")
    private String authorizationRefreshKey = "";

    @Value("${security.token-header}")
    private String headerKey = "";

    @GetMapping(value = {"/webapi/check", "/openwebapi/check"})
    public JsonResult check(HttpServletRequest request){
        String token = jwtService.resolveToken(request);
        if (token == null) {
            return JsonResult.fail();
        }
        try {
            DecodedJWT jwt = jwtService.verifyToken(token);
        } catch (TokenExpiredException e) {
            return JsonResult.fail();
        } catch (JWTVerificationException e) {
            return JsonResult.fail();
        }
        return JsonResult.success();
    }

    @PostMapping(value = {"/webapi/refresh", "/openwebapi/refresh"})
    public JsonResult refresh(HttpServletRequest request, HttpServletResponse response, @RequestBody RefreshTokenForm refeshTokenForm){
        String token = refeshTokenForm.getRefreshToken().replaceFirst(headerKey.endsWith(" ") ? headerKey : headerKey + " ", "");
        try {
            DecodedJWT jwt = jwtService.verifyRefreshToken(token);
            Authentication user ;
            if(request.getRequestURI().contains("/openwebapi/")){
                user = jwtService.getAuthenticationFromJwtForOpenWeb(jwt);
            } else {
                user = jwtService.getAuthenticationFromJwt(jwt);
            }
            if(user == null){

            }
            jwtService.evictRefreshToken(token);

            String newtoken = jwtService.generateToken(user, false);
            String newRefeshToken = jwtService.generateToken(user, true);
            response.setHeader(authorizationKey, String.format((headerKey.endsWith(" ") ? headerKey : headerKey + " ") + "%s", newtoken));
            response.setHeader(authorizationRefreshKey, String.format((headerKey.endsWith(" ") ? headerKey : headerKey + " ") + "%s", newRefeshToken));
        } catch (TokenExpiredException e) {
            jwtService.evictRefreshToken(token);
            return JsonResult.fail();
        } catch (JWTVerificationException e) {
            return JsonResult.fail();
        }
        return JsonResult.success();
    }
}
