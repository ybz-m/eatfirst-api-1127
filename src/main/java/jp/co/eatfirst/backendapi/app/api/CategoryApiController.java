package jp.co.eatfirst.backendapi.app.api;

import jp.co.eatfirst.backendapi.app.dto.form.CategoryForm;
import jp.co.eatfirst.backendapi.app.dto.vo.CategoryVO;
import jp.co.eatfirst.backendapi.app.service.ProductService;
import jp.co.eatfirst.backendapi.app.service.StoreService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryApiController extends AuthticationApiController{

    @Autowired
    ProductService productService;


    /**
    * カテゴリ一覧取得
    * @param storeId 店舗ID
    * @return JsonResult<List<CategoryVO>>　カテゴリ一覧
    * @author eatfirst
    */
    @GetMapping(value = {"/webapi/{storeId}/categorys", "/openwebapi/{storeId}/categorys"})
    public JsonResult<List<CategoryVO>> all(@PathVariable Long storeId){
        List<CategoryVO> categorys = productService.getAllCategory(storeId);
        return JsonResult.success(categorys);
    }
    /**
     * カテゴリ一作成
     * @param storeId 店舗ID
     * @param form カテゴリ登録フォーム
     * @return JsonResult　登録結果
     * @author eatfirst
     */
    @PutMapping(value = {"/webapi/{storeId}/category"})
    public JsonResult save(@PathVariable Long storeId, @RequestBody CategoryForm form){
        productService.saveCategory(storeId, form);
        return JsonResult.success();
    }
    /**
     * カテゴリ一削除
     * @param storeId 店舗ID
     * @param categoryId カテゴリID
     * @return JsonResult　削除結果
     * @author eatfirst
     */
    @DeleteMapping(value = {"/webapi/{storeId}/category/{categoryId}"})
    public JsonResult del(@PathVariable Long storeId, @PathVariable Long categoryId){
        productService.deleteCategory(storeId, categoryId);
        return JsonResult.success();
    }
}
