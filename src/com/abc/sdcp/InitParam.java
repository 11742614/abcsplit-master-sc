//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.sdcp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.log4j.Logger;

public class InitParam {
    private static final Logger logger = Logger.getLogger(InitParam.class);
    public static String configname = "abc-send.ini";
    public static Properties props = new Properties();
    public static String CTR_DIR = "D:/datatemp/data/list";
    public static String NEW_SP_DIR = "D:/datatemp/data/pa/newssp";
    public static String WX_SP_DIR = "D:/datatemp/data/pa/wxsp";
    public static String WB_SP_DIR = "D:/datatemp/data/pa/wbsp";
    public static String NEW_TO_DIR = "D:/datatemp/data/pa/news";
    public static String WX_TO_DIR = "D:/datatemp/data/pa/wx";
    public static String WB_TO_DIR = "D:/datatemp/data/pa/wb";
    public static String BASE_PATH = "/pa";
    public static long periodtime = 5L;

    public InitParam() {
    }

    public static void initconfig() {
        try {
            props.load(new InputStreamReader(InitParam.class.getResourceAsStream("/" + configname), "UTF-8"));
        } catch (IOException var1) {
            var1.printStackTrace();
            logger.error("找不到配置文件" + configname);
        }

        CTR_DIR = props.getProperty("CTR_DIR");
        NEW_SP_DIR = props.getProperty("NEW_SP_DIR");
        WX_SP_DIR = props.getProperty("WX_SP_DIR");
        WB_SP_DIR = props.getProperty("WB_SP_DIR");
        NEW_TO_DIR = props.getProperty("NEW_TO_DIR");
        WX_TO_DIR = props.getProperty("WX_TO_DIR");
        WB_TO_DIR = props.getProperty("WB_TO_DIR");
        BASE_PATH = props.getProperty("BASE_PATH");
        String _periodtime = props.getProperty("PERIOD_TIME");
        periodtime = Long.valueOf(_periodtime.trim()).longValue();
    }
}
