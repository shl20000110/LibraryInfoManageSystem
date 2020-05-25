package com.shihongli.controller;/*
    @author shl
    @create 2020-05-18-20:54
*/

import com.shihongli.util.ShowMessageUtils;
import com.shihongli.dao.UserDaoImpl;
import com.shihongli.model.User;

import java.util.List;

/**
 * 单例模式，只有一个UserAction实例
 * 节约内存，提高效率
 */
public class UserAction {

    private static UserAction userAction;

    static {
        userAction = new UserAction();
    }

    public static UserAction getInstance() {
        return userAction;
    }

    private UserDaoImpl userDao;

    private UserAction() {
        userDao = UserDaoImpl.getInstance();
    }

    /**
     * 添加用户的方法
     *
     * @param id       用户名
     * @param name     姓名
     * @param password 密码
     * @return 注册成功返回true，否则返回false
     */
    public boolean addUser(int id, String name, String password) {
        User user = new User(id, name, password);
        if (userDao.insertUser(user)) {
            ShowMessageUtils.winMessage("注册成功！");
            return true;
        } else {
            ShowMessageUtils.winMessage("注册失败，该账号已存在！");
            return false;
        }
    }

    /**
     * 查询所有的用户
     *
     * @return 返回用户数组
     */
    public Object[][] queryAllUser() {
        List<User> list = userDao.queryAllUser();
        Object[][] obj = new Object[list.size()][4];
        int i = 0;
        for (User user : list) {
            obj[i][0] = user.getId();
            obj[i][1] = user.getName();
            obj[i][2] = user.getPassword();
            i++;
        }
        return obj;
    }
}