//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.test;

import com.abc.InitParam;
import com.abc.NewsParserJob;
import com.abc.WeiboParserJob;
import com.abc.WeixinParserJob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;

public class TestCkm {
    public TestCkm() {
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        InitParam.init();
        Timer timer = new Timer();
        timer.schedule(new NewsParserJob(), 0L, InitParam.PER_TIME * 60L * 1000L);
        Timer weixintimer = new Timer();
        weixintimer.schedule(new WeixinParserJob(), 0L, InitParam.PER_TIME * 60L * 1000L);
        Timer weibotimer = new Timer();
        weibotimer.schedule(new WeiboParserJob(), 0L, InitParam.PER_TIME * 60L * 1000L);
    }

    public static void copy() {
    }
}
