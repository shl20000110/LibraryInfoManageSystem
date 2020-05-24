package com.shihongli.model;/*
    @author shl
    @create 2020-05-18-15:03
*/

//图书类
public class Book {
    //书号
    private int id;
    //书名
    private String name;
    //书价格
    private double price;
    //书的总量
    private int count;
    //类型
    private String type;
    //作者
    private String author;
    //借出次数
    private int discount;
    //已借出的数量
    private int hasLended;
    //藏书地址
    private String address;

    public Book() {

    }

    public Book(int id, String name, double price, int count, String type,
                String author, int discount, int hasLended, String address) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.type = type;
        this.author = author;
        this.discount = discount;
        this.hasLended = hasLended;
        this.address = address;
    }

    public Book(String name,double price, int count, String type, String author, String address) {
        this(0, name,price , count, type, author, 0, 0, address);
    }

    public String getAddress() { return address; }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getHasLended() {
        return hasLended;
    }

    public void setHasLended(int hasLended) {
        this.hasLended = hasLended;
    }

    public String toString() {
        return "Books [id:" + id + " name:" + name + " count:" + count + " type:" + type + " author:" + author
                + " discount:" + discount + " hasLended:" + hasLended + " address:" + address + "]";
    }

}
