package jp.co.eatfirst.backendapi.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.github.dozermapper.core.Mapper;

public class DozerUtils {

    /**
     * 封装dozer处理集合的方法：List<S> --> List<T>
     */
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass) {

        if (CollectionUtils.isEmpty(sourceList)) {
            return null;
        }
        return sourceList.stream().map(item -> mapper.map(item, targetObjectClass)).collect(Collectors.toList());
    }
}
