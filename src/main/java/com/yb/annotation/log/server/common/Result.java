package com.yb.annotation.log.server.common;

import lombok.Data;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Data
public class Result<T> {
    private static final String MESSAGE_SUCCESS = "success";
    private static final Integer STATUS_SUCCESS = 200;
    private static final Integer STATUS_ERROR = 400;

    private Integer status;
    private String message;
    private T data;

    /**
     * 供接口返回数据使用
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<>();
        result.setStatus(STATUS_SUCCESS);
        result.setMessage(MESSAGE_SUCCESS);
        result.setData(t);
        return result;
    }

    /**
     * 这个一般用不着,因为基本都是用异常来捕捉的
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setStatus(STATUS_ERROR);
        result.setMessage(message);
        return result;
    }

    /**
     * 这个主要用来在异常捕捉类里简化返回数据的封装
     * @param status
     * @param <T>
     * @return
     */
    public static <T> Result<T> withStatus(Integer status) {
        Result<T> result = new Result<>();
        result.setStatus(status);
        return result;
    }

    /**
     * 这个也是主要用来在异常捕捉类里简化返回数据的封装
     * @param message
     * @param <T>
     * @return
     */
    public <T> Result<T> withMessage(String message) {
        Result<T> result = new Result<>();
        //取出调用第一个方法返回值里的status
        Integer status = this.getStatus();
        result.setStatus(status);
        result.setMessage(message);
        return result;
    }
}
