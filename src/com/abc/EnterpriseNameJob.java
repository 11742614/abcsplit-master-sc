package com.abc;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class EnterpriseNameJob  extends TimerTask {
    private static final Logger logger = Logger.getLogger(EnterpriseNameJob.class);
    @Override
    public void run() {
        try {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date2 = new Date(System.currentTimeMillis());
            logger.info("***************企业名单每日更新开始，时间：---"+formatter.format(date2)+"******************");
            EnterpriseNameUtil.initMakeCustFile();
            String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
            try {
                System.out.println("企业名单文件路径-->>"+txtfilepath);
                TRSFileUtil.getBigTxtMap(txtfilepath);
                logger.info("***************企业名单文件读取完成******************");
            } catch (IOException e) {
                logger.info("***************"+e+"******************");
                e.printStackTrace();
            }
        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }
    }
}
