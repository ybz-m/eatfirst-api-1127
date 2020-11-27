package jp.co.eatfirst.backendapi.middleware.exception.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.eatfirst.backendapi.middleware.exception.*;
import jp.co.eatfirst.backendapi.middleware.web.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jp.co.eatfirst.backendapi.middleware.frequentlycheck.FrequentlyCheckException;
import lombok.extern.slf4j.Slf4j;

/**
 * 例外ハンドラークラス.
 * <p>
 * 特定の例外をハンドリングしてログ出力します。
 */
@ControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<JsonResult> handleMultipartException(MultipartException e) {
        log.info("ファイルサイズの制限を超えたファイルがアップロードされました。", e.getMessage());
        // TODO error_codeを追記します
        return new ResponseEntity<JsonResult>(JsonResult.fail(""), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessCheckErrorException.class)
    public ResponseEntity<JsonResult> handleBusinessCheckErrorException(BusinessCheckErrorException e) {
        log.warn("check error", e);
        Map error = Maps.newHashMap();
        error.put("error", Lists.newArrayList(messageSource.getMessage(e.getCode(), null, Locale.JAPANESE)));
        return new ResponseEntity<JsonResult>(JsonResult.fail(error), HttpStatus.OK);
    }

    @ExceptionHandler(FrequentlyCheckException.class)
    public ResponseEntity<JsonResult> handleFrequentlyCheckException(FrequentlyCheckException e) {
        log.warn("頻繁に操作しています。", e);
        Map error = Maps.newHashMap();
        error.put("error", Lists.newArrayList(e.getMessage()));
        return new ResponseEntity<JsonResult>(JsonResult.fail(error), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResult> handleBusinessException(MethodArgumentNotValidException e) {
        log.warn("入力不備があります、確認してください。");
        log.debug("入力不備があります、確認してください。", e);
        Map<String, List<String>> messages = e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.groupingBy(x -> ((FieldError) x).getField(), LinkedHashMap::new, Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())));
        return new ResponseEntity<JsonResult>(JsonResult.fail(messages), HttpStatus.OK);
    }

    @ExceptionHandler(ApiCheckErrorException.class)
    public ResponseEntity<JsonResult> handleBusinessException(ApiCheckErrorException e) {
        log.warn("入力不備があります、確認してください。");
        log.debug("入力不備があります、確認してください。", e);
        Map<String, List<String>> messages = e.getErrors().stream()
                .collect(Collectors.groupingBy(x -> ((FieldError) x).getField(), LinkedHashMap::new, Collectors.mapping(ObjectError::getDefaultMessage, Collectors.toList())));

        return new ResponseEntity<JsonResult>(JsonResult.fail(messages), HttpStatus.OK);
    }

    @ExceptionHandler(UserLockedException.class)
    public ResponseEntity<JsonResult> handleBusinessException(UserLockedException e) {
//        log.info("账号（Userid:" + e.getLockedUser().getId() + "）被锁定。锁定类型:" + e.getLockedUser().getStatus());
        return new ResponseEntity<JsonResult>(JsonResult.fail(), HttpStatus.LOCKED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResult> handleException(Exception e) {
        log.error("予期しない例外が発生しました。内容を確認してください。", e);
        return new ResponseEntity<JsonResult>(JsonResult.fail(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<JsonResult> handleException(BusinessException e) {
        log.warn("業務例外が発生しました。内容を確認して対応をしてください。", e);
        return new ResponseEntity<JsonResult>(JsonResult.fail(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppBusinessException.class)
    public ResponseEntity<JsonResult> handleBusinessException(AppBusinessException e) {
        return new ResponseEntity<JsonResult>(JsonResult.fail(e.getMsgHash()), HttpStatus.OK);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<JsonResult> handleSystemException(SystemException e) {
        log.error("システム例外が発生しました。内容を確認して対応をしてください。", e);
        return new ResponseEntity<JsonResult>(JsonResult.fail(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}