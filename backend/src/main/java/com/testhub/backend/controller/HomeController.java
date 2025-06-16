package com.testhub.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 主页控制器
 */
@RestController
public class HomeController {

    /**
     * 首页
     */
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Welcome to TestHub API");
        result.put("status", "running");
        result.put("version", "1.0.0");
        return result;
    }
} 