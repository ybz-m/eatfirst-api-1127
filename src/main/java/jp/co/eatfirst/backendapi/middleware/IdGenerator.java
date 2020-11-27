package jp.co.eatfirst.backendapi.middleware;

public class IdGenerator {
    public enum IdType {
        DEFAULT(defaultIdGenerator),
        CATEGORY(categoryIdGenerator),
        PRODUCT(productIdGenerator),
        ORDER(orderIdGenerator),
        STORE(storeIdGenerator),
        WEBUSER(userIdGenerator),
        IMAGE(imageIdGenerator),
        STAFF(storeStaffIdGenerator);

        IdType(SnowFlake idGenerator) {
            this.idGenerator = idGenerator;
        }

        SnowFlake idGenerator;

        public long next() {
            return idGenerator.nextId();
        }
    }

    private static SnowFlake defaultIdGenerator = new SnowFlake(1, 1);
    private static SnowFlake userIdGenerator = new SnowFlake(2, 1);
    private static SnowFlake storeStaffIdGenerator = new SnowFlake(3, 1);
    private static SnowFlake categoryIdGenerator = new SnowFlake(4, 1);
    private static SnowFlake productIdGenerator = new SnowFlake(5, 1);
    private static SnowFlake orderIdGenerator = new SnowFlake(6, 1);
    private static SnowFlake storeIdGenerator = new SnowFlake(7, 1);
    private static SnowFlake imageIdGenerator = new SnowFlake(8, 1);


    public static Long next(IdType type) {
        return type.next();
    }

}
