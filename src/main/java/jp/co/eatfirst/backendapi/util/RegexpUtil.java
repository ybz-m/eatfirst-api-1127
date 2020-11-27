package jp.co.eatfirst.backendapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtil {
    /**
     * @param pattern
     * @param value
     * @return
     */
    private static boolean isPattern(String pattern, String value) {
        // 正規表現のパターンを作成
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.find();
    }

    public static boolean isChineseTelNo(String telNo) {
        return isPattern("^[1]([3-9])[0-9]{9}$", telNo);
    }

    public static boolean isJapaneseTelNo(String telNo) {
        return isPattern("^(70|80|90)[0-9]{8}$", telNo);
    }
}
