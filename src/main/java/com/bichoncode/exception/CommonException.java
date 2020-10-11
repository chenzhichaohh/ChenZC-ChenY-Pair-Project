package com.bichoncode.exception;

/**
 * 通用异常处理类
 * @author BichonCode
 * @mail chenzhichaohh@163.com
 * @create 2020/10/10
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
