package jp.co.eatfirst.backendapi.app.api;

import com.google.common.io.Files;
import jp.co.eatfirst.backendapi.app.dto.vo.ImageUploadVO;
import jp.co.eatfirst.backendapi.app.service.FileUploadService;
import jp.co.eatfirst.backendapi.middleware.IdGenerator;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/webapi/admin")
public class ImageUploadApiController extends AuthticationApiController{
    @Autowired
    FileUploadService fileUploadService;

    /**
    * 店舗用画像アップロード
    * @param file 画像ファイル
    * @return JsonResult<ImageUploadVO> アップロード結果
    * @author eatfirst
    */
    @PutMapping("/shopImage")
    public JsonResult<ImageUploadVO> updateUImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url = fileUploadService.uploadImage2Cloud("shop_image/" + IdGenerator.next(IdGenerator.IdType.IMAGE) + "." + Files.getFileExtension(file.getOriginalFilename()), file);
        return JsonResult.success(ImageUploadVO.builder().url(url).build());
    }

    /**
    * 商品用画像アップロード
    * @param file 画像ファイル
    * @return JsonResult<ImageUploadVO> アップロード結果
    * @author eatfirst
    */
    @PutMapping("/productImage")
    public JsonResult<ImageUploadVO> updateGroupBuyImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        String url = fileUploadService.uploadImage2Cloud("product_image/" + IdGenerator.next(IdGenerator.IdType.IMAGE) + "." + Files.getFileExtension(file.getOriginalFilename()), file);
        return JsonResult.success(ImageUploadVO.builder().url(url).build());
    }
}
