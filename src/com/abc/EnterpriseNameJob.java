package com.abc;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.TimerTask;

public class EnterpriseNameJob  extends TimerTask {
    private static final Logger logger = Logger.getLogger(EnterpriseNameJob.class);
    @Override
    public void run() {
        try {
            EnterpriseNameUtil.initMakeCustFile();
            String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
            try {
                System.out.println("企业名单文件路径-->>"+txtfilepath);
                TRSFileUtil.getBigTxtMap(txtfilepath);
                logger.info("***************企业名单文件读取完成******************");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
