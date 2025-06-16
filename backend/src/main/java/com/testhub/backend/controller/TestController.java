package com.testhub.backend.controller;

import com.testhub.backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试接口
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, TestHub!";
    }
    
    /**
     * 前后端连接测试接口
     */
    @GetMapping
    public Result<Map<String, Object>> test() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "后端API连接成功");
        data.put("timestamp", System.currentTimeMillis());
        data.put("status", "ok");
        return Result.success("测试成功", data);
    }
}
