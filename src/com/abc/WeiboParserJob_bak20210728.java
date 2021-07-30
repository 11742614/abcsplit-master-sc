//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WeiboParserJob_bak20210728 extends TimerTask {
  private static final Logger logger = Logger.getLogger(WeiboParserJob_bak20210728.class);
  private File[] oklist = null;

  public WeiboParserJob_bak20210728() {
  }

  public void run() {
//    logger.info("微博数据拆分任务开始");
//    this.readfilelist();
//    File[] var4 = this.oklist;
//    int var3 = this.oklist.length;
//
//    for(int var2 = 0; var2 < var3; ++var2) {
//      File okfile = var4[var2];
//      try {
//        if (this.readtrsfile(okfile)) {
//          okfile.delete();
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//    String weiboFile = InitParam.WEIBO_PATH+"/";
//    ZipUtil.delAllFile(weiboFile);
//    logger.info("微博数据拆分任务结束");
  }


  public static void weiBoRun(Map<String,String> txtmap) {
    WeiboParserJob_bak20210728 weiboParserJob = new WeiboParserJob_bak20210728();
    logger.info("微博数据拆分任务开始");
    weiboParserJob.readfilelist();
    File[] var4 = weiboParserJob.oklist;
    int var3 = weiboParserJob.oklist.length;

    for(int var2 = 0; var2 < var3; ++var2) {
      System.out.println("微博数据拆分共"+var3+"个"+"正在处理第"+var2+"个");
      File okfile = var4[var2];
      try {
        if (weiboParserJob.readtrsfile(okfile,txtmap)) {
          okfile.delete();
        }
      } catch (IOException e) {
        logger.info("***************"+e+"******************");
        e.printStackTrace();
      }
    }
    String weiboFile = InitParam.WEIBO_PATH+"/";
    ZipUtil.delAllFile(weiboFile);
    logger.info("微博数据拆分任务结束");
  }

  private void createblank(boolean one) {
    String uuid = UUID.randomUUID().toString();
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    String weixnfilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + ".txt";
    String weixngzfilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
    String weixnokfilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
    FileWriterWithEncoding weixnwriter = null;
    FileWriterWithEncoding weixnmd5writer = null;

    try {
      if (one) {
        weixnwriter = new FileWriterWithEncoding(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weixnfilename, InitParam.CHARSET, true);
      }
    } catch (IOException var12) {
      logger.error(var12.getMessage());
    }

    try {
      if (weixnwriter != null && one) {
        weixnwriter.append("");
        weixnwriter.flush();
        weixnwriter.close();
      }
    } catch (IOException var11) {
      logger.error(var11.getMessage());
    }

    try {
      if (one) {
        GZIPUtil.compressFile(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weixnfilename, InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weixngzfilename);
        File txtf = new File(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weixnfilename);
        txtf.delete();
        weixnmd5writer = new FileWriterWithEncoding(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weixnokfilename, InitParam.CHARSET, true);
        weixnmd5writer.write("");
        weixnmd5writer.flush();
        weixnmd5writer.close();
      }
    } catch (IOException var10) {
      logger.error(var10.getMessage());
    }

  }

  private boolean readtrsfile(File okfile,Map<String,String> txtmap) throws IOException {
    String filename = okfile.getName().replace(".ok", ".trs");
    File trsfile = new File(InitParam.WEIBO_PATH + "/" + filename);
    if (trsfile.exists()) {
      logger.info(trsfile.getAbsoluteFile());
    }

    List<Map<String, Object>> dataList = TRSParser.readFile(trsfile, "GBK", InitParam.CP, InitParam.WEIBOCP_PATH);
    //新增用来初始化数据，和企业名单碰撞
      dataList =TRSFileUtil.initListData(dataList,txtmap);
    if (dataList != null && dataList.size() != 0) {
      String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
      String uuid = UUID.randomUUID().toString();
      String weibofilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + ".txt";
      String splitfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + ".txt";
      String unionfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + ".txt";
      String weibogzfilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String splitgzfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String uniongzfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String weibookfilename = InitParam.WB_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      String splitokfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      String unionokfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      FileWriterWithEncoding weibowriter = null;
      FileWriterWithEncoding weibosplitwriter = null;
      FileWriterWithEncoding weibounionwriter = null;
      FileWriterWithEncoding weibomd5writer = null;
      FileWriterWithEncoding weibosplitmd5writer = null;
      FileWriterWithEncoding weibounionmd5writer = null;

      File txtf;
      File txgztf;
      File txtfs;
      try {
        txtf = new File(InitParam.WEIBOTO_PATH + "/" + dateStr);
        if (!txtf.exists()) {
          txtf.mkdirs();
        }

        txgztf = new File(InitParam.SPILTTO_PATH + "/" + dateStr);
        if (!txgztf.exists()) {
          txgztf.mkdirs();
        }

        txtfs = new File(InitParam.UNION_PATH + "/" + dateStr);
        if (!txtfs.exists()) {
          txtfs.mkdirs();
        }

        weibowriter = new FileWriterWithEncoding(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weibofilename, InitParam.CHARSET, true);
        weibosplitwriter = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.CHARSET, true);
        weibounionwriter = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.CHARSET, true);
      } catch (IOException var37) {
        logger.error(var37.getMessage());
        return false;
      }

      Iterator var39 = dataList.iterator();

      label127:
      while(true) {
        Map datamap;
        do {
          if (!var39.hasNext()) {
            try {
              if (weibowriter != null) {
                weibowriter.flush();
                weibowriter.close();
              }

              if (weibounionwriter != null) {
                weibounionwriter.flush();
                weibounionwriter.close();
              }

              if (weibosplitwriter != null) {
                weibosplitwriter.flush();
                weibosplitwriter.close();
              }
            } catch (IOException var36) {
              logger.error(var36.getMessage());
              return false;
            }

            try {
              GZIPUtil.compressFile(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weibofilename, InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weibogzfilename);
              txtf = new File(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weibofilename);
              txtf.delete();
              weibomd5writer = new FileWriterWithEncoding(InitParam.WEIBOTO_PATH + "/" + dateStr + "/" + weibookfilename, InitParam.CHARSET, true);
              weibomd5writer.write("");
              weibomd5writer.flush();
              weibomd5writer.close();
              GZIPUtil.compressFile(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.UNION_PATH + "/" + dateStr + "/" + uniongzfilename);
              txgztf = new File(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename);
              txgztf.delete();
              weibounionmd5writer = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionokfilename, InitParam.CHARSET, true);
              weibounionmd5writer.write("");
              weibounionmd5writer.flush();
              weibounionmd5writer.close();
              GZIPUtil.compressFile(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitgzfilename);
              txtfs = new File(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename);
              txtfs.delete();
              weibosplitmd5writer = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitokfilename, InitParam.CHARSET, true);
              weibosplitmd5writer.write("");
              weibosplitmd5writer.flush();
              weibosplitmd5writer.close();
              logger.info("成功转换数据文件:" + filename);
              return true;
            } catch (IOException var35) {
              logger.error(var35.getMessage());
              return false;
            }
          }

          Map<String, Object> map = (Map)var39.next();
