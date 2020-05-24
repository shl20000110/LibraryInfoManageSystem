package com.shihongli.util;/*
    @author shl
    @create 2020-05-18-18:13
*/

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBCUtils工具类实现获取数据库连接,使用db_properties配置文件配置
 */
public class JDBCUtils {
    /**
     * 方法一：
     */
    private static String DRIVER = "";
    private static String URL = "";
    private static String user = "";
    private static String password = "";

    static {
        //1 创建properties对象
        Properties properties = new Properties();
        //2 将.properties文件读取成io流
        InputStream inputStream = JDBCUtils.class.getClassLoader().
                getResourceAsStream("db_book.properties");

        try {
            //加载.properties文件中的配置
            properties.load(inputStream);
            DRIVER = properties.getProperty("driver");
            URL = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            //加载mysql驱动
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 获得数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //创建连接
            conn = DriverManager.getConnection(URL, user, password);
            //用于测试数据库是否成功连接
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
/**
 * 方法二:
 */
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://localhost:3306/db_book";
//    /**
//     * 本地MySQL数据库为5.7.28版本，驱动为8.0.13版本 必须要加上serverTimeZone 否则
//     * 数据库无法取得连接
//     */
//    private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_book?"
//            + "useUnicode = true & serverTimezone = GMT"
//            // MySQL在高版本还需要指明是否进行SSL连接
//            + "& characterEncoding = utf8 & useSSL = false";
//    private static  String user = "root";
//    private static  String password = "12345";

//    public static Connection getConnection() {
//        //获取数据库连接的操作
//        Class.forName("com.mysql.jdbc.Driver");
//        //数据库连接地址
//        String url = "jdbc:mysql://localhost:3306/db_book";//db_book数据库
//        String user = "root";
//        String password = "12345";
//        Connection conn = DriverManager.getConnection(url, user, password);
//        System.out.println("数据库连接成功");
//        return conn;
//    }



