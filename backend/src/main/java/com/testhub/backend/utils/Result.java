package com.testhub.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(null);
        return result;
    }

    /**
     * 成功，带数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    /**
     * 成功，带消息和数据
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<T>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static <T> Result<T> fail() {
        Result<T> result = new Result<T>();
        result.setCode(500);
        result.setMessage("操作失败");
        result.setData(null);
        return result;
    }

    /**
     * 失败，带消息
     */
    public static <T> Result<T> fail(String message) {
        Result<T> result = new Result<T>();
        result.setCode(500);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 失败，带状态码和消息
     */
    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 参数错误
     */
    public static <T> Result<T> paramError() {
        Result<T> result = new Result<T>();
        result.setCode(400);
        result.setMessage("参数错误");
        result.setData(null);
        return result;
    }

    /**
     * 参数错误，带消息
     */
    public static <T> Result<T> paramError(String message) {
        Result<T> result = new Result<T>();
        result.setCode(400);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 未授权
     */
    public static <T> Result<T> unauthorized() {
        Result<T> result = new Result<T>();
        result.setCode(401);
        result.setMessage("未授权");
        result.setData(null);
        return result;
    }

    /**
     * 未授权，带消息
     */
    public static <T> Result<T> unauthorized(String message) {
        Result<T> result = new Result<T>();
        result.setCode(401);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 禁止访问
     */
    public static <T> Result<T> forbidden() {
        Result<T> result = new Result<T>();
        result.setCode(403);
        result.setMessage("禁止访问");
        result.setData(null);
        return result;
    }

    /**
     * 禁止访问，带消息
     */
    public static <T> Result<T> forbidden(String message) {
        Result<T> result = new Result<T>();
        result.setCode(403);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

    /**
     * 资源不存在
     */
    public static <T> Result<T> notFound() {
        Result<T> result = new Result<T>();
        result.setCode(404);
        result.setMessage("资源不存在");
        result.setData(null);
        return result;
    }

    /**
     * 资源不存在，带消息
     */
    public static <T> Result<T> notFound(String message) {
        Result<T> result = new Result<T>();
        result.setCode(404);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
} 