package com.shihongli.view;/*
    @author shl
    @create 2020-05-19-12:06
*/

import com.shihongli.controller.BookAction;
import com.shihongli.dao.BookDaoImpl;
import com.shihongli.dao.RecordDaoImpl;
import com.shihongli.dao.UserDaoImpl;
import com.shihongli.model.Book;
import com.shihongli.model.User;
import com.shihongli.util.ShowMessageUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//普通学生用户登录界面
public class UserUi extends JFrame implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    public static final int CARD0 = 0;
    public static final int CARD1 = 1;
    public static final int CARD2 = 2;
    public static final int CARD3 = 3;
    public static final int CARD4 = 4;
    public static final int CARD5 = 5;

    private JLabel card0 = new JLabel();
    private JPanel card1 = new JPanel();
    private JLabel card2 = new JLabel();
    private JPanel card3 = new JPanel();
    private JLabel card4 = new JLabel();

    private UserDaoImpl userDao;
    private BookDaoImpl bookDao;
    private RecordDaoImpl recordDao;
    private BookAction bookAction;

    private int uId;
    private Integer bid = null;
    //默认工具类
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    //屏幕尺寸
    private Dimension sc = toolkit.getScreenSize();
    //点击栏位置
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.NORTH);
    private JLabel bookname = new JLabel("书 名");
    private JButton btnSchB = new JButton("搜 索");
    private JTextField textName = new JTextField();
    String[] recordTableHead =
            {"序号", "书名", "借书时间", "还书时间"};
    String[] bookTableHead =
            {"状态", "书名", "价格", "剩余库存", "类型", "作者", "借阅总次", "所在位置"};
    //组件
    private JLabel uName = new JLabel("用户名");
    private JTextField textUname = new JTextField();
    private JLabel oldPassWord = new JLabel("旧密码");
    private JPasswordField textoldPassWord = new JPasswordField();
    private JLabel newPassWord = new JLabel("新密码");
    private JPasswordField textnewPassWord = new JPasswordField();
    private JButton btnYes = new JButton("确 定");
    private JLabel myinfo = new JLabel("");
    private Font font = new Font("微软雅黑", 0, 20);
    private Font font1 = new Font("微软雅黑", 0, 16);
    private JButton reFresh = new JButton("刷新");
    private JButton btnexit = new JButton("退出");
    //菜单栏
    private JMenuBar menuBar = new JMenuBar();

    /**
     * 初始化
     */
    private void init() {
        bookDao = BookDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
        recordDao = RecordDaoImpl.getInstance();
        bookAction = BookAction.getInstance();
    }

    /**
     * 初始化面板
     */
    private void initPanel() {
        setTitle("图书信息管理系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 600) / 2, 900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        tabbedPane.setFont(font);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(this);
        reFresh.addActionListener(this);
        btnexit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnexit);
        getContentPane().add(tabbedPane);
    }

    public UserUi(int id, int defaultCard) {
        uId = id;
        init();
        initPanel();
        //card0 仅用于放欢迎词，将card0设为不可点击
        tabbedPane.addTab("欢迎用户登录使用", card0);
        tabbedPane.addTab("所有图书信息", card1);
        tabbedPane.addTab("查询图书信息", card2);
        tabbedPane.addTab("借还图书记录", card3);
        tabbedPane.addTab("个人账号信息", card4);
        //默认第一页
        tabbedPane.setSelectedIndex(defaultCard);
        //设置第0页的面板不可用
        tabbedPane.setEnabledAt(CARD0, false);
        addListener();
    }

    private void addListener() {
        btnSchB.addActionListener(this);
        btnYes.addActionListener(this);
    }

    /**
     * 初始化所有图书信息，传入数据与表头
     */
    private void initCard1() {
        card1.removeAll();
        JTable bookTable = new JTable(bookAction.queryAllBooks(), bookTableHead);
        bookTable.setFont(font);
        bookTable.setPreferredSize(new Dimension(880, 600));
        bookTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        bookTable.getTableHeader().setFont(font1);
        bookTable.setRowHeight(30);
        bookTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(bookTable);
        scroll.setPreferredSize(new Dimension(880, 600));
        card1.add(scroll);
    }

    /**
     * 初始化查询图书页面
     */
    private void initCard2() {
        bookname.setBounds(250, 50, 80, 30);
        textName.setBounds(350, 50, 200, 30);
        btnSchB.setBounds(600, 50, 80, 30);

        card2.setFont(font);
        bookname.setFont(font);
        textName.setFont(font);
        btnSchB.setFont(font1);

        card2.add(textName);
        card2.add(bookname);
        card2.add(btnSchB);

        card2.setVerticalAlignment(JLabel.CENTER);
        card2.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * 初始化借还书记录信息表
     */
    private void initCard3() {
        //传入记录数据与表头
        JTable recordTable = new JTable(recordDao.getRecordsByUid(uId), recordTableHead);
        recordTable.setFont(font);
        recordTable.setPreferredSize(new Dimension(880, 600));
        recordTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        recordTable.getTableHeader().setFont(font1);
        recordTable.setRowHeight(30);
        recordTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(recordTable);
        scroll.setPreferredSize(new Dimension(880, 600));
        card3.removeAll();
        card3.add(scroll);
    }

    private void initCard4() {
        uName.setBounds(160, 200, 60, 30);
        textUname.setBounds(220, 200, 200, 30);
        oldPassWord.setBounds(160, 250, 60, 30);
        textoldPassWord.setBounds(220, 250, 200, 30);
        newPassWord.setBounds(160, 300, 60, 30);
        textnewPassWord.setBounds(220, 300, 200, 30);
        btnYes.setBounds(500, 300, 90, 30);

        uName.setFont(font1);
        textUname.setFont(font1);
        oldPassWord.setFont(font1);
        textoldPassWord.setFont(font1);
        newPassWord.setFont(font1);
        textnewPassWord.setFont(font1);
        btnYes.setFont(font);

        textnewPassWord.setEchoChar('●');
        textoldPassWord.setEchoChar('●');

        card4.add(uName);
        card4.add(textUname);
        card4.add(oldPassWord);
        card4.add(textoldPassWord);
        card4.add(newPassWord);
        card4.add(textnewPassWord);
        card4.add(btnYes);

        User me = userDao.queryUser(uId, null);
        String myInfo = new String(
                "<html>" +
                        "<style>" +
                        "td{width:200px;}p{font-size:20px;}" +
                        "th{width:350px;text-align:center;}" +
                        "</style>" +
                        "<h1>基本信息:</h1>" +
                        "<table><tr><td>用户名</td><td>账号</td></tr>" +
                        me.getName() + "<td>" + me.getId() + "</table>" +
                        "<h1>修改用户名及密码</h1>" +
                        "</html>");
        myinfo.setBounds(150, 0, 450, 200);
        myinfo.setFont(font);
        myinfo.setText(myInfo);
        card4.add(myinfo);
        card4.setFont(font);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //点击查询按钮监听器执行代码
        if (e.getSource() == this.btnSchB) {
            String name = textName.getText();
            if (name.equals("")) {
                ShowMessageUtils.winMessage("您还未输入书名！");
            } else {
                Book book = bookDao.searchBook(name);
                //图书不存在
                if (book == null || book.getId() <= 0) {
                    ShowMessageUtils.winMessage("图书不存在!");
                } else {
                    bid = book.getId();
                    //图书状态信息
                    String info = new String();
                    if (book.getCount() == 0) {
                        info = "无库存";
                    } else {
                        info = "可借";
                    }
                    card2.setText("<html>" + "<style>" + "td{width:300px;}"
                            + "th{width:500px;}" + "</style>" +
                            "<table align=\"center\">"
                            + "<tr><td> 状态：" + info + "</td></tr><br>"
                            + "<tr><td>书名：" + book.getName() + "</td></tr><br>"
                            + "<tr><td>价格：" + book.getPrice() + "</td></tr><br>"
                            + "<tr><td>作者：" + book.getAuthor() + "</td></tr><br>"
                            + "<tr><td>类型：" + book.getType() + "</td></tr><br>"
                            + "<tr><td>库存剩余：" + (book.getCount() - book.getHasLended()) + "</td></tr><br>"
                            + "<tr><td>借阅总次：" + book.getDiscount() + "</td></tr><br>"
                            + "<tr><td>藏书位置：" + book.getAddress() + "</td></tr><br>"
                            + "</table>"
                            + "</html>");
                }
            }
        }
        //点击修改密码的确定按钮后 触发器执行以下代码
        else if (e.getSource() == this.btnYes) {
            //修改个人信息
            String name = textUname.getText();
            String oldPassWord = new String(textoldPassWord.getPassword());
            String newPassWord = new String(textnewPassWord.getPassword());
            //姓名 旧密码 新密码 其中一个为空 则无效
            if (name == null || oldPassWord == null || newPassWord == null)
                ShowMessageUtils.winMessage("信息不完整！");
            else {
                //创建一个用户对象，根据用户名进行查询
                User me = userDao.queryUser(uId, null);
                //判断输入的原先密码是否相等不相等提示
                if (me.getPassword().equals(oldPassWord)) {
                    if (userDao.updateUserName(uId, name) && userDao.updateUserPassword(uId, newPassWord, "User")) {
                        ShowMessageUtils.winMessage("修改成功！");
                        mainUi(uId, CARD4);
                        this.dispose();
                    } else {
                        ShowMessageUtils.winMessage("修改失败");
                    }
                } else {
                    ShowMessageUtils.winMessage("旧密码错误！");
                }
            }
        } else if (e.getSource() == this.reFresh) {
            //刷新按钮默认返回第一页
            mainUi(uId, CARD1);
            this.dispose();
        } else if (e.getSource() == this.btnexit) {
            //退出登录按钮，销毁当前界面
            new Index();
            this.dispose();
        }
    }

    public static void mainUi(int id, int defaultCard) {
        UserUi jTabbedPaneTest = new UserUi(id, defaultCard);
        jTabbedPaneTest.setVisible(true);
    }

    /**
     * 点击上边菜单栏会触发的监听事件
     * 实现页面初始化和跳转
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        //获得选中的菜单栏的index，根据index初始化不同的card
        int selectIndex = tabbedPane.getSelectedIndex();
        if (selectIndex == CARD1) {
            initCard1();
        } else if (selectIndex == CARD2) {
            initCard2();
        } else if (selectIndex == CARD3) {
            initCard3();
        } else if (selectIndex == CARD4) {
            initCard4();
        } else {
            System.out.println(selectIndex);
        }
    }
}
