package com.saint.contentcenter.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 周鑫(玖枭)
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {
    @ExceptionHandler({java.lang.SecurityException.class})
    public ResponseEntity<ErrorBody> error(java.lang.SecurityException e) {
        log.warn("发生SecurityException异常，", e);
        ResponseEntity<ErrorBody> responseEntity = new ResponseEntity<>(
                ErrorBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(), HttpStatus.UNAUTHORIZED);

        return responseEntity;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorBody {
    public String body;

    public Integer status;
}
