package com.shihongli.dao;/*
    @author shl
    @create 2020-05-18-18:38
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shihongli.model.*;
import com.shihongli.util.JDBCUtils;

public class AdminDaoImpl {

    private static AdminDaoImpl adminDaoImpl;

    static {
        adminDaoImpl = new AdminDaoImpl();
    }

    private AdminDaoImpl() {
    }

    public static AdminDaoImpl getInstance() {
        return adminDaoImpl;
    }

    /**
     * 查询管理员信息
     *
     * @param id       管理员账号
     * @param password 密码
     * @return
     */
    public Admin queryAdmin(int id, String password) {
        String sql = "select * from administrator where id = ? and password = ?";
        Connection conn = JDBCUtils.getConnection();
        //创建一个结果集
        ResultSet rs = null;
        Admin admin = null;

        PreparedStatement psts = null;
        try {
            //对sql语句进行预处理
            psts = conn.prepareStatement(sql);
            //传入参数
            psts.setObject(1, id);
            psts.setObject(2, password);
            //执行查找操作
            rs = psts.executeQuery();
            while (rs.next()) {
                int return_id = rs.getInt("id");
                String return_password = rs.getString("password");
                admin = new Admin(return_id, return_password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                //用于测试数据库是否关闭
                System.out.println("数据库连接关闭");
                psts.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admin;
    }
}
