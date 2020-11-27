package jp.co.eatfirst.backendapi.app.api;

import jp.co.eatfirst.backendapi.app.dto.form.CategoryForm;
import jp.co.eatfirst.backendapi.app.dto.form.StoreInfoForm;
import jp.co.eatfirst.backendapi.app.dto.vo.CategoryVO;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreInfoVO;
import jp.co.eatfirst.backendapi.app.service.StoreService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShopApiController extends AuthticationApiController{

    @Autowired
    StoreService storeService;

    /**
    * 店舗情報取得
    * @param storeId 店舗ID
    * @return 店舗情報
    * @author eatfirst
    */
    @GetMapping(value = {"/webapi/admin/{storeId}/infomation", "/openwebapi/{storeId}/infomation"})
    public JsonResult<StoreInfoVO> all(@PathVariable Long storeId){
        return JsonResult.success(storeService.getStoreInfo(storeId));
    }
    /**
    * 店舗情報更新
    * @param storeId 店舗ID
    * @param form 店舗情報フォーム
    * @return JsonResult 更新結果
    * @author eatfirst
    */
    @PostMapping(value = {"/openwebapi/admin/{storeId}/infomation"})
    public JsonResult updateStoreInfo(@PathVariable Long storeId, @RequestBody StoreInfoForm form){
        storeService.saveStoreInfo(storeId, form);
        return JsonResult.success();
    }
}
