package com.shihongli.dao;/*
    @author shl
    @create 2020-05-18-18:37
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shihongli.model.Book;
import com.shihongli.util.JDBCUtils;

public class BookDaoImpl {

    private static BookDaoImpl bookDao;

    static {
        bookDao = new BookDaoImpl();
    }

    private BookDaoImpl() { }

    public static BookDaoImpl getInstance() {
        return bookDao;
    }

    /**
     * 增加图书
     *
     * @param name    书名
     * @param count   数量
     * @param type    类型
     * @param author  作者
     * @param address 地址
     * @return 成功添加返回true否则返回false
     */
    public boolean insertBook(String name, double price, int count, String type, String author, String address) {

        String sql = "insert into " +
                "book(name, price, count, type, author, discount, hasLended, address)" +
                "values(?,?,?,?,?,?,?,?)";
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, name);
            psts.setObject(2, price);
            psts.setObject(3, count);
            psts.setObject(4, type);
            psts.setObject(5, author);
            psts.setObject(6, 0);
            psts.setObject(7, 0);
            psts.setObject(8, address);
            //打印SQL语句到屏幕上
            System.out.println(sql);
            //执行,返回值为int
            return psts.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //连接关闭
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
     * 遍历所有书籍
     *
     * @return 返回图书集合
     */
    public List<Book> queryAllBooks() {
        String sql = "select * from book";
        Connection conn = JDBCUtils.getConnection();
        ResultSet rs = null;
        //使用集合存储图书
        List<Book> list = new ArrayList<Book>();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery(); // 执行
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int count = rs.getInt("count");
                String type = rs.getString("type");
                String author = rs.getString("author");
                int discount = rs.getInt("discount");
                int hasLended = rs.getInt("hasLended");
                String address = rs.getString("address");
                Book book = new Book(id, name, price, count, type, author, discount, hasLended, address);
                list.add(book);
            }
            //打印SQL语句到屏幕上
            System.out.println(sql);
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
     * 根据名称删除某一本书
     *
     * @param name 书名
     * @return 成功删除返回true否则返回false
     */
    public boolean deleteBook(String name) {
        String sql = "delete from book where name = '" + name + "'";
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
            //打印SQL语句到屏幕上
            System.out.println(sql);
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
     * 更新某本书的信息
     *
     * @param book 图书对象
     * @param conn 数据库连接
     * @return 成功更新返回true否则返回false
     */
    public boolean updateBook(Book book, Connection conn) {
        boolean isRelease = false;
        String sql = "update book set ";
        //可以改进
        if (book.getPrice() != 0) {
            sql += "price=" + book.getPrice() + ",";
        }
        if (book.getCount() != 0) {
            sql += "count=" + book.getCount() + ",";
        }
        if (book.getDiscount() != 0) {
            sql += "discount=" + book.getDiscount() + ",";
        }
        if (book.getHasLended() != 0) {
            sql += "hasLended=" + book.getHasLended() + ",";
        }
        if (book.getType() != null) {
            sql += "type='" + book.getType() + "',";
        }
        if (book.getAddress() != null) {
            sql += "address='" + book.getAddress() + "' ,";
        }
        if (book.getAuthor() != null) {
            sql += "author='" + book.getAuthor() + "' ,";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where name='" + book.getName() + "'";

        //打印SQL语句到屏幕上
        System.out.println(sql);

        if (conn == null) {
            conn = JDBCUtils.getConnection();
            isRelease = true;
        }
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
                if (isRelease && conn != null) {
                    conn.close();
                    System.out.println("数据库连接关闭");
                }
                if (psts != null) {
                    psts.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据书名查找某本书
     *
     * @param name 书名
     * @return 成功找到返回book对象否则返回null
     */
    public Book searchBook(String name) {
        String sql = "select * from book where name = '" + name + "'";
        Connection conn = JDBCUtils.getConnection();
        ResultSet rs = null;
        Book book = new Book();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            //执行查询操作
            rs = psts.executeQuery();
            if (rs.next()) {
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setPrice(rs.getDouble("price"));
                book.setCount(rs.getInt("count"));
                book.setType(rs.getString("type"));
                book.setAuthor(rs.getString("author"));
                book.setDiscount(rs.getInt("discount"));
                book.setHasLended(rs.getInt("hasLended"));
                book.setAddress(rs.getString("address"));
            }
            //打印SQL语句到屏幕上
            System.out.println(sql);
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
        return book;
    }

    /**
     * 根据某本书的编号查询
     *
     * @param bId 图书号
     * @return 成功找到返回book对象，否则返回null
     */
    public Book queryBook(int bId) {
        String sql = "select * from book where id = " + bId;
        Connection conn = JDBCUtils.getConnection();
        ResultSet rs = null;
        Book book = new Book();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery(); // 执行
            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setPrice(rs.getDouble("price"));
            book.setCount(rs.getInt("count"));
            book.setType(rs.getString("type"));
            book.setAuthor(rs.getString("author"));
            book.setDiscount(rs.getInt("discount"));
            book.setHasLended(rs.getInt("hasLended"));
            book.setAddress(rs.getString("address"));
            //打印SQL语句到屏幕上
            System.out.println(sql);
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
        return book;
    }
}
