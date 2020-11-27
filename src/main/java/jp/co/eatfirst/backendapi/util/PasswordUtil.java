package jp.co.eatfirst.backendapi.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Random;

public class PasswordUtil {
    public static void main(String[] args){
        System.out.println(PasswordUtil.encodepw_BCryptPasswordEncoder("tes", ""));
    }
    public static String randomNumber(int length){

        Random random = new Random();
        StringBuffer result = new StringBuffer(length);
        for (int i=0;i<length;i++)
        {
            result.append(String.valueOf(random.nextInt(10)));
        }

        return result.toString();
    }
    public static String encodepw_BCryptPasswordEncoder(String rawPassword, String salt) {
        if (StringUtils.isEmpty(salt)) {
            return new BCryptPasswordEncoder().encode(rawPassword);
        } else {
            return BCrypt.hashpw(rawPassword, salt);
        }

    }

    public static String encodepw_md5(String rawPassword, String salt) {
        if (StringUtils.isEmpty(salt)) {
            return DigestUtils.md5DigestAsHex(rawPassword.getBytes());
        } else {
            return DigestUtils.md5DigestAsHex((DigestUtils.md5DigestAsHex(rawPassword.getBytes()) + DigestUtils.md5DigestAsHex(salt.getBytes())).getBytes());
        }

    }
}
