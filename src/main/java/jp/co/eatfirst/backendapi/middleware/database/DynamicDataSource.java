package jp.co.eatfirst.backendapi.middleware.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        log.info("looking for datasource:"+DataSourceContextHolder.getDB());
        if(StringUtils.isEmpty(DataSourceContextHolder.getDB())){
            log.info("user default datasource:"+DataSourceContextHolder.DEFAULT_DS);
            return DataSourceContextHolder.DEFAULT_DS;
        } else {
            return DataSourceContextHolder.getDB();
        }
    }
}
