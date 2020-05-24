package com.shihongli.dao;/*
    @author shl
    @create 2020-05-18-17:02
*/

import java.util.*;
import java.sql.*;

import com.shihongli.model.*;
import com.shihongli.util.JDBCUtils;


public class UserDaoImpl {

    private static UserDaoImpl userDaoImpl;

    static {
        userDaoImpl = new UserDaoImpl();
    }

    private UserDaoImpl() {
    }

    //返回实例
    public static UserDaoImpl getInstance() {
        return userDaoImpl;
    }

    /**
     * 增加用户的操作
     *
     * @param user 传入用户对象
     * @return 成功添加返回true否则返回false
     */
    public boolean insertUser(User user) {
        String sql = "insert into user(id,name,password)values(?,?,?)";
        //获得数据库连接
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, user.getId());
            psts.setObject(2, user.getName());
            psts.setObject(3, user.getPassword());
            //执行 ，返回值为int
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
                conn.close();
                System.out.println("数据库连接关闭");
                //关闭预编译
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据用户名与密码查找用户
     *
     * @param id       用户名
     * @param password 密码
     * @return 查询到了返回User对象，否则返回null
     */
    public User queryUser(int id, String password) {
        String sql = "select * from user where id = ?";
        if (password != null) sql += " and password = ?";
        Connection conn = JDBCUtils.getConnection();
        //创建一个结果集
        ResultSet rs = null;
        User user = null;
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, id);
            if (password != null) {
                psts.setObject(2, password);
            }
            //执行
            rs = psts.executeQuery();
            while (rs.next()) {
                int return_id = rs.getInt("id");
                String name = rs.getString("name");
                String return_password = rs.getString("password");
                user = new User(return_id, name, return_password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
                conn.close();
                System.out.println("数据库连接关闭");
                //关闭预编译
                psts.close();
                //关闭结果集
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * 遍历所有的用户
     *
     * @return 返回list集合
     */
    public List<User> queryAllUser() {
        String sql = "select * from user";
        Connection conn = JDBCUtils.getConnection();
        //创建一个结果集
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            //执行
            rs = psts.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                User user = new User(id, name, password);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                System.out.println("数据库连接关闭");
                psts.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 根据用户名进行删除
     *
     * @param id 用户名
     * @return 删除返回true，否则返回false
     */
    public boolean deleteUser(int id) {
        String sql = "delete from user where id=" + id;
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                System.out.println("数据库连接关闭");
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据用户名修改姓名
     *
     * @param id   传入用户名
     * @param name 要修改的新名字
     * @return 若完成修改返回true否则返回false
     */
    public boolean updateUserName(int id, String name) {
        String sql = "update user set name=? where id=" + id;
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, name);
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                System.out.println("数据库连接关闭");
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 修改用户的密码
     *
     * @param id       用户名
     * @param password 旧密码
     * @param str      新密码
     * @return 成功修改返回true否则返回false
     */
    public boolean updateUserPassword(int id, String password, String str) {
        String sql = "update " + str + " set password=? where id=" + id;
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, password);
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                System.out.println("数据库连接关闭");
                psts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
