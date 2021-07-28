package com.abc;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        showTimer();
    }

    public static void showTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("时间=" + new Date() + " 执行了"); // 1次
            }
        };

        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的21:09:00执行，
        calendar.set(year, month, day, 14, 6, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println("1231231");

//        int period = 2 * 1000;
        //每天的date时刻执行task，每隔2秒重复执行
        timer.schedule(task, date);
        //每天的date时刻执行task, 仅执行一次
        //timer.schedule(task, date);
    }

}
