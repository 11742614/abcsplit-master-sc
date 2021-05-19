//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class InitParam {
  private static final Logger logger = Logger.getLogger(InitParam.class);
  public static String ThreadNEWS = "D:/datatemp/abc_data/num.txt";
  public static String ThreadWB = "D:/datatemp/abc_data/num.txt";
  public static String ThreadWX = "D:/datatemp/abc_data/num.txt";
  public static String ThreadXH = "D:/datatemp/abc_data/num.txt";
  public static String configname = "abc.ini";
  public static String areafilename = "area.txt";
  public static String industryfilename = "industry.txt";
  public static String macrofilename = "macro.txt";
  public static String custfilename = "cust.txt";
  public static String custriskfilename = "custrisk.txt";
  public static String areariskfilename = "arearisk.txt";
  public static String industryriskfilename = "industryrisk.txt";
  public static String custfilterfilename = "custfilter.txt";
  public static String xinhaofilename = "xinhao.txt";
  public static String configxmlname = "sysconfig.xml";
  public static String path = InitParam.class.getResource("/").getFile();
  public static boolean CP = true;
  public static String SPLIT_SM = "|!";
  public static String RN_SM = "@#$";
  public static long PER_TIME = 5L;
  public static long CPFile_Time = 7L;
  public static String CHARSET = "GBK";
  public static String InitDate_PATH = "D:/datatemp/CRUV/OUT/TRS";
  public static String InitDate_NEWS_PATH = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/news";
  public static String InitDate_WEIBO_PATH = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/weibo";
  public static String InitDate_WEIXIN_PATH = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/weixin";
  public static String InitDate_XINHAO_PATH = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/xinhao";

  public static String InitDateCP_PATH = "D:/datatemp/init_data/init_data_cp";
  public static String InitDate_NEWS_PATH_CP = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/data_cp/news";
  public static String InitDate_WEIBO_PATH_CP = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/data_cp/weibo";
  public static String InitDate_WEIXIN_PATH_CP = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/data_cp/weixin";
  public static String InitDate_XINHAO_PATH_CP = "D:/EPRO/projectFile/TRS/nonghang/bak/datatemp/CRUV/OUT/TRS/data_cp/xinhao";

  public static String NEWS_PATH = "D:/datatemp/data/abc/news";
  public static String SIGNAL_PATH = "D:/datatemp/data/abc/signal";
  public static String NEWSCP_PATH = "D:/datatemp/data/abc/newscp";
  public static String SIGNALCP_PATH = "D:/datatemp/trstemp/BACKUP/SIGNALCP";
  public static String ENPFILE_PATH = "D:/EPRO/projectFile/TRS/nonghang/wendang/temp/enterpriseNameTest.txt";
  public static String NEWSTO_PATH = "D:/datatemp/data/abc/newsto";
  public static String SIGNALTO_PATH = "D:/datatemp/CRUV/IN/TRS/SIGNAL";
  public static String NEWS_FILEDS = "IRN_SID;IRN_NEWSKIND;IRN_HKEY;IRN_URLDATE;IRN_URLTIME;IRN_LOADATE;IRN_LOADTIME;IRN_LASTDATE;IRN_LASTTIME;IRN_SITENAME;IRN_CHANNEL;IRN_SRCNAME;IRN_AUTHORS;IRN_URLTITLE;IRN_abstract;IRN_KEYWORD;IRN_CONTENT;IRN_URLNAME;IRN_GROUPNAME;IRN_BBSNUM;IRN_NRESERVED2;IRN_NRESERVED3;IRN_BACKUP1;IRN_BACKUP2;IRN_BACKUP3;IRN_BACKUP4;IRN_BACKUP5;IRN_BACKUP6;IRN_BACKUP7;IRN_BACKUP8;IRN_BACKUP9;IRN_BACKUP10;IRN_BACKUP11;IRN_SENDATE;IRN_SENDTIME";
  public static String SIGNAL_FILEDS = "APC_GID;APC_SID;APC_NEWSKIND;APC_OBJKIND;APC_OBJCODETYPE;APC_OBJCODE;APC_OBJCNAME;APC_OBJENAME;APC_SARSLTYPE;APC_SARSLTCODE;APC_SARSLTNAME;APC_LINE;APC_GRADE;APC_BACKUP1;APC_BACKUP2;APC_BACKUP3;APC_BACKUP4;APC_BACKUP5;APC_BACKUP6;APC_BACKUP7;APC_BACKUP8;APC_BACKUP9;APC_BACKUP10;APC_BACKUP11;APC_BACKUP12;APC_SENDATE;APC_SENDTIME";
  public static String WEIXIN_PATH = "D:/datatemp/data/abc/weixin";
  public static String WEIXINCP_PATH = "D:/datatemp/data/abc/weixincp";
  public static String WEIXINTO_PATH = "D:/datatemp/data/abc/weixinto";
  public static String WEIXIN_FILEDS = "IRC_SID;IRC_NEWSKIND;IRC_HKEY;IRC_URLDATE;IRC_URLTIME;IRC_LOADATE;IRC_LOADTIME;IRC_LASTDATE;IRC_LASTTIME;IRC_wechatname;IRC_wechatid;IRC_AUTHORS;IRC_URLTITLE;IRC_abstract;IRC_KEYWORD;IRC_CONTENT;IRC_URLNAME;IRC_RANK;IRC_RDCOUNT;IRC_PRCOUNT;IRC_BACKUP1;IRC_BACKUP2;IRC_BACKUP3;IRC_BACKUP4;IRC_BACKUP5;IRC_BACKUP6;IRC_BACKUP7;IRC_BACKUP8;IRC_BACKUP9;IRC_BACKUP10;IRC_BACKUP11;IRC_SENDATE;IRC_SENDTIME";
  public static String WEIBO_PATH = "D:/datatemp/data/abc/weibo";
  public static String WEIBOCP_PATH = "D:/datatemp/data/abc/weibocp";
  public static String WEIBOTO_PATH = "D:/datatemp/data/abc/weiboto";
  public static String SPILTTO_PATH = "D:/datatemp/data/abc/split";
  public static String CUST_MODECP_PATH = "D:/datatemp/data/abc/custmodecp";
  public static String WEIBO_FILEDS = "IRB_SID;IRB_NEWSKIND;IRB_HKEY;IRB_URLDATE;IRB_URLTIME;IRB_LOADATE;IRB_LOADTIME;IRB_LASTDATE;IRC_LASTTIME;IRB_UID;IRB_SCREEN_NAME;IRB_RETWEETED_UID;IRB_RETWEETED_URL;IRB_RETWEETED_SCREEN_NAME;IRB_KEYWORD;IRB_CONTENT;IRB_URLNAME;IRB_VIA;IRB_RTTCOUNT;IRB_COMMTCOUNT;IRB_APPROVE_COUNT;IRC_BACKUP1;IRC_BACKUP2;IRC_BACKUP3;IRC_BACKUP4;IRC_BACKUP5;IRC_BACKUP6;IRC_BACKUP7;IRC_BACKUP8;IRC_BACKUP9;IRC_BACKUP10;IRC_BACKUP11;IRC_SENDATE;IRC_SENDTIME";
  public static String SPLIT_FILEDS = "APC_SID;APC_NEWSKIND;APC_SN;APC_OBJKIND;APC_OBJCODETYPE;APC_OBJCODE;APC_OBJCNAME;APC_OBJENAME;APC_SARSLTYPE;APC_SARSLTCODE;APC_SARSLTNAME;APC_SAKWCODE;APC_SAKWNAME;APC_BACKUP1;APC_BACKUP2;APC_BACKUP3;APC_BACKUP4;APC_BACKUP5;APC_BACKUP6;APC_BACKUP7;APC_BACKUP8;APC_BACKUP9;APC_BACKUP10;APC_BACKUP11;APC_BACKUP12;APC_SENDATE;APC_SENDTIME";
  public static String UNION_FILEDS = "IRC_SID;IRC_NEWSKIND;IRC_HKEY;IRB_URLNAME;IRC_URLNAME;IRC_URLDATE;IRC_URLTIME;IRC_LOADATE;IRC_LOADTIME;IRC_LASTDATE;IRC_LASTTIME;UID;IRN_AUTHORS;SD_ID;IRB_RETWEETED_SCREEN_NAME;IRB_RETWEETED_URL;IRN_URLTITLE;IRN_abstract;IRN_SITENAME;IRN_CHANNEL;IRN_SRCNAME;IRB_VIA;IRB_RTTCOUNT;IRB_COMMTCOUNT;IRC_RDCOUNT;IRC_PRCOUNT;IRN_GROUPNAME;IRN_BBSNUM;IRN_NRESERVED2;IRN_NRESERVED3;IRC_RANK;IRB_KEYWORD;IRB_CONTENT;IRC_BACKUP1;IRC_BACKUP2;IRC_BACKUP3;IRC_BACKUP4;IRC_BACKUP5;IRC_BACKUP6;IRC_BACKUP7;IRC_BACKUP8;IRC_BACKUP9;IRC_BACKUP10;IRC_BACKUP12;IRC_BACKUP11;IRC_SENDATE;IRC_SENDTIME";
  public static String[] NEWS_FILEDSES = new String[0];
  public static String[] WEIXIN_FILEDSES = new String[0];
  public static String[] SIGNAL_FILEDSES = new String[0];
  public static String[] WEIBO_FILEDSES = new String[0];
  public static String[] SPLIT_FILEDSES = new String[0];
  public static String[] UNION_FILEDSES = new String[0];
  public static List<String> filtercusts = new ArrayList();
  public static String[] CKM_HOSTS = new String[]{"http://127.0.0.1:8001"};
  public static String CKM_USER = "admin";
  public static String CKM_PASSWD = "trsadmin";
  public static String CUST_MODE_NAME = "mod2500W";
  public static String CUST_MODE_PATH = "D:/datatemp/data/abc/custmode";
  public static String CUST_PATH = "D:/datatemp/CRUV/OUT/TRS/abc_cust";
  public static String hashDict = "firmSFRelationdateStringABC.dict";
  public static String infoExtDict = "firmNamesdateStringABC.dict";
  public static String localDict = "localABC.dict";
  public static String custsplit = "|!";
  public static String GZIP = "gz";
  public static String CTRL = "gz.ctl";
  public static String FILED_PREFIX = "00-yuqing";
  public static boolean IS_CPSEND = false;
  public static String DATA_SUFFIX = "trs";
  public static String CONFIG_SUFFIX = "ok";
  public static String[] SEND_PATHCFGs = new String[0];
  public static String NEWS_TP = "01";
  public static String WX_TP = "02";
  public static String WB_TP = "03";
  public static String SG_TP = "04";
  public static String YS_TP = "Y1";
  public static String UN_TP = "Y2";
  public static String SP_TP = "Y3";
  public static String UNION_PATH = "D:/datatemp/data/abc/union";
  public static Map<String, String> idnames = new HashMap();
  public static Properties props = new Properties();

  public InitParam() {
  }

  public static void init() {
    initconfig();
    initindustry();
    initarea();
    initmacro();
//    initCust();
    initcustrisk();
    initindustryrisk();
    initarearisk();
    initfiltercust();
    FileTUtils.initThreadNum();
  }

  public static void initconfig() {
    try {
      props.load(new InputStreamReader(InitParam.class.getResourceAsStream("/" + configname), "UTF-8"));
    } catch (IOException var28) {
      var28.printStackTrace();
      logger.error("找不到配置文件" + configname);
    }

    String CPB = props.getProperty("CP");
    CP = Boolean.valueOf(CPB).booleanValue();
    SPLIT_SM = props.getProperty("SPLIT_SM");
    RN_SM = props.getProperty("RN_SM");
    CHARSET = props.getProperty("CHARSET");
    String PER_TIMEL = props.getProperty("PER_TIME");
    PER_TIME = Long.valueOf(PER_TIMEL).longValue();

    String CPFile_TimeL = props.getProperty("CPFile_Time");
    CPFile_Time = Long.valueOf(CPFile_TimeL).longValue();
    NEWS_PATH = props.getProperty("NEWS_PATH");
    ThreadNEWS = props.getProperty("ThreadNEWS");
    ThreadWB = props.getProperty("ThreadWB");
    ThreadWX = props.getProperty("ThreadWX");
    ThreadXH = props.getProperty("ThreadXH");
    InitDate_PATH = props.getProperty("InitDate_PATH");
    InitDate_NEWS_PATH 	= props.getProperty("InitDate_NEWS_PATH");
    InitDate_WEIBO_PATH = props.getProperty("InitDate_WEIBO_PATH");
    InitDate_WEIXIN_PATH =  props.getProperty("InitDate_WEIXIN_PATH");
    InitDate_XINHAO_PATH =  props.getProperty("InitDate_XINHAO_PATH");


    InitDateCP_PATH = props.getProperty("InitDateCP_PATH");
    InitDate_NEWS_PATH_CP = props.getProperty("InitDate_NEWS_PATH_CP");
    InitDate_WEIBO_PATH_CP = props.getProperty("InitDate_WEIBO_PATH_CP");
    InitDate_WEIXIN_PATH_CP = props.getProperty("InitDate_WEIXIN_PATH_CP");
    InitDate_XINHAO_PATH_CP = props.getProperty("InitDate_XINHAO_PATH_CP");

    NEWSCP_PATH = props.getProperty("NEWSCP_PATH");
    ENPFILE_PATH = props.getProperty("ENPFILE_PATH");
    NEWSTO_PATH = props.getProperty("NEWSTO_PATH");
    NEWS_FILEDS = props.getProperty("NEWS_FILEDS");
    //----new---
    SIGNAL_PATH = props.getProperty("SIGNAL_PATH");
    SIGNALCP_PATH = props.getProperty("SIGNALCP_PATH");
    SIGNALTO_PATH = props.getProperty("SIGNALTO_PATH");
    SIGNAL_FILEDS = props.getProperty("SIGNAL_FILEDS");
    CUST_MODECP_PATH = props.getProperty("CUST_MODECP_PATH");
    //-------
    WEIXIN_PATH = props.getProperty("WEIXIN_PATH");
    WEIXINCP_PATH = props.getProperty("WEIXINCP_PATH");
    WEIXINTO_PATH = props.getProperty("WEIXINTO_PATH");
    WEIXIN_FILEDS = props.getProperty("WEIXIN_FILEDS");
    WEIBO_PATH = props.getProperty("WEIBO_PATH");
    WEIBOCP_PATH = props.getProperty("WEIBOCP_PATH");
    WEIBOTO_PATH = props.getProperty("WEIBOTO_PATH");
    SPILTTO_PATH = props.getProperty("SPILTTO_PATH");
    WEIBO_FILEDS = props.getProperty("WEIBO_FILEDS");
    SPLIT_FILEDS = props.getProperty("SPLIT_FILEDS");
    UNION_FILEDS = props.getProperty("UNION_FILEDS");
    String _CKM_HOSTS = props.getProperty("CKM_HOSTS");
    if (_CKM_HOSTS != null) {
      CKM_HOSTS = _CKM_HOSTS.split(";");
    }

    String _CKM_USER = props.getProperty("CKM_USER");
    if (_CKM_USER != null) {
      CKM_USER = _CKM_USER.trim();
    }

    String _CKM_PASSWD = props.getProperty("CKM_PASSWD");
    if (_CKM_PASSWD != null) {
      CKM_PASSWD = _CKM_PASSWD.trim();
    }

    String _CUST_MODE_NAME = props.getProperty("CUST_MODE_NAME");
    if (_CUST_MODE_NAME != null) {
      CUST_MODE_NAME = _CUST_MODE_NAME.trim();
    }

    String _CUST_MODE_PATH = props.getProperty("CUST_MODE_PATH");
    if (_CUST_MODE_PATH != null) {
      CUST_MODE_PATH = _CUST_MODE_PATH.trim();
    }

    String _CUST_PATH = props.getProperty("CUST_PATH");
    if (_CUST_PATH != null) {
      CUST_PATH = _CUST_PATH.trim();
    }

    String _hashDict = props.getProperty("hashDict");
    if (_hashDict != null) {
      hashDict = _hashDict.trim();
    }

    String _infoExtDict = props.getProperty("infoExtDict");
    if (_infoExtDict != null) {
      infoExtDict = _infoExtDict.trim();
    }

    String _localDict = props.getProperty("localDict");
    if (_localDict != null) {
      localDict = _localDict.trim();
    }

    String _custsplit = props.getProperty("custsplit");
    if (_custsplit != null) {
      custsplit = _custsplit.trim();
    }

    String _GZIP = props.getProperty("GZIP");
    if (_GZIP != null) {
      GZIP = _GZIP.trim();
    }

    String _CTRL = props.getProperty("CTRL");
    if (_CTRL != null) {
      CTRL = _CTRL.trim();
    }

    String _NEWS_TP = props.getProperty("NEWS_TP");
    if (_NEWS_TP != null) {
      NEWS_TP = _NEWS_TP.trim();
    }

    String _WX_TP = props.getProperty("WX_TP");
    if (_WX_TP != null) {
      WX_TP = _WX_TP.trim();
    }
    String _SG_TP = props.getProperty("SG_TP");
    if (_SG_TP != null) {
      SG_TP = _SG_TP.trim();
    }

    String _WB_TP = props.getProperty("WB_TP");
    if (_WB_TP != null) {
      WB_TP = _WB_TP.trim();
    }

    String _YS_TP = props.getProperty("YS_TP");
    if (_YS_TP != null) {
      YS_TP = _YS_TP.trim();
    }

    String _UN_TP = props.getProperty("UN_TP");
    if (_UN_TP != null) {
      UN_TP = _UN_TP.trim();
    }

    String _SP_TP = props.getProperty("SP_TP");
    if (_SP_TP != null) {
      SP_TP = _SP_TP.trim();
    }

    String _UNION_PATH = props.getProperty("UNION_PATH");
    if (_UNION_PATH != null) {
      UNION_PATH = _UNION_PATH.trim();
    }

    String _FILED_PREFIX = props.getProperty("FILED_PREFIX");
    if (_FILED_PREFIX != null) {
      FILED_PREFIX = _FILED_PREFIX.trim();
    }

    NEWS_FILEDSES = NEWS_FILEDS.split(";");

    int i;
    for(i = 0; i < NEWS_FILEDSES.length; ++i) {
      NEWS_FILEDSES[i] = NEWS_FILEDSES[i].trim();
    }

    SIGNAL_FILEDSES = SIGNAL_FILEDS.split(";");
    for(i = 0; i < SIGNAL_FILEDSES.length; ++i) {
      SIGNAL_FILEDSES[i] = SIGNAL_FILEDSES[i].trim();
    }

    WEIXIN_FILEDSES = WEIXIN_FILEDS.split(";");

    for(i = 0; i < WEIXIN_FILEDSES.length; ++i) {
      WEIXIN_FILEDSES[i] = WEIXIN_FILEDSES[i].trim();
    }

    WEIBO_FILEDSES = WEIBO_FILEDS.split(";");

    for(i = 0; i < WEIBO_FILEDSES.length; ++i) {
      WEIBO_FILEDSES[i] = WEIBO_FILEDSES[i].trim();
    }

    SPLIT_FILEDSES = SPLIT_FILEDS.split(";");

    for(i = 0; i < SPLIT_FILEDSES.length; ++i) {
      SPLIT_FILEDSES[i] = SPLIT_FILEDSES[i].trim();
    }

    UNION_FILEDSES = UNION_FILEDS.split(";");

    for(i = 0; i < UNION_FILEDSES.length; ++i) {
      UNION_FILEDSES[i] = UNION_FILEDSES[i].trim();
    }

    String _IS_CPSEND = props.getProperty("IS_CPSEND");
    if (_IS_CPSEND != null) {
      IS_CPSEND = Boolean.valueOf(_IS_CPSEND.trim()).booleanValue();
    }

    String _DATA_SUFFIX = props.getProperty("DATA_SUFFIX");
    if (_DATA_SUFFIX != null) {
      DATA_SUFFIX = _DATA_SUFFIX.trim();
    }

    String _CONFIG_SUFFIX = props.getProperty("CONFIG_SUFFIX");
    if (_CONFIG_SUFFIX != null) {
      CONFIG_SUFFIX = _CONFIG_SUFFIX.trim();
    }

    String _SEND_PATHCFG = props.getProperty("SEND_PATHCFG");
    String[] _SEND_PATHCFGs = _SEND_PATHCFG.split(";");
    SEND_PATHCFGs = new String[_SEND_PATHCFGs.length];

    for(int j = 0; j < _SEND_PATHCFGs.length; ++j) {
      SEND_PATHCFGs[j] = props.getProperty(_SEND_PATHCFGs[j].trim());
    }

  }

  public static void initindustry() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + industryfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            int cdl = code.length();
            Industry industry;
            if (cdl == 1) {
              industry = new Industry();
              industry.code = code;
              industry.lv = 1;
              industry.name = name;
              industry.prdcode = null;
              ParserUtil.industryMaps.put(code, industry);
            } else if (cdl == 3) {
              industry = new Industry();
              industry.code = code;
              industry.lv = 2;
              industry.name = name;
              industry.prdcode = code.substring(0, 1);
              ParserUtil.industryMaps.put(code, industry);
            } else if (cdl == 4) {
              industry = new Industry();
              industry.code = code;
              industry.lv = 3;
              industry.name = name;
              industry.prdcode = code.substring(0, 3);
              ParserUtil.industryMaps.put(code, industry);
            } else if (cdl == 5) {
              industry = new Industry();
              industry.code = code;
              industry.lv = 4;
              industry.name = name;
              industry.prdcode = code.substring(0, 4);
              ParserUtil.industryMaps.put(code, industry);
            }
          }
        }
      }
    } catch (Exception var11) {
      logger.error("初始化行业分类失败：" + var11.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initarea() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + areafilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            int cdl = code.length();
            if (cdl == 6) {
              String one = code.substring(0, 2);
              String two = code.substring(2, 4);
              String three = code.substring(4, 6);
              int oneint = Integer.valueOf(one).intValue();
              int twoint = Integer.valueOf(two).intValue();
              int threeint = Integer.valueOf(three).intValue();
              Area area;
              if (oneint != 0 && twoint == 0 && threeint == 0) {
                area = new Area();
                area.code = code;
                area.lv = 1;
                area.name = name;
                area.prdcode = null;
                ParserUtil.areaMaps.put(code, area);
              }

              if (oneint != 0 && twoint != 0 && threeint == 0) {
                area = new Area();
                area.code = code;
                area.lv = 2;
                area.name = name;
                area.prdcode = one + "0000";
                ParserUtil.areaMaps.put(code, area);
              }

              if (oneint != 0 && twoint != 0 && threeint != 0) {
                area = new Area();
                area.code = code;
                area.lv = 3;
                area.name = name;
                area.prdcode = one + two + "00";
                ParserUtil.areaMaps.put(code, area);
              }
            }
          }
        }
      }
    } catch (Exception var17) {
      logger.error("初始化区域分类失败：" + var17.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initmacro() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + macrofilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            ParserUtil.macroMaps.put(code, name);
            ParserUtil.macroMapsnumkey.put(name,code);
          }
        }
      }
    } catch (Exception var9) {
      logger.error("初始化宏观分类失败：" + var9.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initCust() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + custfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            ParserUtil.custMaps.put(name, code);
          }
        }
      }
    } catch (Exception var9) {
      logger.error("初始化客户名单映射失败：" + var9.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initcustrisk() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + custriskfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            ParserUtil.custriskMaps.put(name, code);
            ParserUtil.custriskMapsnumkey.put(code, name);
          }
        }
      }
    } catch (Exception var9) {
      logger.error("初始化客户风险映射：" + var9.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initindustryrisk() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + industryriskfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            ParserUtil.industryriskMaps.put(name, code);
            ParserUtil.industryriskMapsnumkey.put(code, name);
          }
        }
      }
    } catch (Exception var9) {
      logger.error("初始化行业风险失败：" + var9.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initarearisk() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + areariskfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          String[] lines = line.split("\t");
          if (lines.length == 2) {
            String code = lines[0].trim();
            String name = lines[1].trim();
            ParserUtil.areariskMaps.put(name, code);
            ParserUtil.areariskMapsnumkey.put(code,name);
          }
        }
      }
    } catch (Exception var9) {
      logger.error("初始化区域风险失败：" + var9.getMessage());
    } finally {
      it.close();
    }

  }

  public static void initfiltercust() {
    LineIterator it = null;

    try {
      File file = new File(path + "/" + custfilterfilename);
      it = FileUtils.lineIterator(file, "UTF-8");
      String line = null;

      while(it.hasNext()) {
        line = it.nextLine();
        if (!StringUtils.isBlank(line)) {
          filtercusts.add(line.trim());
        }
      }
    } catch (Exception var6) {
      logger.error("初始化过滤字符串失败：" + var6.getMessage());
    } finally {
      it.close();
    }

  }

}
