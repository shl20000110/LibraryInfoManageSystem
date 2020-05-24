package com.shihongli.view;/*
    @author shl
    @create 2020-05-19-09:00
*/

import com.shihongli.controller.UserAction;
import com.shihongli.util.BackGroundImagesUtils;
import com.shihongli.util.CreateImageUtils;
import com.shihongli.util.ShowMessageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Register implements ActionListener, FocusListener {

    private UserAction userAction;
    private JFrame jf = new JFrame("图书信息管理系统");
    // 获得面板
    private Container con = jf.getContentPane();
    //默认管理设置
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    // 获得屏幕尺寸
    private Dimension sc = toolkit.getScreenSize();
    // 页面信息
    private JLabel title = new JLabel("用户注册");
    private JLabel id = new JLabel("用户名");
    private JLabel name = new JLabel("姓  名");
    private JLabel pass = new JLabel("密  码");
    private JLabel pass2 = new JLabel("确认密码");
    //输入文本框与提示
    private JTextField textId = new JTextField();
    private String hintText = "用户账号/学号";
    private JTextField textName = new JTextField();
    //密码框
    private JPasswordField textPs = new JPasswordField();
    private JPasswordField textPs2 = new JPasswordField();
    //按钮
    private JButton registerBtn = new JButton("确定");
    private JButton backLoginBtn = new JButton("返回");
    //字体样式（粗体，斜体），大小
    private Font font = new Font("楷体", 1, 28);
    private Font font1 = new Font("微软雅黑", 0, 22);
    private Font font2 = new Font("微软雅黑", 0, 18);

    //初始化注册页面
    public Register() {

        userAction = UserAction.getInstance();
        con.setLayout(null);
        jf.setSize(900, 600);
        jf.setLocation((sc.width - 900) / 2, (sc.height - 600) / 2);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        con.setVisible(true);

        //设置组件位置
        title.setBounds(380, 80, 170, 30);
        id.setBounds(300, 140, 100, 30);
        name.setBounds(300, 190, 100, 30);
        pass.setBounds(300, 240, 100, 30);
        pass2.setBounds(300, 290, 120, 30);
        textId.setBounds(430, 145, 170, 25);
        textName.setBounds(430, 195, 170, 25);
        textPs.setBounds(430, 245, 170, 25);
        textPs2.setBounds(430, 295, 170, 25);
        registerBtn.setBounds(320, 380, 100, 25);
        backLoginBtn.setBounds(490, 380, 100, 25);
        //字体样式设置
        title.setFont(font);
        title.setForeground(Color.black);
        id.setFont(font1);
        id.setForeground(Color.black);
        name.setFont(font1);
        name.setForeground(Color.black);
        pass.setFont(font1);
        pass.setForeground(Color.black);
        pass2.setFont(font1);
        pass2.setForeground(Color.black);
        textId.setFont(font1);
        textPs.setFont(font1);
        textName.setFont(font1);
        textPs2.setFont(font1);
        registerBtn.setFont(font2);
        backLoginBtn.setFont(font2);
        textId.setForeground(Color.GRAY);
        //文字提示
        textId.setText(hintText);
        //鼠标悬浮提示
        id.setToolTipText("请输入用户账号/学号");
        name.setToolTipText("请输入用户姓名");
        pass.setToolTipText("请输入密码");
        pass2.setToolTipText("请再次输入密码");
        //配置小图标
        ImageIcon icon1 = CreateImageUtils.CreateImageIcon("user.png");
        id.setIcon(icon1);
        ImageIcon icon2 = CreateImageUtils.CreateImageIcon("name.png");
        name.setIcon(icon2);
        ImageIcon icon3 = CreateImageUtils.CreateImageIcon("password.png");
        pass.setIcon(icon3);
        ImageIcon icon4 = CreateImageUtils.CreateImageIcon("bookinfo.png");
        pass2.setIcon(icon4);
        ImageIcon icon5 = CreateImageUtils.CreateImageIcon("signup.png");
        registerBtn.setIcon(icon5);
        ImageIcon icon6 = CreateImageUtils.CreateImageIcon("return.png");
        backLoginBtn.setIcon(icon6);
        //边框
        textId.setBorder(null);
        textName.setBorder(null);
        textPs2.setBorder(null);
        textPs.setBorder(null);
        //按钮设置透明无边框
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(false);
        backLoginBtn.setBorderPainted(false);
        backLoginBtn.setContentAreaFilled(false);
        //密码默认为●
        textPs.setEchoChar('●');
        textPs2.setEchoChar('●');
        //监听器
        registerBtn.addActionListener(this);
        backLoginBtn.addActionListener(this);
        textId.addFocusListener(this);
        //配置背景图片
        JLabel background = new BackGroundImagesUtils().setBackGroundImage(jf, "bg2.jpg");
        //加入组件到面板
        con.add(title);
        con.add(id);
        con.add(name);
        con.add(pass);
        con.add(pass2);
        con.add(textId);
        con.add(textName);
        con.add(textPs);
        con.add(textPs2);
        con.add(registerBtn);
        con.add(backLoginBtn);
        con.add(background);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 按下确认按钮 监听器执行以下代码
        if (e.getSource() == this.registerBtn) {
            String password = new String(textPs.getPassword());
            String password2 = new String(textPs2.getPassword());
            String id = textId.getText();
            //用户名 旧密码 新密码 都不能为空
            if (id.equals("") || password.equals("") || password2.equals("")) {
                ShowMessageUtils.winMessage("用户名、密码不能为空！");
            } else {
                if (password.equals(password2)) {
                    //新输入密码等于确认密码
                    Integer userID = -1;
                    try {
                        // String型用户名转int型用户名，非数字字符会抛异常
                        userID = Integer.parseInt(id);
                        boolean res = userAction.addUser(userID, this.textName.getText(), password);
                        if (res) {
                            //注册成功则跳转到登录界面
                            new Login();
                            this.jf.dispose();
                        }
                    } catch (Exception ex) {
                        ShowMessageUtils.winMessage("请输入有效的账号！");
                        ex.printStackTrace();
                    }
                } else {
                    ShowMessageUtils.winMessage("两次输入的密码不同！");
                }
            }
        }
        //返回按钮 跳转至登录页面
        else if (e.getSource() == this.backLoginBtn) {
            new Login();
            this.jf.dispose();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获得焦点时，清空提示内容
        String temp = textId.getText();
        if (temp.equals(hintText)) {
            textId.setText("");
            textId.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点即没有输入内容，显示提示内容
        String temp = textId.getText();
        if (temp.equals("")) {
            textId.setText(hintText);
            textId.setForeground(Color.GRAY);
        }
    }
}
