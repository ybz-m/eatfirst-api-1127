package jp.co.eatfirst.backendapi.middleware.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;


/**
 * API結果ファイル.
 */
public class JsonResult<T> {
    /**
     * API実施結果コード.
     */
    private int code;
    /**
     * メッセージコード.
     */
    private Map<String, List<String>> msg;
    /**
     * 結果データ.
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, List<String>> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, List<String>> msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "code=" + code + " message=" + msg + " data=" + data;
    }

    /**
     * API実施失敗の場合、該当エラー状態になる. <BR>
     * エラーメッセージ設定されない場合、デフォールトメッセージを出力する
     */
    public static <T> JsonResult<T> fail() {
        return JsonResult.fail("想定外のエラーが発生しました。");
    }

    /**
     * API実施失敗の場合、該当エラー状態になる. <BR>
     * エラーメッセージ設定されない場合、デフォールトメッセージを出力する <BR>
     * 戻りデータも設定できる
     */
    public static <T> JsonResult<T> fail(T data) {
        return JsonResult.fail(null, data);
    }

    /**
     * API実施失敗の場合、該当エラー状態になる. <BR>
     * エラーメッセージと戻りデータも設定できる
     */
    public static <T> JsonResult<T> fail(String msg, T data) {
        JsonResult<T> ret = JsonResult.fail(msg);
        ret.setData(data);
        return ret;
    }

    /**
     * API実施NGの場合、該当エラー状態になる. <BR>
     * エラーメッセージを設定できる
     */
    public static <T> JsonResult<T> fail(String msg) {
        JsonResult<T> ret = new JsonResult<T>();
        ret.setCode(1);
        if (!StringUtils.isEmpty(msg)) {
            Map<String, List<String>> msgMap = new HashMap<String, List<String>>();
            msgMap.put(msg, null);
            ret.setMsg(msgMap);
        }
        return ret;
    }

    public static <T> JsonResult<T> fail(Map<String, List<String>> msgMap) {
        JsonResult<T> ret = JsonResult.fail(new String());
        ret.setMsg(msgMap);
        return ret;
    }

    /**
     * API実施OKの場合、該当状態になる. <BR>
     */
    public static <T> JsonResult<T> success() {
        JsonResult<T> ret = new JsonResult<T>();
        ret.setCode(0);
        return ret;
    }

    /**
     * API実施OKの場合、該当状態になる. <BR>
     * メッセージを設定できる
     */
    public static <T> JsonResult<T> successOnlyMessage(String msg) {
        JsonResult<T> ret = JsonResult.success();
        Map<String, List<String>> msgMap = new HashMap<String, List<String>>();
        msgMap.put("success", Lists.newArrayList(msg));
        ret.setMsg(msgMap);
        return ret;
    }

    /**
     * API実施OKの場合、該当状態になる. <BR>
     * 戻りデータを設定できる
     */
    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> ret = JsonResult.success();
        ret.setData(data);
        return ret;
    }

    /**
     * API実施OKの場合、該当状態になる. <BR>
     * メッセージと戻りデータを設定できる
     */
    public static <T> JsonResult<T> success(String msg, T data) {
        JsonResult<T> ret = JsonResult.successOnlyMessage(msg);
        ret.setData(data);
        return ret;
    }

    public static <T> JsonResult<T> success(Map<String, List<String>> msgMap) {
        JsonResult<T> ret = JsonResult.success();
        ret.setMsg(msgMap);
        return ret;
    }

    public static <T> JsonResult<T> success(Map<String, List<String>> msgMap, T data) {
        JsonResult<T> ret = JsonResult.success(msgMap);
        ret.setData(data);
        return ret;
    }
}