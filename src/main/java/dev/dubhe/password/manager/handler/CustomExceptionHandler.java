package dev.dubhe.password.manager.handler;


import dev.dubhe.password.manager.data.ResponseResult;
import dev.dubhe.password.manager.exception.CustomException;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseResult handle(@Nonnull CustomException exception) {
        return ResponseResult.msg(exception.getCode(), exception.getMessage());
    }
}
