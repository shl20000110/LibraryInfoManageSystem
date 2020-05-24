package com.shihongli.util;/*
    @author shl
    @create 2020-05-22-8:57
*/

import javax.swing.*;
import java.awt.*;

/**
 * 创建小图标的工具类，便于给标签和按钮加图标，减少代码冗余
 * 传入图标图片名
 */

public class CreateImageUtils {
    public static ImageIcon CreateImageIcon(String picName) {
        ImageIcon icon = new ImageIcon("src/pic/" + picName);
        //统一设置图标大小
        icon.setImage(icon.getImage().getScaledInstance(26,30, Image.SCALE_DEFAULT));
        return icon;
    }
}
