package com.abc;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.abc.FileTUtils;


import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;


public class ZipFileJob extends TimerTask {


    @Override
    public void run() {
        //            ZipUtil.unZipFileToConfigPath();

//        ZipUtil.unZipAllThread();
//        System.out.println("sad");

//        ZipUtil.moveZipFile();
//        ZipUtil.moveDelete();
//            NewsParserJob.newRun();
        //----------
        String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
        Map<String,String> txtmap = new HashMap<String,String>();
        try {
            txtmap = TRSFileUtil.getBigTxtMap(txtfilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String newstr =  FileTUtils.getFileContent(InitParam.ThreadNEWS);
if(newstr.equals("NEWS")) {
    Map<String, String> finalTxtmap1 = txtmap;
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
if(wxstr.equals("WX")) {
    Map<String, String> finalTxtmap2 = txtmap;
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
if(wbstr.equals("WB")) {
    Map<String, String> finalTxtmap = txtmap;
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
if(xhstr.equals("XH")){
    Map<String, String> finalTxtmap3 = txtmap;
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