//          datamap = this.parserData(map);
          datamap = this.parserTRSData(map,txtmap);
          for(Object key:datamap.keySet()){
            String keyy = key.toString();
            String value = datamap.get(keyy).toString();
            if(!StringUtils.isNotBlank(value) || value.equals("null")){
              value = "";
            }
            datamap.put(key,value);
          }
        } while(datamap == null);
        List<Map<String, String>> splits = this.splitData(datamap);
        if(splits.size()==0) {
          Map tempmap = new HashMap();
          datamap = tempmap;
        }
        String line = "";
        int i = 0;
        String[] var30 = InitParam.WEIBO_FILEDSES;
        int var29 = InitParam.WEIBO_FILEDSES.length;

        int n;
        for(n = 0; n < var29; ++n) {
          String param = var30[n];
          if (i > 0) {
            line = line + InitParam.SPLIT_SM;
          }

          if (StringUtils.isBlank((String)datamap.get(param))) {
            line = line;
          } else {
            line = line + (String)datamap.get(param);
          }

          ++i;
        }
        if(splits.size()!=0) {
          this.write(weibowriter, line);
        }
//        Map<String, String> dataMaps = this.parserUnionMap(datamap);
        Map<String, String> dataMaps = this.parserTRSUnionMap(datamap);

        n = 0;
        String line_ = "";
        String[] var33 = InitParam.UNION_FILEDSES;
        int var32 = InitParam.UNION_FILEDSES.length;

        for(int var31 = 0; var31 < var32; ++var31) {
          String param = var33[var31];
          if (n > 0) {
            line_ = line_ + InitParam.SPLIT_SM;
          }

          if (StringUtils.isNotBlank((String)dataMaps.get(param))) {
            line_ = line_ + (String)dataMaps.get(param);
          } else {
            line_ = line_;
          }

          ++n;
        }

        this.write(weibounionwriter, line_);

        Iterator var46 = splits.iterator();

        while(true) {
          Map map2;
          do {
            if (!var46.hasNext()) {
              continue label127;
            }

            map2 = (Map)var46.next();
          } while(map2 == null);

          String slitLine = "";

          for(int j = 0; j < InitParam.SPLIT_FILEDSES.length; ++j) {
            if (j > 0) {
              slitLine = slitLine + InitParam.SPLIT_SM;
            }

            if (StringUtils.isBlank((String)map2.get(InitParam.SPLIT_FILEDSES[j]))) {
              slitLine = slitLine;
            } else {
              slitLine = slitLine + (String)map2.get(InitParam.SPLIT_FILEDSES[j]);
            }
          }

          this.write(weibosplitwriter, slitLine);
        }
      }
    } else {
      return false;
    }
  }

  private void write(FileWriterWithEncoding writer, String line) {
    try {
      writer.append(line + InitParam.RN_SM + "\n");
    } catch (IOException var4) {
      var4.printStackTrace();
    }

  }

  private List<Map<String, String>> splitData(Map<String, String> data) {
    List<Map<String, String>> dataList = new ArrayList();
    //area
    String CATALOG3 = (String)data.get("CATALOG3");
//    String CATALOG1 = (String)data.get("CATALOG1");
    //arearisk
    String CATALOG16 = (String)data.get("CATALOG16");
    //custrisk
    String CATALOG12 = (String)data.get("CATALOG12");
    //cust
    String CATALOG9 = (String)data.get("CATALOG9");
    //macroesrisk
    String CATALOG20 = (String)data.get("CATALOG20");
    //industr
    String CATALOG21 = (String)data.get("CATALOG21");
    //inrisk
    String CATALOG11 = (String)data.get("CATALOG6");
    String ZFM = (String)data.get("ZFM");
    if (ZFM != null) {
      ZFM = ZFM.replace(";", "");
    }

    String IRA_publicsentiment = "04";
    if ("正面".equals(ZFM)) {
      IRA_publicsentiment = "01";
    } else if ("负面".equals(ZFM)) {
      IRA_publicsentiment = "02";
    } else if ("中性".equals(ZFM)) {
      IRA_publicsentiment = "03";
    } else {
      IRA_publicsentiment = "04";
    }

    int num = 1;
    String IRN_SID = (String)data.get("IRB_SID");
    String IRA_SENDATE = (String)data.get("IRC_SENDATE");
    String IRA_SENDTIME = (String)data.get("IRC_SENDTIME");
    String IRB_URLDATE = (String)data.get("IRB_URLDATE");
    List<String> areaes = ParserUtil.getAreas(CATALOG3);
    String APC_SIMRANK = (String)data.get("IRC_SIMRANK");
    String APC_SIMID = (String)data.get("IRC_SIMID");
    String APC_SIMV = (String)data.get("IRC_SIMV");
    String APV_SIGNT = (String)data.get("IRC_SIGNT");
    List industries;
    if (areaes != null && areaes.size() > 0) {
      industries = ParserUtil.parseAreaRisk(areaes, CATALOG16, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "13", IRB_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!industries.isEmpty()) {
        dataList.addAll(industries);
        num += industries.size();
      }
    }

    industries = ParserUtil.getIndustrys(CATALOG21);
    if (industries != null && industries.size() > 0) {
      List<Map<String, String>> list = ParserUtil.parseIndustryRisk(industries, CATALOG11, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "13", IRB_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!list.isEmpty()) {
        dataList.addAll(list);
        num += list.size();
      }
    }

    String[] macroes = (String[])null;
    if (StringUtils.isNotBlank(CATALOG20)) {
      macroes = CATALOG20.split(";");
    }

    if (macroes != null && macroes.length > 0) {
      List<Map<String, String>> list = ParserUtil.parseMacroRisk(macroes, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "13", IRB_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!list.isEmpty()) {
        dataList.addAll(list);
        num += list.size();
      }
    }

    String[] companies = (String[])null;
    if (StringUtils.isNotBlank(CATALOG9)) {
      companies = CATALOG9.split(";");
    }

    List list;
    if (companies != null && companies.length > 0) {
      list = ParserUtil.parseCustRisk(companies, CATALOG12, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "13", IRB_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!list.isEmpty()) {
        dataList.addAll(list);
        num += list.size();
      }
    }

//    list = ParserUtil.parseZFMRisk(IRA_publicsentiment, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRB_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
//    if (!list.isEmpty()) {
//      dataList.addAll(list);
//      int var10000 = num + list.size();
//    }

    return dataList;
  }
  private Map<String, String> parserTRSUnionMap(Map<String, String> map){
    Map<String, String> datamap = new HashMap();
    String []mapkey   = {"IRU_SID","IRU_NEWSKIND","IRU_HKEY","IRU_URLNAME","IRU_OLDURLNAME","IRU_URLDATE","IRU_URLTIME","IRU_LOADATE","IRU_LOADTIME","IRU_LASTDATE","IRU_LASTTIME","IRU_AUTHORID","IRU_AUTHORS","IRU_RETWEETED_SCREEN_ID","IRU_RETWEETED_SCREEN_NAME","IRU_RETWEETED_URL","IRU_VIA","IRU_RTTCOUNT","IRU_COMMTCOUNT","IRU_PRCOUNT","IRU_KEYWORD","IRU_CONTENT","IRU_BACKUP1","IRU_BACKUP2","IRU_BACKUP3","IRU_BACKUP4","IRU_BACKUP5","IRU_BACKUP6","IRU_BACKUP7","IRU_BACKUP8","IRU_BACKUP9","IRU_BACKUP10","IRU_BACKUP11","IRU_SENDATE","IRU_SENDTIME","IRU_SIMRANK","IRU_SIMID","IRU_SIMV","IRU_SIGNT"};
    String []mapvalue = {"IRB_SID","IRB_NEWSKIND","IRB_HKEY","IRB_URLNAME","IRB_URLNAME","IRB_URLDATE","IRB_URLTIME","IRB_LOADATE","IRB_LOADTIME","IRB_LASTDATE","IRC_LASTTIME","IRB_UID","IRB_SCREEN_NAME","IRB_RETWEETED_UID","IRB_RETWEETED_SCREEN_NAME","IRB_RETWEETED_URL","IRB_VIA","IRB_RTTCOUNT","IRB_COMMTCOUNT","IRB_APPROVE_COUNT","IRB_KEYWORD","IRB_CONTENT","IRC_BACKUP1","IRC_BACKUP2","IRC_BACKUP3","IRC_BACKUP4","IRC_BACKUP5","IRC_BACKUP6","IRC_BACKUP7","IRC_BACKUP8","IRC_BACKUP9","IRC_BACKUP10","IRC_BACKUP11","IRC_SENDATE","IRC_SENDTIME","IRC_SIMRANK","IRC_SIMID","IRC_SIMV","IRC_SIGNT"};
    for (int j = 0; j < mapkey.length; j++) {
      String trsstr = (String)map.get(mapvalue[j]);
      if (trsstr == null) {
        trsstr = "";
      }
      datamap.put(mapkey[j], trsstr);
    }
    //整合表里面没有值的
    String []mapkon ={"IRU_URLTITLE","IRU_abstract","IRU_SITENAME","channel","IRU_SRCNAME","IRU_RDCOUNT","IRU_GROUPNAME","IRU_BBSNUM","IRU_NRESERVED2","IRU_NRESERVED3","IRU_RANK","IRU_BACKUP12"};
    for (int j = 0; j < mapkon.length; j++) {
      datamap.put(mapkon[j], "");
    }

    return datamap;
  }


  private Map<String, String> parserUnionMap(Map<String, String> map) {
    Map<String, String> _map = new HashMap();
    String IRB_SID = (String)map.get("IRB_SID");
    if (IRB_SID == null) {
      IRB_SID = "";
    }

    _map.put("IRC_SID", IRB_SID);
    String IRB_NEWSKIND = (String)map.get("IRB_NEWSKIND");
    if (IRB_NEWSKIND == null) {
      IRB_NEWSKIND = "";
    }

    _map.put("IRC_NEWSKIND", IRB_NEWSKIND);
    String IRB_HKEY = (String)map.get("IRB_HKEY");
    if (IRB_HKEY == null) {
      IRB_HKEY = "";
    }

    _map.put("IRC_HKEY", IRB_HKEY);
    String IRB_URLDATE = (String)map.get("IRB_URLDATE");
    if (IRB_URLDATE == null) {
      IRB_URLDATE = "";
    }

    _map.put("IRC_URLDATE", IRB_URLDATE);
    String IRB_URLTIME = (String)map.get("IRB_URLTIME");
    if (IRB_URLTIME == null) {
      IRB_URLTIME = "";
    }

    _map.put("IRC_URLTIME", IRB_URLTIME);
    String IRB_LOADATE = (String)map.get("IRB_LOADATE");
    if (IRB_LOADATE == null) {
      IRB_LOADATE = "";
    }

    _map.put("IRC_LOADATE", IRB_LOADATE);
    String IRB_LOADTIME = (String)map.get("IRB_LOADTIME");
    if (IRB_LOADTIME == null) {
      IRB_LOADTIME = "";
    }

    _map.put("IRC_LOADTIME", IRB_LOADTIME);
    String IRB_LASTDATE = (String)map.get("IRB_LASTDATE");
    if (IRB_LASTDATE == null) {
      IRB_LASTDATE = "";
    }

    _map.put("IRC_LASTDATE", IRB_LASTDATE);
    String IRB_LASTTIME = (String)map.get("IRB_LASTTIME");
    if (IRB_LASTTIME == null) {
      IRB_LASTTIME = "";
    }

    _map.put("IRC_LASTTIME", IRB_LASTTIME);
    String IRB_UID = (String)map.get("IRB_UID");
    if (IRB_UID == null) {
      IRB_UID = "";
    }

    _map.put("IR_UID", IRB_UID);
    String IRB_SCREEN_NAME = (String)map.get("IRB_SCREEN_NAME");
    if (IRB_SCREEN_NAME == null) {
      IRB_SCREEN_NAME = "";
    }

    _map.put("IRN_AUTHORS", IRB_SCREEN_NAME);
    String IRB_RETWEETED_UID = (String)map.get("IRB_RETWEETED_UID");
    if (IRB_RETWEETED_UID == null) {
      IRB_RETWEETED_UID = "";
    }

    _map.put("SD_ID", IRB_RETWEETED_UID);
    String IRB_RETWEETED_URL = (String)map.get("IRB_RETWEETED_URL");
    if (IRB_RETWEETED_URL == null) {
      IRB_RETWEETED_URL = "";
    }

    _map.put("IRB_RETWEETED_URL", IRB_RETWEETED_URL);
    String IRB_RETWEETED_SCREEN_NAME = (String)map.get("IRB_RETWEETED_SCREEN_NAME");
    if (IRB_RETWEETED_SCREEN_NAME == null) {
      IRB_RETWEETED_SCREEN_NAME = "";
    }

    _map.put("IRB_RETWEETED_SCREEN_NAME", IRB_RETWEETED_SCREEN_NAME);
    String IRB_KEYWORD = (String)map.get("IRB_KEYWORD");
    if (IRB_KEYWORD == null) {
      IRB_KEYWORD = "";
    }

    _map.put("IRB_KEYWORD", IRB_KEYWORD);
    String IRB_CONTENT = (String)map.get("IRB_CONTENT");
    if (IRB_CONTENT == null) {
      IRB_CONTENT = "";
    }

    _map.put("IRB_CONTENT", IRB_CONTENT);
    String IRB_URLNAME = (String)map.get("IRB_URLNAME");
    if (IRB_URLNAME == null) {
      IRB_URLNAME = "";
    }

    _map.put("IRB_URLNAME", IRB_URLNAME);
    String IRB_VIA = (String)map.get("IRB_VIA");
    if (IRB_VIA == null) {
      IRB_VIA = "";
    }

    _map.put("IRB_VIA", IRB_VIA);
    String IRB_RTTCOUNT = (String)map.get("IRB_RTTCOUNT");
    if (IRB_RTTCOUNT == null) {
      IRB_RTTCOUNT = "";
    }

    _map.put("IRB_RTTCOUNT", IRB_RTTCOUNT);
    String IRB_COMMTCOUNT = (String)map.get("IRB_COMMTCOUNT");
    if (IRB_COMMTCOUNT == null) {
      IRB_COMMTCOUNT = "";
    }

    _map.put("IRB_COMMTCOUNT", IRB_COMMTCOUNT);
    String IRB_APPROVE_COUNT = (String)map.get("IRB_APPROVE_COUNT");
    if (IRB_APPROVE_COUNT == null) {
      IRB_APPROVE_COUNT = "";
    }

    _map.put("IRB_APPROVE_COUNT", IRB_APPROVE_COUNT);
    _map.put("IRC_BACKUP1", "");
    _map.put("IRC_BACKUP2", "");
    _map.put("IRC_BACKUP3", "");
    _map.put("IRC_BACKUP4", "");
    _map.put("IRC_BACKUP5", "");
    _map.put("IRC_BACKUP6", "");
    _map.put("IRC_BACKUP7", "");
    _map.put("IRC_BACKUP8", "");
    _map.put("IRC_BACKUP9", "");
    _map.put("IRC_BACKUP10", "");
    _map.put("IRC_BACKUP11", "");
    _map.put("IRC_SENDATE", DateUtils.dateFormat(new Date(), "yyyyMMdd"));
    _map.put("IRC_SENDTIME", DateUtils.dateFormat(new Date(), "HHmmss"));
    return _map;
  }


  /**
   * 新增方法
   * @param map
   * @return
   */
  private Map<String, String> parserTRSData(Map<String, Object> map,Map<String, String> txtmap) throws IOException {
    Map<String, String> datamap = new HashMap();
    String strtemp = (String)map.get("irhkeybbsnum");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRB_SID",strtemp);
    datamap.put("IRB_HKEY",strtemp);


    strtemp = (String)map.get("datatype");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    if(strtemp.equals("1")){
      //传统新闻。原枚举值11
      strtemp = "11";
    }else if(strtemp.equals("2")){
      //微信新闻。原枚举值12
      strtemp = "12";
    }else if(strtemp.equals("3")){
      //微博新闻。原枚举值13
      strtemp = "13";
    }
    datamap.put("IRB_NEWSKIND",strtemp);


    strtemp = (String)map.get("urldate");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    //-----获取8位的日期-------
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDate date = null;
    if(StringUtils.isNotBlank(strtemp) && !strtemp.equals("null")) {
      date = LocalDate.parse(strtemp, formatter);
      formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      strtemp = date.format(formatter);
    }
    //--------------------------
    datamap.put("IRB_URLDATE",strtemp);

    strtemp = (String)map.get("pubtime");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    //----获取6位的时间----
    SimpleDateFormat formatter1=new SimpleDateFormat("HHmmss");
    SimpleDateFormat formatter2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    try {
      if(StringUtils.isNotBlank(strtemp) && !strtemp.equals("null")) {
        strtemp = formatter1.format(formatter2.parse(strtemp));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    //-------------------
    datamap.put("IRB_URLTIME",strtemp);


    strtemp = (String)map.get("savetime");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    //-----获取8位的日期-------
    formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    if(StringUtils.isNotBlank(strtemp) && !strtemp.equals("null")) {
      date = LocalDate.parse(strtemp, formatter);
      formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      strtemp = date.format(formatter);
    }
    //--------------------------
    datamap.put("IRB_LOADATE",strtemp);
    datamap.put("IRB_LASTDATE",strtemp);

    strtemp = (String)map.get("savetime");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    //----获取6位的时间----
    try {
      if(StringUtils.isNotBlank(strtemp) && !strtemp.equals("null")) {
        strtemp = formatter1.format(formatter2.parse(strtemp));
      }
    } catch (ParseException e) {
      logger.info("***************"+e+"******************");
      e.printStackTrace();
    }
    //-------------------
    datamap.put("IRB_LOADTIME",strtemp);
    datamap.put("IRC_LASTTIME",strtemp);

    strtemp = (String)map.get("irauthors");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRB_SCREEN_NAME",strtemp);


    strtemp = (String)map.get("content");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRB_CONTENT",strtemp);


    strtemp = (String)map.get("url");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRB_URLNAME",strtemp);

    strtemp = (String)map.get("trssimrank");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIMRANK",strtemp);

    strtemp = (String)map.get("trssimid");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIMID",strtemp);



    strtemp = (String)map.get("trssimvalue");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIMV",strtemp);


    strtemp = (String)map.get("trssignature");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIGNT",strtemp);


//    strtemp = (String)map.get("irkeywords");
//    if (StringUtils.isBlank(strtemp)) {
//      strtemp = "";
//    }
//    if(!StringUtils.isBlank(strtemp) && strtemp.indexOf(";")!=-1){
//      datamap.put("IRN_KEYWORD",strtemp.replace(";","|"));
//    }else {
//      datamap.put("IRN_KEYWORD",strtemp);
//    }

    datamap.put("IRN_KEYWORD","");
    datamap.put("IRB_UID","");
    datamap.put("IRB_RETWEETED_UID","");
    datamap.put("IRB_RETWEETED_URL","");
    datamap.put("IRB_RETWEETED_SCREEN_NAME","");
    datamap.put("IRB_VIA","");
    datamap.put("IRB_RTTCOUNT","");
    datamap.put("IRB_COMMTCOUNT","");
    datamap.put("IRB_APPROVE_COUNT","");
    datamap.put("IRC_BACKUP1","");
    datamap.put("IRC_BACKUP2","");
    datamap.put("IRC_BACKUP3","");
    datamap.put("IRC_BACKUP4","");
    datamap.put("IRC_BACKUP5","");
    datamap.put("IRC_BACKUP6","");
    datamap.put("IRC_BACKUP7","");
    datamap.put("IRC_BACKUP8","");
    datamap.put("IRC_BACKUP9","");
    datamap.put("IRC_BACKUP10","");
    datamap.put("IRC_BACKUP11","");
    datamap.put("IRC_SENDATE",DateUtils.dateFormat(new Date(), "yyyyMMdd"));
    datamap.put("IRC_SENDTIME",DateUtils.dateFormat(new Date(), "HHmmss"));


    //-------------------------
    strtemp = (String)map.get("trsCust");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsCust",strtemp);
//    strtemp = TRSFileUtil.strFilterToNum("trsCust",strtemp);
    String custid =TRSFileUtil.strFilterToNum("trsCust",strtemp);;
    datamap.put("CATALOG8",custid);
    datamap.put("CATALOG9",custid);

    strtemp = (String)map.get("trsCustRisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsCustRisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsCustRisk",strtemp);
    datamap.put("CATALOG12",strtemp);

    String trsArea = "";

    strtemp = (String)map.get("trsIndustry");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsIndustry",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsIndustry",strtemp);
    String[] Industrycontent = strtemp.split(";");
    String trsIndustryonly = "";
    for (int i = 0; i < Industrycontent.length; i++) {
      if(Industrycontent[i].contains("_")) {
        trsIndustryonly += Industrycontent[i].substring(0, Industrycontent[i].indexOf("_"))+";";
      }
    }
    datamap.put("CATALOG21",trsIndustryonly);
    trsArea = strtemp;

    strtemp = (String)map.get("trsInrisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsInrisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsInrisk",strtemp);
    if(!EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp).equals("")){
      strtemp = EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp);
    }
    datamap.put("CATALOG6",strtemp);
    trsArea = strtemp;


    strtemp = (String)map.get("trsArea");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsArea",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsArea",strtemp);
    String[] content = strtemp.split(";");
    String trsAreaonly = "";
    for (int i = 0; i < content.length; i++) {
      if(content[i].contains("_")) {
        trsAreaonly += content[i].substring(0, content[i].indexOf("_"))+";";
      }
    }
    datamap.put("CATALOG3",trsAreaonly);
    trsArea = strtemp;

    strtemp = (String)map.get("trsArearisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsArearisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsArearisk",strtemp);
    if(!EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp).equals("")){
      strtemp = EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp);
    }
    datamap.put("CATALOG16",strtemp);


    strtemp = (String)map.get("trsRisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsRisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsRisk",strtemp);
    datamap.put("CATALOG15",strtemp);




    strtemp = (String)map.get("trsMacrorisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsMacrorisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsMacrorisk",strtemp);
    datamap.put("CATALOG20",strtemp);







    strtemp = TRSFileUtil.positiveOrNegative((String)map.get("trsBankAppraise"),(String)map.get("trsCustInvestment"));
    datamap.put("ZFM",strtemp);
    //---------




    return datamap;
  }



  private Map<String, String> parserData(Map<String, Object> map) {
    Map<String, String> datamap = new HashMap();
    Object IRC_NEWSKIND = map.get("IRB_NEWSKIND");
    String IRC_NEWSKINDstr = "13";
    if (IRC_NEWSKIND != null) {
      IRC_NEWSKINDstr = String.valueOf(IRC_NEWSKIND);
    }

    datamap.put("IRB_NEWSKIND", IRC_NEWSKINDstr);
    Object IRC_HKEY = map.get("IRB_HKEY");
    String IRC_HKEYstr = "";
    if (IRC_HKEY != null) {
      IRC_HKEYstr = String.valueOf(IRC_HKEY);
    }

    datamap.put("IRB_HKEY", IRC_HKEYstr);
    datamap.put("IRB_SID", IRC_HKEYstr);
    Object IRC_URLTIME = map.get("IRB_URLDATE");
    String IRC_URLTIMEstr = "";
    if (IRC_URLTIME != null) {
      IRC_URLTIMEstr = String.valueOf(IRC_URLTIME);
    }

    datamap.put("IRB_URLDATE", ParserUtil.getDateFromStr(IRC_URLTIMEstr));
    datamap.put("IRB_URLTIME", ParserUtil.getTimeFromStr(IRC_URLTIMEstr));
    Object IRC_LOADTIME = map.get("IRB_LOADTIME");
    String IRC_LOADTIMEstr = "";
    if (IRC_LOADTIME != null) {
      IRC_LOADTIMEstr = String.valueOf(IRC_LOADTIME);
    }

    datamap.put("IRB_LOADATE", ParserUtil.getDateFromStr(IRC_LOADTIMEstr));
    datamap.put("IRB_LOADTIME", ParserUtil.getTimeFromStr(IRC_LOADTIMEstr));
    Object IRC_LASTTIME = map.get("IRB_LASTTIME");
    String IRC_LASTTIMEstr = "";
    if (IRC_LASTTIME != null) {
      IRC_LASTTIMEstr = String.valueOf(IRC_LASTTIME);
    }

    datamap.put("IRB_LASTDATE", ParserUtil.getDateFromStr(IRC_LASTTIMEstr));
    datamap.put("IRB_LASTTIME", ParserUtil.getTimeFromStr(IRC_LASTTIMEstr));
    Object IRB_UID = map.get("IRB_UID");
    String IRB_UIDstr = "";
    if (IRB_UID != null) {
      IRB_UIDstr = String.valueOf(IRB_UID);
    }

    datamap.put("IRB_UID", ParserUtil.parseStr(IRB_UIDstr, InitParam.RN_SM));
    Object IRB_SCREEN_NAME = map.get("IRB_SCREEN_NAME");
    String IRB_SCREEN_NAMEstr = "";
    if (IRB_SCREEN_NAME != null) {
      IRB_SCREEN_NAMEstr = String.valueOf(IRB_SCREEN_NAME);
    }

    datamap.put("IRB_SCREEN_NAME", ParserUtil.parseStr(IRB_SCREEN_NAMEstr, InitParam.RN_SM));
    Object IRB_RETWEETED_UID = map.get("IRB_RETWEETED_UID");
    String IRB_RETWEETED_UIDstr = "";
    if (IRB_RETWEETED_UID != null) {
      IRB_RETWEETED_UIDstr = String.valueOf(IRB_RETWEETED_UID);
    }

    datamap.put("IRB_RETWEETED_UID", ParserUtil.parseStr(IRB_RETWEETED_UIDstr, InitParam.RN_SM));
    Object IRB_RETWEETED_URL = map.get("IRB_RETWEETED_URL");
    String IRB_RETWEETED_URLstr = "";
    if (IRB_RETWEETED_URL != null) {
      IRB_RETWEETED_URLstr = String.valueOf(IRB_RETWEETED_URL);
    }

    datamap.put("IRB_RETWEETED_URL", ParserUtil.parseStr(IRB_RETWEETED_URLstr, InitParam.RN_SM));
    Object IRB_RETWEETED_SCREEN_NAME = map.get("IRB_RETWEETED_SCREEN_NAME");
    String IRB_RETWEETED_SCREEN_NAMEstr = "";
    if (IRB_RETWEETED_SCREEN_NAME != null) {
      IRB_RETWEETED_SCREEN_NAMEstr = String.valueOf(IRB_RETWEETED_SCREEN_NAME);
    }

    datamap.put("IRB_RETWEETED_SCREEN_NAME", ParserUtil.parseStr(IRB_RETWEETED_SCREEN_NAMEstr, InitParam.RN_SM));
    Object IRB_CONTENT = map.get("IRB_CONTENT");
    String IRB_CONTENTstr = "";
    if (IRB_CONTENT != null) {
      IRB_CONTENTstr = String.valueOf(IRB_CONTENT);
    }

    datamap.put("IRB_CONTENT", ParserUtil.parseStr(IRB_CONTENTstr, InitParam.RN_SM));
    Object IRB_URLNAME = map.get("IRB_URLNAME");
    String IRB_URLNAMEstr = "";
    if (IRB_URLNAME != null) {
      IRB_URLNAMEstr = String.valueOf(IRB_URLNAME);
    }

    datamap.put("IRB_URLNAME", ParserUtil.parseStr(IRB_URLNAMEstr, InitParam.RN_SM));
    Object IRB_VIA = map.get("IRB_VIA");
    String IRB_VIAstr = "";
    if (IRB_VIA != null) {
      IRB_VIAstr = String.valueOf(IRB_VIA);
    }

    datamap.put("IRB_VIA", ParserUtil.parseStr(IRB_VIAstr, InitParam.RN_SM));
    Object IRB_RTTCOUNT = map.get("IRB_RTTCOUNT");
    String IRB_RTTCOUNTstr = "";
    if (IRB_RTTCOUNT != null) {
      IRB_RTTCOUNTstr = String.valueOf(IRB_RTTCOUNT);
    }

    datamap.put("IRB_RTTCOUNT", ParserUtil.parseStr(IRB_RTTCOUNTstr, InitParam.RN_SM));
    Object IRB_COMMTCOUNT = map.get("IRB_COMMTCOUNT");
    String IRB_COMMTCOUNTstr = "";
    if (IRB_COMMTCOUNT != null) {
      IRB_COMMTCOUNTstr = String.valueOf(IRB_COMMTCOUNT);
    }

    datamap.put("IRB_COMMTCOUNT", ParserUtil.parseStr(IRB_COMMTCOUNTstr, InitParam.RN_SM));
    Object IRB_APPROVE_COUNT = map.get("IRB_APPROVE_COUNT");
    String IRB_APPROVE_COUNTstr = "";
    if (IRB_APPROVE_COUNT != null) {
      IRB_APPROVE_COUNTstr = String.valueOf(IRB_APPROVE_COUNT);
    }

    datamap.put("IRB_APPROVE_COUNT", ParserUtil.parseStr(IRB_APPROVE_COUNTstr, InitParam.RN_SM));
    Object CATALOG3 = map.get("CATALOG3");
    String CATALOG3str = "";
    if (CATALOG3 != null) {
      CATALOG3str = String.valueOf(CATALOG3);
    }

    datamap.put("CATALOG3", CATALOG3str);
    Object CATALOG20 = map.get("CATALOG20");
    String CATALOG20str = "";
    if (CATALOG20 != null) {
      CATALOG20str = String.valueOf(CATALOG20);
    }

    datamap.put("CATALOG20", CATALOG20str);
    Object CATALOG9 = map.get("CATALOG9");
    String CATALOG9str = "";
    if (CATALOG9 != null) {
      CATALOG9str = String.valueOf(CATALOG9);
    }

    datamap.put("CATALOG9", CATALOG9str);
    Object CATALOG21 = map.get("CATALOG21");
    String CATALOG21str = "";
    if (CATALOG21 != null) {
      CATALOG21str = String.valueOf(CATALOG21);
    }

    datamap.put("CATALOG21", CATALOG21str);
    Object ZFM = map.get("ZFM");
    String ZFMstr = "";
    if (ZFM != null) {
      ZFMstr = String.valueOf(ZFM);
    }

    Object IRC_KEYWORD = map.get("IRC_KEYWORD");
    String IRC_KEYWORDtr = "";
    if (IRC_KEYWORD != null) {
      IRC_KEYWORDtr = String.valueOf(IRC_KEYWORD);
      IRC_KEYWORDtr = IRC_KEYWORDtr.replace(";", "|");
    }

    datamap.put("IRC_KEYWORD", IRC_KEYWORDtr);
    Object CATALOG1 = map.get("CATALOG1");
    String CATALOG1str = "";
    if (CATALOG1 != null) {
      CATALOG1str = String.valueOf(CATALOG1);
    }

    datamap.put("CATALOG1", CATALOG1str);
    Object CATALOG11 = map.get("CATALOG11");
    String CATALOG11str = "";
    if (CATALOG11 != null) {
      CATALOG11str = String.valueOf(CATALOG11);
    }

    datamap.put("CATALOG11", CATALOG11str);
    Object CATALOG12 = map.get("CATALOG12");
    String CATALOG12str = "";
    if (CATALOG12 != null) {
      CATALOG12str = String.valueOf(CATALOG12);
    }

    datamap.put("CATALOG12", CATALOG12str);
    datamap.put("IRC_BACKUP1", "");
    datamap.put("IRC_BACKUP2", "");
    datamap.put("IRC_BACKUP3", "");
    datamap.put("IRC_BACKUP4", "");
    datamap.put("IRC_BACKUP5", "");
    datamap.put("IRC_BACKUP6", "");
    datamap.put("IRC_BACKUP7", "");
    datamap.put("IRC_BACKUP8", "");
    datamap.put("IRC_BACKUP9", "");
    datamap.put("IRC_BACKUP10", "");
    datamap.put("IRC_BACKUP11", "");
    datamap.put("IRC_SENDATE", DateUtils.dateFormat(new Date(), "yyyyMMdd"));
    datamap.put("IRC_SENDTIME", DateUtils.dateFormat(new Date(), "HHmmss"));
    datamap.put("ZFM", ZFMstr);
    return datamap;
  }

  private void readfilelist() {
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    File f = new File(InitParam.WEIBOTO_PATH + "/" + dateStr);
    if (!f.exists()) {
      f.mkdirs();
    }

    if (f.listFiles() == null || f.listFiles().length == 0) {
      this.createblank(true);
    }

    File fs = new File(InitParam.SPILTTO_PATH + "/" + dateStr);
    if (!fs.exists()) {
      fs.mkdirs();
    }

    File fsu = new File(InitParam.UNION_PATH + "/" + dateStr);
    if (!fsu.exists()) {
      fsu.mkdirs();
    }

    File newspath = new File(InitParam.WEIBO_PATH);
    this.oklist = newspath.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith("ok");
      }
    });
  }
}
