package com.shihongli.util;/*
    @author shl
    @create 2020-05-18-20:18
*/

import javax.swing.JOptionPane;

public class ShowMessageUtils {
    /**
     *  提示信息弹窗，多个地方调用，使用static修饰，减少代码冗余
     */
    public static void winMessage(String str) {
        //对话框
        JOptionPane.showMessageDialog(null, str, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}
