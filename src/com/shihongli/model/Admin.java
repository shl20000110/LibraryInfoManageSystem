package com.shihongli.model;/*
    @author shl
    @create 2020-05-18-14:57
*/

//管理员类
public class Admin {
    //管理员编号
    private int id;
    //管理员密码
    private String password;

    public Admin() {
    }

    public Admin(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
