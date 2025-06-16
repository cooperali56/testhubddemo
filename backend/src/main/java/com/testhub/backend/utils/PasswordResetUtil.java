package com.testhub.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码重置工具类
 */
public class PasswordResetUtil {
    
    public static void main(String[] args) {
        // 使用BCryptPasswordEncoder加密密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // 需要加密的admin密码
        String adminPassword = "admin123";
        
        // 加密
        String encodedAdminPassword = encoder.encode(adminPassword);
        
        // 打印加密后的密码
        System.out.println("原始admin密码: " + adminPassword);
        System.out.println("加密后的admin密码: " + encodedAdminPassword);
        
        // 需要加密的tester密码
        String testerPassword = "test123";
        
        // 加密
        String encodedTesterPassword = encoder.encode(testerPassword);
        
        // 打印加密后的密码
        System.out.println("\n原始tester密码: " + testerPassword);
        System.out.println("加密后的tester密码: " + encodedTesterPassword);
        
        // 生成SQL语句
        System.out.println("\n-- 重置admin用户的密码SQL:");
        System.out.println("UPDATE tb_user SET password = '" + encodedAdminPassword + "' WHERE username = 'admin';");
        
        System.out.println("\n-- 重置tester用户的密码SQL:");
        System.out.println("UPDATE tb_user SET password = '" + encodedTesterPassword + "' WHERE username = 'tester';");
    }
} 