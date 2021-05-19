//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.sdcp;

import java.util.Timer;

public class Start {
    public Start() {
    }

    public static void main(String[] args) {
        InitParam.initconfig();
        NewsSdJob job = new NewsSdJob();
//        WeixinSdJob wxjob = new WeixinSdJob();
//        WeiboSdJob wbjob = new WeiboSdJob();
        Timer timer = new Timer();
        timer.schedule(job, 0L, InitParam.periodtime * 60L * 1000L);
//        Timer timer2 = new Timer();
//        timer2.schedule(wxjob, 0L, InitParam.periodtime * 60L * 1000L);
//        Timer timer3 = new Timer();
//        timer3.schedule(wbjob, 0L, InitParam.periodtime * 60L * 1000L);
    }
}
