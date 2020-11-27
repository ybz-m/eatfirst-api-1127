package jp.co.eatfirst.backendapi.middleware.config;

import jp.co.eatfirst.backendapi.middleware.database.DataSourceContextHolder;
import jp.co.eatfirst.backendapi.middleware.database.DynamicDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiDataSourceConfig {
    //数据源1
    @Bean(name = DataSourceContextHolder.DEFAULT_DS)
    @ConfigurationProperties(prefix = "spring.datasource.multidb.primary") // application.properteis中对应属性的前缀
    public DataSource primaryDatasource() {
        return DataSourceBuilder.create().build();
    }

    //数据源2
    @Bean(name = DataSourceContextHolder.SECONDARY_DS)
    @ConfigurationProperties(prefix = "spring.datasource.multidb.secondary") // application.properteis中对应属性的前缀
    public DataSource secondaryDatasource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(primaryDatasource());
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put(DataSourceContextHolder.DEFAULT_DS, primaryDatasource());
        dsMap.put(DataSourceContextHolder.SECONDARY_DS, secondaryDatasource());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
