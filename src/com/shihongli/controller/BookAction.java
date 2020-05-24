package com.shihongli.controller;/*
    @author shl
    @create 2020-05-18-23:07
*/

import com.shihongli.dao.BookDaoImpl;
import com.shihongli.dao.RecordDaoImpl;
import com.shihongli.model.Book;
import com.shihongli.model.Record;
import com.shihongli.util.DateUtils;
import com.shihongli.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookAction {

    //初始化
    private static BookAction bookAction;

    static {
        bookAction = new BookAction();
    }

    private BookDaoImpl bookDao;
    private RecordDaoImpl recordDao;

    private BookAction() {
        bookDao = BookDaoImpl.getInstance();
        recordDao = RecordDaoImpl.getInstance();
    }

    public static BookAction getInstance() {
        return bookAction;
    }

    //定义状态常量
    public static final String CAN = "可借";
    public static final String CANT = "无库存";

    /**
     * 借书的操作
     *
     * @param uid  用户名
     * @param name 用户姓名
     * @return
     */
    public boolean lendBook(int uid, String name) {
        Book bk = bookDao.searchBook(name);
        //已借出数量等于图书总数量，则不可借
        if (bk.getCount() == bk.getHasLended()) {
            return false;
        }
        //借书
        Record rd = new Record(uid, bk.getId(), DateUtils.getDate(), "未还");
        //借出的数量加1
        bk.setHasLended(bk.getHasLended() + 1);
        //借出书的次数加1
        bk.setDiscount(bk.getDiscount());
        //获取连接
        Connection conn = JDBCUtils.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        boolean res = bookDao.updateBook(bk, conn);
        if (!res) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        res = recordDao.insertRecord(rd, conn);
        if (!res) {
            try {
                conn.rollback();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } else {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    /**
     * 还书的操作
     *
     * @param uid  用户名
     * @param name 用户姓名
     * @return
     */
    public boolean returnBook(int uid, String name) {
        //根据书名找到要还的书
        Book bk = bookDao.searchBook(name);
        //如果书号不存在 <=0的情况下则不能还书
        if (bk.getId() <= 0) {
            return false;
        }
        //借出的书的数量减1
        bk.setHasLended(bk.getHasLended() - 1);
        //获取连接
        Connection conn = JDBCUtils.getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        boolean res = recordDao.updateRecord(uid, bk.getId(), DateUtils.getDate(), conn);
        if (!res) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        res = bookDao.updateBook(bk, conn);
        if (!res) {
            try {
                conn.rollback();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } else {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    /**
     * 查询所有的书
     *
     * @return 返回二维数组
     */
    public Object[][] queryAllBooks() {
        List<Book> list = bookDao.queryAllBooks();
        Object[][] obj = new Object[list.size()][8];
        int i = 0;
        for (Book book : list) {
            //如果借出的数量小于书的总量 则可以借出 否则不能借出
            obj[i][0] = book.getHasLended() < book.getCount() ? CAN : CANT;
            obj[i][1] = book.getName();
            obj[i][2] = book.getPrice();
            obj[i][3] = book.getCount() - book.getHasLended();
            obj[i][4] = book.getType();
            obj[i][5] = book.getAuthor();
            obj[i][6] = book.getDiscount();
            obj[i][7] = book.getAddress();
            i++;
        }
        return obj;
    }

    /**
     * 查找书
     *
     * @return 返回对象数组
     */
    public Object[][] queryBooks() {
        List<Book> list = bookDao.queryAllBooks();
        Object[][] obj = new Object[list.size()][8];
        int i = 0;
        for (Book book : list) {
            obj[i][0] = book.getId();
            obj[i][1] = book.getName();
            obj[i][2] = book.getPrice();
            obj[i][3] = book.getCount() - book.getHasLended();
            obj[i][4] = book.getType();
            obj[i][5] = book.getAuthor();
            obj[i][6] = book.getDiscount();
            obj[i][7] = book.getAddress();
            i++;
        }
        return obj;
    }

    /**
     * 增加书
     *
     * @param name    书名
     * @param price   价格
     * @param count   总数
     * @param type    类型
     * @param author  作者
     * @param address 藏书地点
     * @return
     */
    public boolean insertBook(String name, double price, int count, String type, String author, String address) throws Exception {
        if (bookDao.searchBook(name).getId() > 0) {
            return false;
        }
        return bookDao.insertBook(name, price, count, type, author, address);
    }


}
