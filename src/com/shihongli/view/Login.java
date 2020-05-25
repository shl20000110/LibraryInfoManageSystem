package com.shihongli.view;/*
    @author shl
    @create 2020-05-18-21:56
*/

import com.shihongli.controller.LoginAction;
import com.shihongli.model.Admin;
import com.shihongli.model.User;
import com.shihongli.util.BackGroundImagesUtils;
import com.shihongli.util.CreateImageUtils;
import com.shihongli.util.ShowMessageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Random;

/**
 * 登录界面设计
 */
public class Login implements ActionListener, FocusListener {

    private LoginAction loginAction;
    private JFrame jf = new JFrame("图书信息管理系统");
    //获得面板
    private Container con = jf.getContentPane();
    //获得系统默认工具类
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    //获得屏幕尺寸
    private Dimension sc = toolkit.getScreenSize();
    //标题
    private JLabel title = new JLabel("欢迎使用图书信息管理系统");
    private JLabel name = new JLabel("用户名");
    private JLabel password = new JLabel("密  码");
    //用户名输入框与提示
    private JTextField textName = new JTextField();
    private String hintText1 = "用户账号/学号";
    //密码框与提示
    private JPasswordField textPs = new JPasswordField();
    private String hintText2 = "密码";
    //单选
    private JRadioButton choice1 = new JRadioButton("用 户");
    private JRadioButton choice2 = new JRadioButton("管理员");
    private JLabel code1 = new JLabel("验证码");
    private JTextField textCode = new JTextField();
    private JLabel code2 = new JLabel();
    private String realCode;
    //按钮
    private JButton button1 = new JButton("注册");
    private JButton button2 = new JButton("登录");
    //字体样式（粗体，斜体）大小
    private Font font = new Font("楷体", 1, 28);
    private Font font1 = new Font("微软雅黑", 0, 22);
    private Font font2 = new Font("微软雅黑", 0, 20);
    //按钮单选组
    private ButtonGroup buttonGroup = new ButtonGroup();

    public Login() {
        loginAction = LoginAction.getInstance();
        con.setLayout(null);
        //设置窗口大小：900 * 600
        jf.setSize(900, 600);
        jf.setLocation((sc.width - 900) / 2, (sc.height - 600) / 2);
        // 窗口大小不可变
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con.setVisible(true);
        //设置组件位置
        title.setBounds(250, 80, 380, 30);
        name.setBounds(285, 140, 100, 30);
        password.setBounds(285, 190, 100, 30);
        textName.setBounds(400, 143, 160, 25);
        textPs.setBounds(400, 193, 160, 25);
        choice1.setBounds(330, 290, 140, 25);
        choice2.setBounds(470, 290, 80, 25);
        code1.setBounds(285, 240, 100, 25);
        textCode.setBounds(400, 240, 80, 25);
        code2.setBounds(490, 240, 70, 25);
        button1.setBounds(290, 360, 120, 25);
        button2.setBounds(450, 360, 120, 25);
        //字体样式设置
        title.setFont(font);
        name.setFont(font1);
        password.setFont(font1);
        textName.setFont(font1);
        textPs.setFont(font1);
        code1.setFont(font1);
        textCode.setFont(font1);
        code2.setFont(font1);
        button1.setFont(font2);
        button2.setFont(font2);
        title.setForeground(Color.black);
        name.setForeground(Color.black);
        password.setForeground(Color.black);
        code1.setForeground(Color.black);
        code2.setForeground(Color.black);
        textName.setForeground(Color.GRAY);
        textPs.setForeground(Color.GRAY);
        //文字提示
        textName.setText(hintText1);
        textPs.setText(hintText2);
        //鼠标悬浮提示
        name.setToolTipText("请输入用户账号/学号");
        password.setToolTipText("请输入用户密码");
        code1.setToolTipText("请输入验证码");
        //设置小图标，调用自定义方法
        ImageIcon icon1 = CreateImageUtils.CreateImageIcon("user.png");
        ImageIcon icon2 = CreateImageUtils.CreateImageIcon("password.png");
        ImageIcon icon3 = CreateImageUtils.CreateImageIcon("book.png");
        ImageIcon icon4 = CreateImageUtils.CreateImageIcon("login.png");
        ImageIcon icon5 = CreateImageUtils.CreateImageIcon("signup.png");
        name.setIcon(icon1);
        password.setIcon(icon2);
        code1.setIcon(icon3);
        button1.setIcon(icon4);
        button2.setIcon(icon5);
        //边框
        textName.setBorder(null);
        textPs.setBorder(null);
        textCode.setBorder(null);
        //单选/按钮透明无边框
        choice1.setContentAreaFilled(false);
        choice2.setContentAreaFilled(false);
        button1.setContentAreaFilled(false);
        button2.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        //将密码显示为● JPassWord默认显示也为●
        textPs.setEchoChar('●');
        //单选框默认选择choice1，默认普通用户登录
        choice1.setSelected(true);
        //设置验证码
        code2.setText(getCode());
        //监听器
        button1.addActionListener(this);
        button2.addActionListener(this);
        textName.addFocusListener(this);
        textPs.addFocusListener(this);
        //调用自定义方法加载背景图片，在pic文件夹下
        JLabel backGroundImage = new BackGroundImagesUtils().setBackGroundImage(jf, "bg2.jpg");
        //配件加入面板
        con.add(title);
        con.add(name);
        con.add(password);
        con.add(textName);
        con.add(textPs);
        con.add(choice1);
        con.add(choice2);
        buttonGroup.add(choice1);
        buttonGroup.add(choice2);
        con.add(code1);
        con.add(code2);
        con.add(textCode);
        con.add(button1);
        con.add(button2);
        con.add(backGroundImage);
    }

