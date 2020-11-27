package jp.co.eatfirst.backendapi.middleware.database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceContextHolder {
    /**
     */
    public static final String DEFAULT_DS = "primaryDatasource";
    public static final String SECONDARY_DS = "secondaryDatasource";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDB(String dbType) {
        log.info("change to {"+dbType+"}");
        contextHolder.set(dbType);
    }

    public static String getDB() {
        return (contextHolder.get());
    }

    public static void clearDB() {
        contextHolder.remove();
    }
}
