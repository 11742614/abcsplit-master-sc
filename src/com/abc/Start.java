//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.Timer;

import com.abc.util.EnterpriseNameUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.poi.ss.formula.functions.T;

public class Start {
  public Start() {
  }

  public static void main(String[] args) throws IOException {
    InitParam.init();
    EnterpriseNameUtil.initMakeCustFile();
//    //-----新增---------
//    //定时5分钟一次的解压原文件  并把原文件拷贝到目标位置
    Timer Filetimer = new Timer();
    Filetimer.schedule(new ZipFileJob(), 0L, InitParam.PER_TIME * 60L * 1000L);

    //定时天数的删除备份文件任务
    Timer DelTime = new Timer();
    DelTime.schedule(new DeleteFileJob(), 0L, InitParam.CPFile_Time * 86400000L);

    //定时天数的删除备份文件任务
//    Timer custTime = new Timer();
//    custTime.schedule(new EnterpriseNameJob(), 0L, 86400000L);



    //得到时间类
    Calendar date = Calendar.getInstance();
    //设置时间为 xx-xx-xx 00:00:00
    date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 0, 0, 0);
    //一天的毫秒数
    long daySpan = 24 * 60 * 60 * 1000;
    //得到定时器实例
    Timer custTime = new Timer();
    custTime.schedule(new EnterpriseNameJob(), date.getTime(), daySpan);



//    //------------------
//
    if(false) {
      Timer timer = new Timer();
      timer.schedule(new NewsParserJob(), 60L * 1000L,InitParam.PER_TIME * 60L * 1000L);
      Timer weixintimer = new Timer();
      weixintimer.schedule(new WeixinParserJob(), 60L * 1000L, InitParam.PER_TIME * 60L * 1000L);
      Timer weibotimer = new Timer();
      weibotimer.schedule(new WeiboParserJob(), 60L * 1000L, InitParam.PER_TIME * 60L * 1000L);
      //-----new------
      Timer signaltimer = new Timer();
      signaltimer.schedule(new SignalParserJob(), 60L * 1000L, InitParam.PER_TIME * 60L * 1000L);
    }
    //----------
    Timer dictimer = new Timer();
    dictimer.schedule(new CPdicJob(), 60L * 1000L, InitParam.PER_TIME * 60L * 1000L);


//    Timer timer = new Timer();
//    timer.schedule(new NewsParserJob(), 0L, 60L * 1000L);
//    Timer weixintimer = new Timer();
//    weixintimer.schedule(new WeixinParserJob(), 0L, 60L * 1000L);
//    Timer weibotimer = new Timer();
//    weibotimer.schedule(new WeiboParserJob(), 0L, 60L * 1000L);
//    //-----new------
//    Timer signaltimer = new Timer();
//    signaltimer.schedule(new SignalParserJob(), 0L, 60L * 1000L);
//    //----------
//    Timer dictimer = new Timer();
//    dictimer.schedule(new CPdicJob(), 0L, 60L * 1000L);


    String name = ManagementFactory.getRuntimeMXBean().getName();
    String pid = name.split("@")[0];
    FileWriterWithEncoding fileWriter = null;

    try {
      fileWriter = new FileWriterWithEncoding("abc.pid", "GBK");
      fileWriter.append(pid);
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException var9) {
      var9.printStackTrace();
    }

  }
}
