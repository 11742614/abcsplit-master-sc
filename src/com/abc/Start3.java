package com.abc;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.*;

public class Start3 {
    private static final Logger logger = Logger.getLogger(Start3.class);
    public static void main(String[] args) throws IOException {
        logger.info("***************程序开始初始化******************");
        InitParam.init();
        EnterpriseNameUtil.initMakeCustFile();

        logger.info("***************程序开始执行******************");
        String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
        try {
            System.out.println("企业名单文件路径-->>"+txtfilepath);
            TRSFileUtil.getBigTxtMap(txtfilepath);
            logger.info("***************企业名单文件读取完成******************");
        } catch (IOException e) {
            e.printStackTrace();
        }

//    //-----新增---------
//    //定时5分钟一次的解压原文件  并把原文件拷贝到目标位置
        Timer Filetimer = new Timer();
        Filetimer.schedule(new ZipFileJob(), 0L, InitParam.PER_TIME * 60L * 1000L);

        //定时天数的删除备份文件任务
        Timer DelTime = new Timer();
        DelTime.schedule(new DeleteFileJob(), 0L, InitParam.CPFile_Time * 86400000L);

        //得到时间类
        Calendar date = Calendar.getInstance();
        //设置时间为 xx-xx-xx 00:00:00
        date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 0, 0, 0);
        //一天的毫秒数
        long daySpan = 24 * 60 * 60 * 1000;
        //得到定时器实例
        Timer custTime = new Timer();
        custTime.schedule(new EnterpriseNameJob(), date.getTime(), daySpan);


        //----------
        Timer dictimer = new Timer();
        dictimer.schedule(new CPdicJob(), 60L * 1000L, InitParam.PER_TIME * 60L * 1000L);


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
