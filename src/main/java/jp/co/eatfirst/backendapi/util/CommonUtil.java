package jp.co.eatfirst.backendapi.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class CommonUtil {
    /**
     * 生成随机密码生成方式一 密码字典 -> 随机获取字符
     *
     * @param len 生成密码长度
     * @return
     */
    public static String makePwd(int len) {
        // 生成的随机数
        int i;
        // 生成的密码的长度
        int count = 0;
        // 密码字典
        char[] str = { '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', '2', '3', '4', '5', '6', '7', '8', '9', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', '2', '3', '4', '5', '6', '7', '8', '9', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2',
                '3', '4', '5', '6', '7', '8', '9', };
        StringBuilder stringBuffer = new StringBuilder("");
        Random r = new Random();
        while (count < len) {
            // 生成 0 ~ 密码字典-1之间的随机数
            i = r.nextInt(str.length);
            stringBuffer.append(str[i]);
            count++;
        }
        return stringBuffer.toString();
    }

    public static boolean isWechat(HttpServletRequest request) {
        String ua = request.getHeader("user-agent").toLowerCase();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(ua) && ua.indexOf("micromessenger") > 0) {
            // 是微信浏览器
            return true;
        }
        return false;
    }

    /**
     * Integerの値の比較.
     *
     * @param intRes 比較元
     * @param intTar 比較対象
     * @return 比較結果
     */
    public static boolean equals(Integer intRes, Integer intTar) {

        if (null == intRes && null == intTar) {
            return true;
        }

        if (null == intRes || null == intTar) {
            return false;
        }

        if (intRes.intValue() == intTar.intValue()) {
            return true;
        }

        return false;
    }


    public static <E> List<E> getSubList(int page, int pageSize, List<E> motoList) {
        int pageS = pageSize * page;
        int pageE = pageSize * (page + 1);
        int subListS = 0;
        int subListE = 0;

        if (motoList.size() <= pageSize) {
            subListS = 0;
            subListE = motoList.size();
        } else if (motoList.size() <= pageS || motoList.size() <= pageE) {
            subListS = (motoList.size() - pageSize);
            subListE = (motoList.size());
        } else if (motoList.size() > pageE) {
            subListS = pageS;
            subListE = pageE;
        }

        List<E> subList = motoList.subList(subListS, subListE);

        return subList;
    }

    /**
     * javabean 转 Map
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> transBeanToMap(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}
