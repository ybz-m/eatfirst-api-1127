package jp.co.eatfirst.backendapi.app.domain;

import jp.co.eatfirst.backendapi.app.dao.repository.db.OrderDetailOptionRepository;
import jp.co.eatfirst.backendapi.app.dao.repository.db.OrderDetailsRepository;
import jp.co.eatfirst.backendapi.app.dao.repository.db.OrderInfoRepository;
import jp.co.eatfirst.backendapi.app.dto.dto.OrderDetailDto;

import java.util.List;

public class Order {

    Long orderId;
    OrderInfoRepository orderInfoRepository;

    OrderDetailsRepository orderDetailsRepository;

    OrderDetailOptionRepository orderDetailOptionRepository;

    public void saveOrder(Long storeId,
                         Long tableId,
                         Long orderId,
                         Integer paymentId,
                         Integer takeoutFlg,
                         String receiptTime,
                         String tel,
                         List<OrderDetailDto> details){

    }
}
