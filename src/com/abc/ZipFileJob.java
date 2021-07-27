package com.abc;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.abc.FileTUtils;


import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.log4j.Logger;


public class ZipFileJob extends TimerTask {
    private static final Logger logger = Logger.getLogger(ZipFileJob.class);
    public static ConcurrentMap<String, String> custMaps = new ConcurrentHashMap();
    public static ConcurrentMap<String, String> CustMapsISnot = new ConcurrentHashMap();
    @Override
    public void run() {
        //            ZipUtil.unZipFileToConfigPath();

//        ZipUtil.unZipAllThread();
//        System.out.println("sad");

//        ZipUtil.moveZipFile();
//        ZipUtil.moveDelete();
//            NewsParserJob.newRun();
        //----------
        try {
            ZipUtil.logBak();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String newstr =  FileTUtils.getFileContent(InitParam.ThreadNEWS);
if(newstr.equals("NEWS")&&CustMapsISnot.get("isnot").equals("1")) {
    Map<String, String> finalTxtmap1 = custMaps;
    Thread t1 = new Thread() {
            public void run() {
            try {

                ZipUtil.newsUnZipFileToConfigPath(finalTxtmap1);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    FileTUtils.replacTextContent(InitParam.ThreadNEWS,"0","NEWS");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
         t1.start();
}

String wxstr = FileTUtils.getFileContent(InitParam.ThreadWX);
if(wxstr.equals("WX")&&CustMapsISnot.get("isnot").equals("1")) {
    Map<String, String> finalTxtmap2 = custMaps;
    Thread t2 = new Thread() {
        public void run() {
            try {

                ZipUtil.wxUnZipFileToConfigPathThread(finalTxtmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    FileTUtils.replacTextContent(InitParam.ThreadWX,"2","WX");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    t2.start();
}

String wbstr = FileTUtils.getFileContent(InitParam.ThreadWB);
if(wbstr.equals("WB")&&CustMapsISnot.get("isnot").equals("1")) {
    Map<String, String> finalTxtmap = custMaps;
    Thread t3 = new Thread() {
        public void run() {
            try {

                ZipUtil.wbUnZipFileToConfigPathThread(finalTxtmap);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    FileTUtils.replacTextContent(InitParam.ThreadWB,"1","WB");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    t3.start();
}


String xhstr = FileTUtils.getFileContent(InitParam.ThreadXH);
if(xhstr.equals("XH")&&CustMapsISnot.get("isnot").equals("1")){
    Map<String, String> finalTxtmap3 = custMaps;
    Thread t4 = new Thread() { public void run() {
            try {

                ZipUtil.xhUnZipFileToConfigPathThread(finalTxtmap3);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    FileTUtils.replacTextContent(InitParam.ThreadXH,"3","XH");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } };
        t4.start();
}



//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //==========

    }

}
