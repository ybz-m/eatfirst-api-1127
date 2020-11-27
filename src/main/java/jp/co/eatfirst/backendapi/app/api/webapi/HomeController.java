package jp.co.eatfirst.backendapi.app.api.webapi;

import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /**
    * ヘルスチェック
    * @return JsonResult<String>
    * @author eatfirst
    */
    @GetMapping(value = "/")
    public JsonResult<String> home(){
        return JsonResult.success("Hello EatFirst");
    }
}
