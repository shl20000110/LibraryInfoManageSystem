package com.shihongli.dao;/*
    @author shl
    @create 2020-05-18-17:12
*/

import com.shihongli.model.Record;
import com.shihongli.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl {

    private static RecordDaoImpl recordDao;

    static {
        recordDao = new RecordDaoImpl();
    }

    private RecordDaoImpl() {
    }

    public static RecordDaoImpl getInstance() {
        return recordDao;
    }

    /**
     * 向借还书信息表中添加记录
     *
     * @param record 记录
     * @param conn   数据库连接
     * @return 成功添加返回true否则返回false
     */
    public boolean insertRecord(Record record, Connection conn) {
        boolean isRelease = false;
        String sql = "insert into record(uid,bid,lendTime,returnTime)values(?,?,?,?)";
        if (conn == null) {
            conn = JDBCUtils.getConnection();
            isRelease = true;
        }
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, record.getUid());
            psts.setObject(2, record.getBid());
            psts.setObject(3, record.getLendTime());
            psts.setObject(4, record.getReturnTime());
            //执行，返回值为int类型
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
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
     * 修改还书记录 还书的时间
     *
     * @param uid        用户名
     * @param bid        书号
     * @param returnTime 还书时间
     * @param conn       数据库连接
     * @return 成功修改返回true否则返回false
     */
    public boolean updateRecord(int uid, int bid, String returnTime, Connection conn) {
        boolean isRelease = false;
        String sql = "update record set returnTime=? where uid=? and bid=" + bid;
        if (conn == null) {
            conn = JDBCUtils.getConnection();
            isRelease = true;
        }
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            psts.setObject(1, returnTime);
            psts.setObject(2, uid);
            int res = psts.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
                if (isRelease && conn != null) {
                    conn.close();
                    System.out.println("数据库连接关闭");
                }
                //关闭预编译
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
     * 根据用户名获得借还书的记录表
     *
     * @param uid 用户名
     * @return 成功获得返回对象数组否则返回null
     */
    public Object[][] getRecordsByUid(int uid) {
        String sql = "select record.* , name  from Record, Book " + "where Record.bid=Book.id and uid=" + uid;
        Connection conn = JDBCUtils.getConnection();
        ResultSet rs = null;
        List<Record> list = new ArrayList<Record>();
        PreparedStatement psts = null;
        try {
            psts = conn.prepareStatement(sql);
            rs = psts.executeQuery();// 执行
            while (rs.next()) {
                String name = rs.getString("name");
                String lendTime = rs.getString("lendTime");
                String returnTime = rs.getString("returnTime");
                Record rd = new Record(lendTime, returnTime);
                rd.setName(name);
                list.add(rd);
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
        Object[][] obj = new Object[list.size()][4];
        int i = 0;
        for (Record re : list) {
            obj[i][0] = i + 1;
            obj[i][1] = re.getName();
            obj[i][2] = re.getLendTime();
            obj[i][3] = re.getReturnTime();
            i++;
        }
        return obj;
    }
}
