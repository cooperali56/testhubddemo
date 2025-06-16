package com.testhub.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        try {
            // 加载MySQL JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 设置JDBC连接URL
            String url = "jdbc:mysql://localhost:3306/testhub?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useAffectedRows=true";
            String username = "testhub";
            String password = "123456";
            
            // 建立连接
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接成功!");
            
            // 执行查询
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tb_user");
            
            // 处理结果
            if (rs.next()) {
                System.out.println("用户表中的记录数: " + rs.getInt(1));
            }
            
            // 关闭资源
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("连接数据库时发生异常: ");
            e.printStackTrace();
        }
    }
} 