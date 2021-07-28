package com.abc.test;

import com.abc.DateUtils;
import com.abc.InitParam;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Demo2 {
    public static void main(String[] args) throws IOException, ParseException {
//        String time = "2020/07/21 14:11:59";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDate date = LocalDate.parse(time, formatter);
//        time = date.format(formatter);
//        System.out.println(time);



//        String time="2020/07/21 14:11:59";
//        SimpleDateFormat formatter1=new SimpleDateFormat("HHmmss");
//        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        time=formatter1.format(formatter2.parse(time));
//        System.out.println(time);

        String IRN_SENDATE = DateUtils.dateFormat(new Date(), "yyyyMMdd");
        String IRN_SENDTIME = DateUtils.dateFormat(new Date(), "HHmmss");
        System.out.println(IRN_SENDATE+"---------------"+IRN_SENDTIME);

    }


}
