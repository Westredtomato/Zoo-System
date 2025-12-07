package org.example.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;

    private DatabaseConnection() {
        loadProperties();
        connect();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        // 从database.properties读取配置
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/database.properties")) {

            if (input == null) {
                throw new IOException("无法找到database.properties配置文件");
            }

            Properties prop = new Properties();
            prop.load(input);

            // 读取配置
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
            String driver = prop.getProperty("db.driver");

            if (url == null || username == null || password == null) {
                throw new IllegalArgumentException("database.properties中缺少必要的数据库配置");
            }

            // 注册驱动
            try {
                Class.forName(driver != null ? driver : "org.postgresql.Driver");
                System.out.println("成功加载数据库驱动: " + (driver != null ? driver : "org.postgresql.Driver"));
            } catch (ClassNotFoundException e) {
                System.err.println("无法加载数据库驱动: " + e.getMessage());
            }

            System.out.println("数据库配置加载成功:");
            System.out.println("URL: " + url);
            System.out.println("Username: " + username);
            System.out.println("Schema: " + extractSchemaFromURL(url));

        } catch (IOException e) {
            System.err.println("加载配置文件失败: " + e.getMessage());
            // 使用硬编码的配置作为备用
            useFallbackConfig();
        }
    }

    private String extractSchemaFromURL(String url) {
        if (url != null && url.contains("currentSchema=")) {
            String[] parts = url.split("currentSchema=");
            if (parts.length > 1) {
                return parts[1].split("&")[0];
            }
        }
        return "default";
    }

    private void useFallbackConfig() {
        // 使用你的硬编码配置
        url = "jdbc:postgresql://192.168.161.18:5432/zoosystem?currentSchema=manage";
        username = "opengauss";
        password = "openGauss@123";
        System.out.println("使用备用配置连接到数据库...");
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ 成功连接到数据库!");
            System.out.println("数据库产品: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("数据库版本: " + connection.getMetaData().getDatabaseProductVersion());

            // 测试连接有效性
            if (connection.isValid(5)) {
                System.out.println("✅ 数据库连接有效");
            }

        } catch (SQLException e) {
            System.err.println("❌ 数据库连接失败!");
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("SQL状态: " + e.getSQLState());
            System.err.println("错误代码: " + e.getErrorCode());

            // 提供调试信息
            System.err.println("连接URL: " + url);
            System.err.println("用户名: " + username);

            e.printStackTrace();

            // 尝试使用简化连接
            trySimpleConnection();
        }
    }

    private void trySimpleConnection() {
        try {
            // 尝试不使用schema的连接
            String simpleUrl = "jdbc:postgresql://192.168.161.18:5432/zoosystem";
            connection = DriverManager.getConnection(simpleUrl, username, password);
            System.out.println("✅ 使用简化URL连接成功!");

            // 设置schema
            try (var stmt = connection.createStatement()) {
                stmt.execute("SET search_path TO manage");
                System.out.println("✅ 成功设置schema为: manage");
            }

        } catch (SQLException e) {
            System.err.println("❌ 简化连接也失败: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("连接已关闭，重新连接...");
                connect();
            }
        } catch (SQLException e) {
            System.err.println("获取连接时出错: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("数据库连接已关闭!");
            }
        } catch (SQLException e) {
            System.err.println("关闭连接时出错: " + e.getMessage());
        }
    }

    // 测试连接的静态方法
    public static boolean testConnection() {
        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection conn = db.getConnection();

            if (conn != null && !conn.isClosed() && conn.isValid(5)) {
                System.out.println("=========================================");
                System.out.println("✅ 数据库连接测试成功!");
                System.out.println("数据库: " + conn.getMetaData().getDatabaseProductName());
                System.out.println("版本: " + conn.getMetaData().getDatabaseProductVersion());
                System.out.println("URL: " + db.url);
                System.out.println("用户: " + db.username);
                System.out.println("Schema: " + db.extractSchemaFromURL(db.url));
                System.out.println("=========================================");
                return true;
            }
        } catch (Exception e) {
            System.err.println("=========================================");
            System.err.println("❌ 数据库连接测试失败!");
            System.err.println("错误: " + e.getMessage());
            System.err.println("=========================================");
        }
        return false;
    }

    // 执行简单查询测试连接
    public static void testQuery() {
        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            Connection conn = db.getConnection();

            System.out.println("执行测试查询...");

            // 尝试查询版本
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT version()")) {
                if (rs.next()) {
                    System.out.println("数据库版本信息: " + rs.getString(1));
                }
            }

            // 尝试查询当前schema
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT current_schema()")) {
                if (rs.next()) {
                    System.out.println("当前schema: " + rs.getString(1));
                }
            }

            // 尝试列出所有表（如果有权限）
            try {
                var metaData = conn.getMetaData();
                try (var rs = metaData.getTables(null, "manage", "%", new String[]{"TABLE"})) {
                    System.out.println("manage schema中的表:");
                    int count = 0;
                    while (rs.next()) {
                        System.out.println("  - " + rs.getString("TABLE_NAME"));
                        count++;
                    }
                    System.out.println("共找到 " + count + " 个表");
                }
            } catch (SQLException e) {
                System.out.println("无法获取表信息（权限问题）: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("测试查询失败: " + e.getMessage());
        }
    }
}