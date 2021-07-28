package com.abc;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test2  {

    public static void main(String[] args) {
        //得到时间类
        Calendar date = Calendar.getInstance();
        //设置时间为 xx-xx-xx 00:00:00
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 14, 9, 0);
        //一天的毫秒数
        long daySpan = 24 * 60 * 60 * 1000;
        //得到定时器实例
        Timer custTime = new Timer();
        custTime.schedule(new EnterpriseNameJob(), date.getTime(), daySpan);
//        custTime.schedule(new EnterpriseNameJob(), date.getTime());
    }
}
