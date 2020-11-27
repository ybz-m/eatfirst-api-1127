package jp.co.eatfirst.backendapi.app.service;

import jp.co.eatfirst.backendapi.app.domain.DomainFactory;
import jp.co.eatfirst.backendapi.app.dto.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class OrderService {

    @Autowired
    DomainFactory domainFactory;


    public void newOrder(String storeId, OrderForm form){

    }
}
