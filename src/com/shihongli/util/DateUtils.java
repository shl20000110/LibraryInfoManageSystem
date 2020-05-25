package com.shihongli.util;/*
    @author shl
    @create 2020-05-20-18:17
*/

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getDate() {
        Date date = new Date();
        // 将英文时间转为指定格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}


