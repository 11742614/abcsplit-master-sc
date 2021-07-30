package com.abc;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.abc.util.ZipUtil;

public class Test2  {

    public static void main(String[] args) {
//        String str = "java怎么，把字符,串中~@#$^&的asdasd的汉200字取出来";
//        String regEx="[a-zA-Z`~@#$^&*+=|{}\\[\\].<>/~！@#&*|]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        str = m.replaceAll("").trim();
//
//        System.out.println(str);

        for (int i = 0; i < 10; i++) {
            if(i==5) {
                continue;
            }
            System.out.println(i);
        }
    }
}
