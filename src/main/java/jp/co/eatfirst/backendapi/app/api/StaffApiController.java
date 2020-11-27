package jp.co.eatfirst.backendapi.app.api;

import jp.co.eatfirst.backendapi.app.dao.entity.StoreStaff;
import jp.co.eatfirst.backendapi.app.dto.form.StaffForm;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreInfoVO;
import jp.co.eatfirst.backendapi.app.dto.vo.StoreStaffVO;
import jp.co.eatfirst.backendapi.app.service.StaffService;
import jp.co.eatfirst.backendapi.app.service.StoreService;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webapi/admin")
public class StaffApiController extends AuthticationApiController{
    @Autowired
    StaffService staffService;

    /**
    * スタッフ一覧取得
    * @param storeId 店舗ID
    * @return JsonResult<List<StoreStaffVO>> スタッフ一覧
    * @author eatfirst
    */
    @GetMapping(value = {"/{storeId}/staffs"})
    public JsonResult<List<StoreStaffVO>> all(@PathVariable Long storeId){
        return JsonResult.success(staffService.getAllStaff(storeId));
    }
    /**
     * スタッフ作成
     * @param storeId 店舗ID
     * @param form スタッフフォーム
     * @return JsonResult 処理結果
     * @author eatfirst
     */
    @PutMapping(value = {"/{storeId}/staff"})
    public JsonResult<StoreInfoVO> saveStaff(@PathVariable Long storeId,  @RequestBody StaffForm form){
        staffService.saveStaff(storeId, null, form);
        return JsonResult.success();
    }
    /**
     * スタッフ更新
     * @param storeId 店舗ID
     * @param staffId スタッフID
     * @param form スタッフフォーム
     * @return JsonResult 処理結果
     * @author eatfirst
     */
    @PostMapping(value = {"/{storeId}/staff/{staffId}"})
    public JsonResult<StoreInfoVO> updateStaff(@PathVariable Long storeId,  @PathVariable Long staffId,  @RequestBody StaffForm form){
        staffService.saveStaff(storeId, staffId, form);
        return JsonResult.success();
    }
    /**
     * スタッフ削除
     * @param storeId 店舗ID
     * @param staffId スタッフID
     * @return JsonResult 処理結果
     * @author eatfirst
     */
    @DeleteMapping(value = {"/{storeId}/staff/{staffId}"})
    public JsonResult<StoreInfoVO> delStaff(@PathVariable Long storeId, @PathVariable Long staffId){
        staffService.delStaff(storeId, staffId);
        return JsonResult.success();
    }
}
