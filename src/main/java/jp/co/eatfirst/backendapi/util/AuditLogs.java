package jp.co.eatfirst.backendapi.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

public class AuditLogs {

    /**
     * ログイン監査のログ出力用の文字列を生成
     *
     * @param request リクエスト情報
     * @return ログ出力用の文字列（タブ区切り）
     */
    public static String createAuditLog(String result, HttpServletRequest request) {
        String xForwardedFor = request.getHeader("x-forwarded-for");
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("result", result);
        map.put("id", request.getParameter("loginId"));
        map.put("remoteHost", xForwardedFor == null ? request.getRemoteAddr() : xForwardedFor);
        map.put("userAgent", request.getHeader("user-agent"));

        StringJoiner sj = new StringJoiner("\t");
        map.entrySet().stream().forEach(m -> sj.add(m.getKey() + ":" + m.getValue()));
        return sj.toString();
    }

}
