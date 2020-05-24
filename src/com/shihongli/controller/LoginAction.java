package com.shihongli.controller;/*
    @author shl
    @create 2020-05-18-19:48
*/

import com.shihongli.dao.AdminDaoImpl;
import com.shihongli.dao.UserDaoImpl;
import com.shihongli.model.Admin;
import com.shihongli.model.User;

public class LoginAction {

    private static LoginAction loginAction;
    private UserDaoImpl userDao;
    private AdminDaoImpl admDao;

    static {
        loginAction = new LoginAction();
    }

    public static LoginAction getInstance() {
        return loginAction;
    }

    private LoginAction() {
        userDao = UserDaoImpl.getInstance();
        admDao = AdminDaoImpl.getInstance();
    }

    public User userLogin(int id, String password) {
        User u = userDao.queryUser(id, password);
        return u;
    }

    public Admin adminLogin(int id, String password) {
        return admDao.queryAdmin(id, password);
    }

}
