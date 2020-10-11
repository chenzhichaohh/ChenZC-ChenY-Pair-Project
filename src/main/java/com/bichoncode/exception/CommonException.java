package com.bichoncode.exception;

/**
 * 通用异常处理类
 * @author ChenYan
 */
public class CommonException extends RuntimeException {
    private String message;

    public CommonException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