    /**
     * 生成验证码方法
     *
     * @return 返回验证码字符串
     */
    private String getCode() {
        StringBuilder code = new StringBuilder();
        Random rand = new Random(System.currentTimeMillis());
        //随机产生四个0-9数字
        for (int i = 0; i < 4; i++) {
            int temp = rand.nextInt();
            if (temp < 0) {
                temp = -temp;
            }
            code.append(temp % 10);
        }
        realCode = code.toString();
        return realCode;
    }

    //重写监听方法
    @Override
    public void actionPerformed(ActionEvent e) {
        //判断监听的事件，事件不同，执行的代码也不同
        //登录事件
        if (e.getSource() == this.button2) {
            // 获得输入的账号和密码
            String id = textName.getText();
            String password = new String(textPs.getPassword());
            //用户名或密码为空，提示
            if (id.equals("") || password.equals("")) {
                ShowMessageUtils.winMessage("用户名、密码不能为空！");
            } else {
                //获得用户输入的验证码，进行比对
                String code = textCode.getText();
                //若验证码相同
                if (realCode.equals(code)) {
                    //获取选择的用户类型
                    int choice = 2;
                    if (choice1.isSelected()) {
                        choice = 1;
                    }
                    Integer userId = -1;
                    try {
                        // String型用户名转int型用户名，非数字字符时会抛异常
                        userId = Integer.parseInt(id);
                        // 调用登录方法
                        login(choice, userId, password);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ShowMessageUtils.winMessage("用户名无效！");
                    }
                } else {
                    ShowMessageUtils.winMessage("验证码不正确！");
                }
            }
        }
        // 注册按钮
        else if (e.getSource() == this.button1) {
            new Register();
            //点击注册按钮时,new一个Register的页面，首页页面销毁
            this.jf.dispose();
        }
    }

    /**
     * 登录方法
     *
     * @param choice   单选框
     * @param userId   验证码
     * @param password 密码
     */
    private void login(int choice, Integer userId, String password) {
        boolean res = false;
        User user = null;
        Admin admin = null;
        if (choice == 1) {
            //用户登录
            user = loginAction.userLogin(userId, password);
            if (user != null) {
                res = true;
                //跳转页面
                UserUi.mainUi(userId, UserUi.CARD1);
            }
        } else {
            //管理员登录
            admin = loginAction.adminLogin(userId, password);
            if (admin != null) {
                res = true;
                //跳转页面
                AdminUi.mainUi(userId, UserUi.CARD1);
            }
        }
        //登录成功则销毁登录窗口
        if (res) {
            jf.dispose();
        } else {
            ShowMessageUtils.winMessage("用户名或密码错误！");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获得焦点时，清空提示内容
        String temp1 = textName.getText();
        String temp2 = String.valueOf(textPs.getPassword());
        if (temp1.equals(hintText1)) {
            textName.setText("");
            textName.setForeground(Color.BLACK);
        }
        if (temp2.equals(hintText2)) {
            textPs.setText("");
            textPs.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点即没有输入内容，显示提示内容
        String temp1 = textName.getText();
        if (temp1.equals("")) {
            textName.setForeground(Color.GRAY);
            textName.setText(hintText1);
        }
        String temp2 = String.valueOf(textPs.getPassword());
        if (temp2.equals("")) {
            textPs.setForeground(Color.GRAY);
            textPs.setText(hintText2);
        }
    }
}