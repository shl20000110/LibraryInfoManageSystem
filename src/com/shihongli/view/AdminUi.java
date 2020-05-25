package com.shihongli.view;/*
    @author shl
    @create 2020-05-19-11:41
*/

import com.shihongli.controller.BookAction;
import com.shihongli.controller.UserAction;
import com.shihongli.dao.BookDaoImpl;
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

/**
 * 管理员页面设计
 */
public class AdminUi extends JFrame implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 1L;
    private UserDaoImpl userDao;
    private BookDaoImpl bookDao;
    private BookAction bookAction;
    private UserAction userAction;

    //管理员账号
    private int aId;
    //用户账号
    private int uId;
    //书名
    private String bName;
    //默认管理设置
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    // 获得屏幕尺寸
    private Dimension sc = toolkit.getScreenSize();

    private JLabel card0 = new JLabel();
    private JPanel card1 = new JPanel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JPanel card4 = new JPanel();
    private JLabel card5 = new JLabel();

    //用户管理页面组件
    private JLabel id = new JLabel("用户名");
    private JTextField textId = new JTextField();
    private JButton btnSearch = new JButton("搜  索");
    private JButton btnDelU = new JButton("删除该用户");
    private JPasswordField textNewpassword = new JPasswordField();
    private JButton newpassword = new JButton("修改密码");
    //添加用户页面组件
    private JLabel newUid = new JLabel("用户名");
    private JLabel newUname = new JLabel("姓  名");
    private JLabel newUpassword = new JLabel("密  码");
    private JTextField textUid = new JTextField();
    private JTextField textUname = new JTextField();
    private JPasswordField textUpassword = new JPasswordField();
    private JButton btnNewUser = new JButton("确定");
    //图书管理页面配件
    private JLabel btnName = new JLabel("书 名");
    private JLabel price5 = new JLabel("价 格");
    private JLabel count5 = new JLabel("数 量");
    private JLabel type5 = new JLabel("类 型");
    private JLabel author5 = new JLabel("作 者");
    private JLabel address5 = new JLabel("藏书位置");
    private JTextField textName = new JTextField();
    private JTextField price_5 = new JTextField();
    private JTextField count_5 = new JTextField();
    private JTextField type_5 = new JTextField();
    private JTextField author_5 = new JTextField();
    private JTextField address_5 = new JTextField();
    private JButton btnUpdate5 = new JButton("修 改");
    private JButton button5 = new JButton("添 加");
    private JButton btnSearchB = new JButton("搜 索");
    private JButton btnDeleteB = new JButton("删 除");
    private JButton lendBtn = new JButton("借 书");
    private JButton returnBtn = new JButton("还 书");
    //查看所有图书页面
    private String[] userTableHead = {"用户名", "姓 名", "密 码"};
    private String tableUser = new String(
            "<html><style>"
                    + "table{margin-top:0px;padding-left:80px;}"
                    + "td{width:150px;}</style>"
                    + "<table><tr><td>账号</td><td>用户名</td><td>密码</td></tr>");
    String[] bookTableHead = {"编号", "书名","价格","剩余库存","类型","作者","借阅总次","所在位置"};
    private Font font = new Font("微软雅黑", 0, 20);
    private Font font1 = new Font("微软雅黑", 0, 16);
    private JTabbedPane tabbedPane;
    private JButton reFresh = new JButton("刷新");
    private JButton btnExit = new JButton("退出");
    private JMenuBar menuBar = new JMenuBar();
    
    /**
     * 管理员页面初始化
     * @param aId 管理员账号
     * @param defaultCard 默认首页
     */
    public AdminUi(int aId, int defaultCard) {
        this.aId = aId;
        init();
        initPane();
        tabbedPane.addTab("欢迎管理员登录使用", card0);
        tabbedPane.addTab("所有用户信息", card1);
        tabbedPane.addTab("用户管理", card2);
        tabbedPane.addTab("添加用户", card3);
        tabbedPane.addTab("所有图书信息", card4);
        tabbedPane.addTab("图书管理", card5);
        tabbedPane.setSelectedIndex(defaultCard);
        tabbedPane.setEnabledAt(0, false);
        addListener();
    }

    private void addListener() {
        //修改用户密码
        newpassword.addActionListener(this);
        //查找用户
        btnSearch.addActionListener(this);
        //删除用户
        btnDelU.addActionListener(this);
        //确定按钮（用户）
        btnNewUser.addActionListener(this);
        //删除图书
        btnDeleteB.addActionListener(this);
        //查找图书
        btnSearchB.addActionListener(this);
        //添加图书
        button5.addActionListener(this);
        //更新图书信息
        btnUpdate5.addActionListener(this);
        //还书
        returnBtn.addActionListener(this);
        //借书
        lendBtn.addActionListener(this);
    }

    private void initPane() {
        setTitle("图书信息管理系统");
        setResizable(false);
        setBounds((sc.width - 900) / 2, (sc.height - 600) / 2, 900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane(JTabbedPane.NORTH);
        tabbedPane.setFont(font);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addChangeListener(this);
        reFresh.addActionListener(this);
        btnExit.addActionListener(this);
        this.setJMenuBar(menuBar);
        menuBar.add(reFresh);
        menuBar.add(btnExit);
        getContentPane().add(tabbedPane);
    }

    /**
     * 以下均为初始化页面信息
     */
    //所有用户信息
    private void initCard1() {
        card1.removeAll();
        JTable userTable = new JTable(userAction.queryAllUser(), userTableHead);
        userTable.setFont(font);
        card1.setFont(font);
        userTable.setPreferredSize(new Dimension(880, 490));
        userTable.getTableHeader().setPreferredSize(new Dimension(0, 30));
        userTable.getTableHeader().setFont(font1);
        userTable.setRowHeight(30);
        userTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(userTable);
        scroll.setPreferredSize(new Dimension(880, 490));
        card1.add(scroll);
    }
    //用户管理
    private void initCard2() {
        
        id.setBounds(200, 150, 90, 30);
        textId.setBounds(300, 150, 200, 30);
        btnSearch.setBounds(570, 150, 150, 30);
        btnDelU.setBounds(170, 300, 150, 30);
        newpassword.setBounds(370, 300, 150, 30);
        textNewpassword.setBounds(570, 300, 170, 30);

        card2.setFont(font);
        id.setFont(font);
        textId.setFont(font);
        btnSearch.setFont(font1);
        btnDelU.setFont(font1);
        newpassword.setFont(font1);
        textNewpassword.setFont(font);
        textNewpassword.setEchoChar('●');

        card2.add(textId);
        card2.add(id);
        card2.add(btnDelU);
        card2.add(btnSearch);
        card2.add(newpassword);
        card2.add(textNewpassword);
        //设置水平垂直对齐
        card2.setVerticalAlignment(JLabel.CENTER);
        card2.setHorizontalAlignment(JLabel.CENTER);
        
    }
    //添加用户
    private void initCard3() {
        
        newUid.setBounds(250, 80, 80, 30);
        textUid.setBounds(350, 80, 200, 30);
        newUname.setBounds(250, 150, 80, 30);
        textUname.setBounds(350, 150, 200, 30);
        newUpassword.setBounds(250, 220, 80, 30);
        textUpassword.setBounds(350, 220, 200, 30);
        btnNewUser.setBounds(300, 300, 90, 30);

        newUid.setFont(font);
        textUid.setFont(font);
        newUname.setFont(font);
        textUname.setFont(font);
        newUpassword.setFont(font);
        textUpassword.setFont(font);
        btnNewUser.setFont(font);
        
        //密码显示为；●字符样式
        textUpassword.setEchoChar('●');
        
        card3.add(newUid);
        card3.add(textUid);
        card3.add(newUname);
        card3.add(textUname);
        card3.add(newUpassword);
        card3.add(textUpassword);
        card3.add(btnNewUser);
        
    }
    //所有图书信息
    private void initCard4() {
        JTable bookTable = new JTable(bookAction.queryBooks(), bookTableHead);
        bookTable.setFont(font);
        bookTable.setPreferredSize(new Dimension(880, 490));
        bookTable.getTableHeader().setPreferredSize(new Dimension(100, 30));
        bookTable.getTableHeader().setFont(font1);
        bookTable.setRowHeight(30);
        bookTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(bookTable);
        scroll.setPreferredSize(new Dimension(880, 490));
        card4.removeAll();
        card4.add(scroll);
    }

    private void initCard5() {
        // 查找书籍，删除书籍，修改书籍信息
        btnName.setBounds(150, 40, 80, 30);
        textName.setBounds(230, 40, 200, 30);
        btnSearchB.setBounds(450, 40, 100, 30);
        btnDeleteB.setBounds(350, 350, 90, 30);
        lendBtn.setBounds(450, 350, 90, 30);
        returnBtn.setBounds(550, 350, 90, 30);
        price5.setBounds(150, 80, 80, 30);
        price_5.setBounds(230, 80, 200, 30);
        count5.setBounds(150, 120, 80, 30);
        count_5.setBounds(230, 120, 200, 30);
        type5.setBounds(150, 160, 80, 30);
        type_5.setBounds(230, 160, 200, 30);
        author5.setBounds(150, 200, 80, 30);
        author_5.setBounds(230, 200, 200, 30);
        address5.setBounds(150, 240, 80, 30);
        address_5.setBounds(230, 240, 200, 30);
        button5.setBounds(250, 350, 90, 30);
        btnUpdate5.setBounds(150, 350, 90, 30);
        
        btnName.setFont(font);
        textName.setFont(font);
        btnSearchB.setFont(font);
        btnDeleteB.setFont(font);
        lendBtn.setFont(font);
        returnBtn.setFont(font);
        count5.setFont(font);
        count_5.setFont(font);
        price5.setFont(font);
        price_5.setFont(font);
        type5.setFont(font);
        type_5.setFont(font);
        author5.setFont(font);
        author_5.setFont(font);
        address5.setFont(font);
        address_5.setFont(font);
        button5.setFont(font);
        btnUpdate5.setFont(font);
        
        card5.add(btnName);
        card5.add(textName);
        card5.add(btnSearchB);
        card5.add(btnDeleteB);
        card5.add(lendBtn);
        card5.add(returnBtn);
        card5.add(price5);
        card5.add(price_5);
        card5.add(count5);
        card5.add(count_5);
        card5.add(type_5);
        card5.add(type5);
        card5.add(author5);
        card5.add(author_5);
        card5.add(address5);
        card5.add(address_5);
        card5.add(button5);
        card5.add(btnUpdate5);
    }


    /**
     * 获取实例对象
     */
    private void init() {
        uId = -1;
        bName = null;
        userAction = UserAction.getInstance();
        userDao = UserDaoImpl.getInstance();
        bookDao = BookDaoImpl.getInstance();
        bookAction = BookAction.getInstance();
    }

    /**
     * 管理员登录后 自动跳转至第一页
     * @param id 用户名
     * @param defaultCard 默认首页
     */
    public static void mainUi(int id, int defaultCard) {
        AdminUi jTabbedPaneTest = new AdminUi(id, defaultCard);
        jTabbedPaneTest.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //查找用户按钮
        if (e.getSource() == this.btnSearch) {
            String id = textId.getText();
            if (id.equals("")) {
                ShowMessageUtils.winMessage("未输入账号！");
            } else {
                int uid = -1;
                try {
                    uid = Integer.parseInt(id);
                } catch (NumberFormatException ee) {
                    ShowMessageUtils.winMessage("用户名不正确！");
                    return;
                }
                User user = userDao.queryUser(uid, null);
                if (user == null)
                    ShowMessageUtils.winMessage("此账号不存在!");
                else {
                    uId = uid;
                    String str = new String(tableUser);
                    str += "<tr><td>" + user.getId() + "</td><td>"
                            + user.getName() + "</td><td>" + user.getPassword()
                            + "</td>" + "</tr></table></html>";
                    card2.setText(str);
                }
            }
        }
        //删除用户
        else if (e.getSource() == this.btnDelU) {
            if (uId <= 0)
                ShowMessageUtils.winMessage("请先查找一位用户！");
            else {
                if (userDao.deleteUser(uId)) {
                    //删除成功
                    ShowMessageUtils.winMessage("已删除该用户！");
                    //账号置空
                    uId = 0;
                } else {
                    ShowMessageUtils.winMessage("删除用户失败！");
                }
            }
        }
        //修改密码
        else if (e.getSource() == this.newpassword) {
            if (uId <= 0) {
                ShowMessageUtils.winMessage("请先查找一位用户！");
            } else {
                //获得新密码
                String password = new String(textNewpassword.getPassword());
                if (id.equals("") || password.equals("")) {
                    ShowMessageUtils.winMessage("未填写新密码！");
                } else {
                    if (userDao.updateUserPassword(uId, password, "User")) {
                        ShowMessageUtils.winMessage("密码修改成功！");
                    } else{
                        ShowMessageUtils.winMessage("密码修改失败！");
                    }
                }
            }
        }
        //添加用户确定按钮
        else if (e.getSource() == this.btnNewUser) {
            //获得账号与密码
            String password = new String(textUpassword.getPassword());
            String id = textUid.getText();
            if (id.equals("") || password.equals("")) {
                ShowMessageUtils.winMessage("用户名、密码不能为空！");
            } else {
                Integer userID = -1;
                try {
                    // String型用户名转int型用户名，非数字字符会抛异常
                    userID = Integer.parseInt(id);
                    userAction.addUser(userID, this.textUname.getText(), password);
                } catch (Exception ex) {
                    ShowMessageUtils.winMessage("请输入有效的账号！");
                }
            }
        }
        //查找图书
        else if (e.getSource() == this.btnSearchB) {
            //获取书名
            String name = textName.getText();
            if (name.equals("")) {
                ShowMessageUtils.winMessage("未输入书名！");
            } else {
                Book book = bookDao.searchBook(name);
                if (book == null || book.getId() <= 0){
                    ShowMessageUtils.winMessage("图书不存在!");
                } else {
                    //找到书 获取图书信息
                    bName = book.getName();
                    price_5.setText("" + book.getPrice());
                    count_5.setText("" + book.getCount());
                    type_5.setText(book.getType());
                    author_5.setText(book.getAuthor());
                    address_5.setText(book.getAddress());
                }
            }
        }
        //删除图书
        else if (e.getSource() == this.btnDeleteB) {
            if (this.bName == null) {
                ShowMessageUtils.winMessage("请先查找一本书！");
            } else {
                if (bookDao.deleteBook(bName)) {
                    ShowMessageUtils.winMessage("成功删除该书！");
                    bName = null;
                } else{
                    ShowMessageUtils.winMessage("暂无此书！\n删除失败");
                }
            }
        }
        //添加图书
        else if (e.getSource() == this.button5) {
            int count;
            double price;
            try {
                count = Integer.parseInt(count_5.getText());
                price = Double.parseDouble(price_5.getText());
            } catch (NumberFormatException ex) {
                ShowMessageUtils.winMessage("数量格式填写不正确!");
                return;
            }
            String name = textName.getText();
            String type = type_5.getText();
            String author = author_5.getText();
            String address = address_5.getText();
            if (name.equals("")  || type.equals("") || author.equals("") || count_5.getText().equals("") || address.equals("")) {
                ShowMessageUtils.winMessage("书名、价格、类型、数量、作者均不能为空！");
            } else {
                try {
                    if (bookAction.insertBook(name, price,count, type, author, address)) {
                        ShowMessageUtils.winMessage("添加成功！");
                    } else {
                        ShowMessageUtils.winMessage("添加失败！");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        //修改图书信息
        else if (e.getSource() == this.btnUpdate5) {
            if (bName == null) {
                ShowMessageUtils.winMessage("请先查找一本书！");
            } else {
                int count;
                double price;
                try {
                    count = Integer.parseInt(count_5.getText());
                    price = Double.parseDouble(price_5.getText());
                } catch (NumberFormatException ee) {
                    ShowMessageUtils.winMessage("图书序号格式不正确!");
                    return;
                }
                String type = type_5.getText();
                String author = author_5.getText();
                String address = address_5.getText();

                if (type.equals("") || author.equals("") || address.equals("")) {
                    ShowMessageUtils.winMessage("图书信息不完整！");
                } else {
                    Book book = new Book(bName, price, count, type, author, address);
                    if (bookDao.updateBook(book, null)) {
                        ShowMessageUtils.winMessage("图书信息修改成功！");
                        bName = null;
                    } else{
                        ShowMessageUtils.winMessage("图书信息修改失败！");
                    }
                }
            }
        }
        //还书按钮
        else if (e.getSource() == this.returnBtn) {
            if (bName == null) {
                ShowMessageUtils.winMessage("请先查找一本书！");
            } else {
                //弹出对话框
                String uIdStr = JOptionPane.showInputDialog(new JTextField(),"请输入还书者用户名");
                if (uIdStr == null){
                    return;
                }
                Integer userID = -1;
                try {
                    userID = Integer.parseInt(uIdStr);
                } catch (NumberFormatException ex) {
                    ShowMessageUtils.winMessage("用户名格式错误！");
                    return;
                }
                if (bookAction.returnBook(userID, bName)) {
                    ShowMessageUtils.winMessage("还书操作成功！");
                } else {
                    ShowMessageUtils.winMessage("还书操作失败！");
                }
            }
        }
        //借书操作
        else if (e.getSource() == this.lendBtn) {
            if (bName == null) {
                ShowMessageUtils.winMessage("请先查找一本书！");
            } else {
                //弹出对话框
                String uIdStr = JOptionPane.showInputDialog(new JTextField(), "请输入借书者用户名");
                if (uIdStr == null) {
                    return;
                }
                Integer userID = -1;
                try {
                    userID = Integer.parseInt(uIdStr);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    ShowMessageUtils.winMessage("用户名格式错误！");
                    return;
                }
                try {
                    if (bookAction.lendBook(userID, bName)) {
                        ShowMessageUtils.winMessage("借书操作成功！");
                    } else {
                        ShowMessageUtils.winMessage("借书操作失败！");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        //刷新按钮 默认返回所有用户页面
        else if (e.getSource() == this.reFresh) {
            AdminUi.mainUi(aId, UserUi.CARD1);
            //销毁当前页面
            this.dispose();
        }
        //退出登录按钮
        else if (e.getSource() == this.btnExit) {
            //返回首页
            new Index();
            //销毁当前页面
            this.dispose();
        }
    }

    /**
     * 鼠标点击上方的菜单，实现内容的初始化与跳转
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        int selectIndex = tabbedPane.getSelectedIndex();
        if (selectIndex == UserUi.CARD1) {
            initCard1();
        } else if (selectIndex == UserUi.CARD2) {
            initCard2();
        } else if (selectIndex == UserUi.CARD3) {
            initCard3();
        } else if (selectIndex == UserUi.CARD4) {
            initCard4();
        } else if (selectIndex == UserUi.CARD5) {
            initCard5();
        } else {
            System.out.println(selectIndex);
        }
    }
}
