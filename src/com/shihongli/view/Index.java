package com.shihongli.view;/*
    @author shl
    @create 2020-05-19-21:36
*/

/**
 * 程序执行的main函数
 */
public class Index {

    public static Login login;

    public static void main(String[] args) {
        new Index();
    }

    public Index() {
       //新建登录页面
        login = new Login();
    }

    public static Login getLogin() {
        return login;
    }
}