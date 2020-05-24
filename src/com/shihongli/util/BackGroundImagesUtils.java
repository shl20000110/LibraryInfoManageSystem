package com.shihongli.util;/*
    @author shl
    @create 2020-05-22-15:31
*/

import javax.swing.*;
import java.awt.*;

/**
 *设置JFrame背景图片，减少代码冗余
 */
public class BackGroundImagesUtils {
    public static JLabel setBackGroundImage(JFrame jFrame,String bgImage) {
        ImageIcon icon = new ImageIcon("src/pic/" + bgImage);
        JLabel bg = new JLabel(icon);
        JLayeredPane layeredPane = jFrame.getLayeredPane();
        bg.setSize(jFrame.getSize().width,jFrame.getSize().height);
        icon.setImage(icon.getImage().getScaledInstance
                (bg.getSize().width,bg.getSize().height, Image.SCALE_DEFAULT));
        layeredPane.add(bg,new Integer(Integer.MIN_VALUE));

        return bg;
    }
}
