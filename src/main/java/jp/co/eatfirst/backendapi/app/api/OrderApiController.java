package jp.co.eatfirst.backendapi.app.api;

import jp.co.eatfirst.backendapi.app.dto.form.OrderForm;
import jp.co.eatfirst.backendapi.app.dto.form.ProductForm;
import jp.co.eatfirst.backendapi.app.service.OrderService;
import jp.co.eatfirst.backendapi.app.service.StoreService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderApiController extends AuthticationApiController{
    @Autowired
    OrderService orderService;


    /**
    * 注文受付
    * @param storeId　店舗ID
    * @param form　注文フォーム
    * @return  JsonResult 処理結果
    * @author eatfirst
    */
    @PutMapping(value = {"/webapi/{storeId}/order", "/openwebapi/{storeId}/order"})
    public JsonResult order(@PathVariable String storeId, @RequestBody OrderForm form){
        orderService.newOrder(storeId, form);
        return JsonResult.success();
    }

}
