package jp.co.eatfirst.backendapi.app.api;

import jp.co.eatfirst.backendapi.app.dto.form.CategoryForm;
import jp.co.eatfirst.backendapi.app.dto.form.ProductForm;
import jp.co.eatfirst.backendapi.app.dto.vo.CategoryVO;
import jp.co.eatfirst.backendapi.app.dto.vo.ProductVO;
import jp.co.eatfirst.backendapi.app.service.ProductService;
import jp.co.eatfirst.backendapi.app.service.StoreService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductApiController extends AuthticationApiController{

    @Autowired
    ProductService productService;



    /**
    * 商品一覧取得
    * @param storeId 店舗ID
    * @param categoryId カテゴリID
    * @return JsonResult<List<ProductVO>> 商品一覧
    * @author eatfirst
    */
    @GetMapping(value = {"/webapi/admin/{storeId}/{categoryId}/products", "/openwebapi/{storeId}/{categoryId}/products"})
    public JsonResult<List<ProductVO>> all(@PathVariable Long storeId, @PathVariable Long categoryId){
        List<ProductVO> products = productService.getAllProductByCategory(storeId, categoryId);
        return JsonResult.success(products);
    }
    /**
    * 商品作成
    * @param storeId 店舗ID
    * @param form 商品フォーム
    * @return JsonResult 処理結果
    * @author eatfirst
    */
    @PutMapping(value = {"/webapi/admin/{storeId}/product"})
    public JsonResult save(@PathVariable Long storeId, @RequestBody ProductForm form){
        productService.saveProduct(storeId, form);
        return JsonResult.success();
    }

    /**
    * 商品削除
    * @param storeId 店舗ID
    * @param categoryId カテゴリID
    * @param productId 商品ID
    * @return JsonResult 処理結果
    * @author eatfirst
    */
    @DeleteMapping(value = {"/webapi/admin/{storeId}/{categoryId}/{productId}"})
    public JsonResult del(@PathVariable Long storeId, @PathVariable Long categoryId, @PathVariable Long productId){
        productService.deleteProduct(storeId, categoryId, productId);
        return JsonResult.success();
    }
}
