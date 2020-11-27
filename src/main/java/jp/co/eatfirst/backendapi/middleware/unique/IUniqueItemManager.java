package jp.co.eatfirst.backendapi.middleware.unique;

public interface IUniqueItemManager<T extends Object>{

    public T newItem() ;

    public T loadItem();

    public boolean isTimeOut();
}
