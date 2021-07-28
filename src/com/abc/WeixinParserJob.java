//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

import com.abc.util.EnterpriseNameUtil;
import com.abc.util.TRSFileUtil;
import com.abc.util.ZipUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class WeixinParserJob extends TimerTask {
  private static final Logger logger = Logger.getLogger(WeixinParserJob.class);
  private File[] oklist = null;

  public WeixinParserJob() {
  }

  public void run() {

//    logger.info("微信数据拆分任务开始");
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
//    String weixinFile = InitParam.WEIXIN_PATH+"/";
//    ZipUtil.delAllFile(weixinFile);
//    logger.info("微信数据拆分任务结束");
  }



  public static void WeiXinrun(Map<String,String> txtmap) {
    WeixinParserJob weixinParserJob = new WeixinParserJob();
    logger.info("微信数据拆分任务开始");
    weixinParserJob.readfilelist();
    File[] var4 = weixinParserJob.oklist;
    int var3 = weixinParserJob.oklist.length;

    for(int var2 = 0; var2 < var3; ++var2) {
      File okfile = var4[var2];
      try {
        if (weixinParserJob.readtrsfile(okfile,txtmap)) {
          okfile.delete();
        }
      } catch (IOException e) {
        logger.info("***************"+e+"******************");
        e.printStackTrace();
      }
    }
    String weixinFile = InitParam.WEIXIN_PATH+"/";
    ZipUtil.delAllFile(weixinFile);
    logger.info("微信数据拆分任务结束");
  }

  private void createblank(boolean one) {
    String uuid = UUID.randomUUID().toString();
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    String weixnfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + ".txt";
    String weixngzfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
    String weixnokfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
    FileWriterWithEncoding weixnwriter = null;
    FileWriterWithEncoding weixnmd5writer = null;

    try {
      if (one) {
        weixnwriter = new FileWriterWithEncoding(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixnfilename, InitParam.CHARSET, true);
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
        GZIPUtil.compressFile(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixnfilename, InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixngzfilename);
        File txtf = new File(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixnfilename);
        txtf.delete();
        weixnmd5writer = new FileWriterWithEncoding(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixnokfilename, InitParam.CHARSET, true);
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
    File trsfile = new File(InitParam.WEIXIN_PATH + "/" + filename);
    if (trsfile.exists()) {
      logger.info(trsfile.getAbsoluteFile());
    }

    List<Map<String, Object>> dataList = TRSParser.readFile(trsfile, "GBK", InitParam.CP, InitParam.WEIXINCP_PATH);
    //新增用来初始化数据，和企业名单碰撞
      dataList =TRSFileUtil.initListData(dataList,txtmap);
    if (dataList != null && dataList.size() != 0) {
      String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
      String uuid = UUID.randomUUID().toString();
      String weixinfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + ".txt";
      String splitfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + ".txt";
      String unionfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + ".txt";
      String weixingzfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String splitgzfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String uniongzfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
      String weixinokfilename = InitParam.WX_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      String splitokfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      String unionokfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
      FileWriterWithEncoding weixinwriter = null;
      FileWriterWithEncoding weixinsplitwriter = null;
      FileWriterWithEncoding weixinunionwriter = null;
      FileWriterWithEncoding weixinmd5writer = null;
      FileWriterWithEncoding weixinsplitmd5writer = null;
      FileWriterWithEncoding weixinunionmd5writer = null;

      File txtf;
      File txgztf;
      File txtfs;
      try {
        txtf = new File(InitParam.WEIXINTO_PATH + "/" + dateStr);
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

        weixinwriter = new FileWriterWithEncoding(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixinfilename, InitParam.CHARSET, true);
        weixinsplitwriter = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.CHARSET, true);
        weixinunionwriter = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.CHARSET, true);
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
              if (weixinwriter != null) {
                weixinwriter.flush();
                weixinwriter.close();
              }

              if (weixinunionwriter != null) {
                weixinunionwriter.flush();
                weixinunionwriter.close();
              }

              if (weixinsplitwriter != null) {
                weixinsplitwriter.flush();
                weixinsplitwriter.close();
              }
            } catch (IOException var36) {
              logger.error(var36.getMessage());
              return false;
            }

            try {
              GZIPUtil.compressFile(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixinfilename, InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixingzfilename);
              txtf = new File(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixinfilename);
              txtf.delete();
              weixinmd5writer = new FileWriterWithEncoding(InitParam.WEIXINTO_PATH + "/" + dateStr + "/" + weixinokfilename, InitParam.CHARSET, true);
              weixinmd5writer.write("");
              weixinmd5writer.flush();
              weixinmd5writer.close();
              GZIPUtil.compressFile(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.UNION_PATH + "/" + dateStr + "/" + uniongzfilename);
              txgztf = new File(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename);
              txgztf.delete();
              weixinunionmd5writer = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionokfilename, InitParam.CHARSET, true);
              weixinunionmd5writer.write("");
              weixinunionmd5writer.flush();
              weixinunionmd5writer.close();
              GZIPUtil.compressFile(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitgzfilename);
              txtfs = new File(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename);
              txtfs.delete();
              weixinsplitmd5writer = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitokfilename, InitParam.CHARSET, true);
              weixinsplitmd5writer.write("");
              weixinsplitmd5writer.flush();
              weixinsplitmd5writer.close();
              logger.info("成功转换数据文件:" + filename);
              return true;
            } catch (IOException var35) {
              logger.error(var35.getMessage());
              return false;
            }
          }

          Map<String, Object> map = (Map)var39.next();
//          datamap = this.parserData(map);
          //新增
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
        String[] var30 = InitParam.WEIXIN_FILEDSES;
        int var29 = InitParam.WEIXIN_FILEDSES.length;

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
          this.write(weixinwriter, line);
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

        this.write(weixinunionwriter, line_);

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

          this.write(weixinsplitwriter, slitLine);
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
  private Map<String, String> parserTRSUnionMap(Map<String, String> map) {
    Map<String, String> datamap = new HashMap();
    String []mapkey   = {"IRU_SID","IRU_NEWSKIND","IRU_HKEY","IRU_OLDURLNAME","IRU_URLDATE","IRU_URLTIME","IRU_LOADATE","IRU_LOADTIME","IRU_LASTDATE","IRU_LASTTIME","IRU_AUTHORS","IRU_RDCOUNT","IRU_PRCOUNT","IRU_RANK","IRU_KEYWORD","IRU_CONTENT","IRU_BACKUP1","IRU_BACKUP2","IRU_BACKUP3","IRU_BACKUP4","IRU_BACKUP5","IRU_BACKUP6","IRU_BACKUP7","IRU_BACKUP8","IRU_BACKUP9","IRU_BACKUP10","IRU_BACKUP11","IRU_SENDATE","IRU_SENDTIME","IRU_SIMRANK","IRU_SIMID","IRU_SIMV","IRU_SIGNT"};

    String []mapvalue = {"IRC_SID","IRC_NEWSKIND","IRC_HKEY","IRC_URLNAME","IRC_URLDATE","IRC_URLTIME","IRC_LOADATE","IRC_LOADTIME","IRC_LASTDATE","IRC_LASTTIME","IRC_AUTHORS","IRC_RDCOUNT","IRC_PRCOUNT","IRC_RANK","IRC_KEYWORD","IRC_CONTENT","IRC_BACKUP1","IRC_BACKUP2","IRC_BACKUP3","IRC_BACKUP4","IRC_BACKUP5","IRC_BACKUP6","IRC_BACKUP7","IRC_BACKUP8","IRC_BACKUP9","IRC_BACKUP10","IRC_BACKUP11","IRC_SENDATE","IRC_SENDTIME","IRC_SIMRANK","IRC_SIMID","IRC_SIMV","IRC_SIGNT"};
    for (int j = 0; j < mapkey.length; j++) {
      String trsstr = (String)map.get(mapvalue[j]);
      if (trsstr == null) {
        trsstr = "";
      }
      datamap.put(mapkey[j], trsstr);
    }
    //整合表里面没有值的   全部设置为空
    String []mapkon ={"IRU_AUTHORID","IRU_RETWEETED_SCREEN_ID","IRU_RETWEETED_SCREEN_NAME","IRU_RETWEETED_URL","IRU_URLTITLE","IRU_abstract","IRU_SITENAME","channel","IRU_SRCNAME","IRU_VIA","IRU_RTTCOUNT","IRU_COMMTCOUNT","IRU_GROUPNAME","IRU_BBSNUM","IRU_NRESERVED2","IRU_NRESERVED3","IRU_BACKUP12"};
    for (int j = 0; j < mapkon.length; j++) {
      datamap.put(mapkon[j], "");
    }

    return datamap;
  }

  private Map<String, String> parserUnionMap(Map<String, String> map) {
    Map<String, String> _map = new HashMap();
    String IRC_SID = (String)map.get("IRC_SID");
    if (IRC_SID == null) {
      IRC_SID = "";
    }

    _map.put("IRC_SID", IRC_SID);
    String IRC_NEWSKIND = (String)map.get("IRC_NEWSKIND");
    if (IRC_NEWSKIND == null) {
      IRC_NEWSKIND = "";
    }

    _map.put("IRC_NEWSKIND", IRC_NEWSKIND);
    String IRC_HKEY = (String)map.get("IRC_HKEY");
    if (IRC_HKEY == null) {
      IRC_HKEY = "";
    }

    _map.put("IRC_HKEY", IRC_HKEY);
    String IRC_URLDATE = (String)map.get("IRC_URLDATE");
    if (IRC_URLDATE == null) {
      IRC_URLDATE = "";
    }

    _map.put("IRC_URLDATE", IRC_URLDATE);
    String IRC_URLTIME = (String)map.get("IRC_URLTIME");
    if (IRC_URLTIME == null) {
      IRC_URLTIME = "";
    }

    _map.put("IRC_URLTIME", IRC_URLTIME);
    String IRC_LOADATE = (String)map.get("IRC_LOADATE");
    if (IRC_LOADATE == null) {
      IRC_LOADATE = "";
    }

    _map.put("IRC_LOADATE", IRC_LOADATE);
    String IRC_LOADTIME = (String)map.get("IRC_LOADTIME");
    if (IRC_LOADTIME == null) {
      IRC_LOADTIME = "";
    }

    _map.put("IRC_LOADTIME", IRC_LOADTIME);
    String IRC_LASTDATE = (String)map.get("IRC_LASTDATE");
    if (IRC_LASTDATE == null) {
      IRC_LASTDATE = "";
    }

    _map.put("IRC_LASTDATE", IRC_LASTDATE);
    String IRC_LASTTIME = (String)map.get("IRC_LASTTIME");
    if (IRC_LASTTIME == null) {
      IRC_LASTTIME = "";
    }

    _map.put("IRC_LASTTIME", IRC_LASTTIME);
    String IRC_wechatname = (String)map.get("IRC_wechatname");
    if (IRC_wechatname == null) {
      IRC_wechatname = "";
    }

    _map.put("IRC_wechatname", IRC_wechatname);
    String IRC_wechatid = (String)map.get("IRC_wechatid");
    if (IRC_wechatid == null) {
      IRC_wechatid = "";
    }

    _map.put("IRC_wechatid", IRC_wechatid);
    String IRC_AUTHORS = (String)map.get("IRC_AUTHORS");
    if (IRC_AUTHORS == null) {
      IRC_AUTHORS = "";
    }

    _map.put("IRC_AUTHORS", IRC_AUTHORS);
    String IRC_URLTITLE = (String)map.get("IRC_URLTITLE");
    if (IRC_URLTITLE == null) {
      IRC_URLTITLE = "";
    }

    _map.put("IRC_URLTITLE", IRC_URLTITLE);
    String IRC_abstract = (String)map.get("IRC_abstract");
    if (IRC_abstract == null) {
      IRC_abstract = "";
    }

    _map.put("IRC_abstract", IRC_abstract);
    String IRC_KEYWORD = (String)map.get("IRC_KEYWORD");
    if (IRC_KEYWORD == null) {
      IRC_KEYWORD = "";
    }

    _map.put("IRB_KEYWORD", IRC_KEYWORD);
    String IRC_CONTENT = (String)map.get("IRC_CONTENT");
    if (IRC_CONTENT == null) {
      IRC_CONTENT = "";
    }

    _map.put("IRC_CONTENT", IRC_CONTENT);
    String IRC_URLNAME = (String)map.get("IRC_URLNAME");
    if (IRC_URLNAME == null) {
      IRC_URLNAME = "";
    }

    _map.put("IRC_URLNAME", IRC_URLNAME);
    String IRC_RANK = (String)map.get("IRC_RANK");
    if (IRC_RANK == null) {
      IRC_RANK = "";
    }

    _map.put("IRC_RANK", IRC_RANK);
    String IRC_RDCOUNT = (String)map.get("IRC_RDCOUNT");
    if (IRC_RDCOUNT == null) {
      IRC_RDCOUNT = "";
    }

    _map.put("IRC_RDCOUNT", IRC_RDCOUNT);
    String IRC_PRCOUNT = (String)map.get("IRC_PRCOUNT");
    if (IRC_PRCOUNT == null) {
      IRC_PRCOUNT = "";
    }

    _map.put("IRC_PRCOUNT", IRC_PRCOUNT);
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

  private List<Map<String, String>> splitData(Map<String, String> data) {
    List<Map<String, String>> dataList = new ArrayList();
//    String CATALOG3 = (String)data.get("CATALOG3");
////    String CATALOG1 = (String)data.get("CATALOG1");
//    String CATALOG12 = (String)data.get("CATALOG12");
//    String CATALOG16 = (String)data.get("CATALOG16");
//    String CATALOG9 = (String)data.get("CATALOG9");
//    String CATALOG20 = (String)data.get("CATALOG20");
//    String CATALOG21 = (String)data.get("CATALOG21");
//    String CATALOG11 = (String)data.get("CATALOG11");
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
    String IRN_SID = (String)data.get("IRC_SID");
    String IRA_SENDATE = (String)data.get("IRC_SENDATE");
    String IRA_SENDTIME = (String)data.get("IRC_SENDTIME");
    String IRC_URLDATE = (String)data.get("IRC_URLDATE");
    List<String> areaes = ParserUtil.getAreas(CATALOG3);
    String APC_SIMRANK = (String)data.get("IRC_SIMRANK");
    String APC_SIMID = (String)data.get("IRC_SIMID");
    String APC_SIMV = (String)data.get("IRC_SIMV");
    String APV_SIGNT = (String)data.get("IRC_SIGNT");
    List industries;
    if (areaes != null && areaes.size() > 0) {
      industries = ParserUtil.parseAreaRisk(areaes, CATALOG16, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRC_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!industries.isEmpty()) {
        dataList.addAll(industries);
        num += industries.size();
      }
    }

    industries = ParserUtil.getIndustrys(CATALOG21);
    if (industries != null && industries.size() > 0) {
      List<Map<String, String>> list = ParserUtil.parseIndustryRisk(industries, CATALOG11, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRC_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
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
      List<Map<String, String>> list = ParserUtil.parseMacroRisk(macroes, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRC_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
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
      list = ParserUtil.parseCustRisk(companies, CATALOG12, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRC_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!list.isEmpty()) {
        dataList.addAll(list);
        num += list.size();
      }
    }

//    list = ParserUtil.parseZFMRisk(IRA_publicsentiment, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "12", IRC_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
//    if (!list.isEmpty()) {
//      dataList.addAll(list);
//      int var10000 = num + list.size();
//    }

    return dataList;
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
    datamap.put("IRC_SID",strtemp);
    datamap.put("IRC_HKEY",strtemp);

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
    datamap.put("IRC_NEWSKIND",strtemp);

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
    datamap.put("IRC_URLDATE",strtemp);

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
    datamap.put("IRC_URLTIME",strtemp);

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
    datamap.put("IRC_LOADATE",strtemp);
    datamap.put("IRC_LASTDATE",strtemp);

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
    datamap.put("IRC_LOADTIME",strtemp);
    datamap.put("IRC_LASTTIME",strtemp);


    strtemp = (String)map.get("irauthors");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_wechatname",strtemp);
    datamap.put("IRC_wechatid","");

    strtemp = (String)map.get("irsrcname");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_AUTHORS",strtemp);

    strtemp = (String)map.get("title");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_URLTITLE",strtemp);

    strtemp = (String)map.get("trsAbstract");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_abstract",strtemp);


    strtemp = (String)map.get("irkeywords");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    if(!StringUtils.isBlank(strtemp) && strtemp.indexOf(";")!=-1){
      datamap.put("IRN_KEYWORD",strtemp.replace(";","|"));
    }else {
      datamap.put("IRN_KEYWORD",strtemp);
    }

    strtemp = (String)map.get("content");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_CONTENT",strtemp);


    strtemp = (String)map.get("url");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_URLNAME",strtemp);


    datamap.put("IRC_RANK","");
    datamap.put("IRC_RDCOUNT","");
    datamap.put("IRC_PRCOUNT","");
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


    //------------------------------------
//-------------------------
    String trsArea = "";
    strtemp = (String)map.get("trsCust");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsCust",strtemp);
//    strtemp = TRSFileUtil.strFilterToNum("trsCust",strtemp);
    String custid =TRSFileUtil.strFilterToNum("trsCust",strtemp);
    datamap.put("CATALOG8",custid);
    datamap.put("CATALOG9",strtemp);

    strtemp = (String)map.get("trsCustRisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

    strtemp = TRSFileUtil.trsJsonToStr("trsCustRisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsCustRisk",strtemp);
    datamap.put("CATALOG12",strtemp);


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





    return datamap;
  }


  private Map<String, String> parserData(Map<String, Object> map) {
    Map<String, String> datamap = new HashMap();
    Object IRC_NEWSKIND = map.get("IRC_NEWSKIND");
    String IRC_NEWSKINDstr = "12";
    if (IRC_NEWSKIND != null) {
      IRC_NEWSKINDstr = String.valueOf(IRC_NEWSKIND);
    }

    datamap.put("IRC_NEWSKIND", IRC_NEWSKINDstr);
    Object IRC_HKEY = map.get("IRC_HKEY");
    String IRC_HKEYstr = "";
    if (IRC_HKEY != null) {
      IRC_HKEYstr = String.valueOf(IRC_HKEY);
    }

    datamap.put("IRC_HKEY", IRC_HKEYstr);
    datamap.put("IRC_SID", IRC_HKEYstr);
    Object IRC_URLDATE = map.get("IRC_URLDATE");
    String IRC_URLDATEstr = "";
    if (IRC_URLDATE != null) {
      IRC_URLDATEstr = String.valueOf(IRC_URLDATE);
    }

    datamap.put("IRC_URLDATE", ParserUtil.getDateFromStr(IRC_URLDATEstr));
    Object IRC_URLTIME = map.get("IRC_URLTIME");
    String IRC_URLTIMEstr = "";
    if (IRC_URLTIME != null) {
      IRC_URLTIMEstr = String.valueOf(IRC_URLTIME);
    }

    datamap.put("IRC_URLTIME", ParserUtil.getTimeFromStr(IRC_URLTIMEstr));
    Object IRC_LOADTIME = map.get("IRC_LOADTIME");
    String IRC_LOADTIMEstr = "";
    if (IRC_LOADTIME != null) {
      IRC_LOADTIMEstr = String.valueOf(IRC_LOADTIME);
    }

    datamap.put("IRC_LOADATE", ParserUtil.getDateFromStr(IRC_LOADTIMEstr));
    datamap.put("IRC_LOADTIME", ParserUtil.getTimeFromStr(IRC_LOADTIMEstr));
    Object IRC_LASTTIME = map.get("IRC_LASTTIME");
    String IRC_LASTTIMEstr = "";
    if (IRC_LASTTIME != null) {
      IRC_LASTTIMEstr = String.valueOf(IRC_LASTTIME);
    }

    datamap.put("IRC_LASTDATE", ParserUtil.getDateFromStr(IRC_LASTTIMEstr));
    datamap.put("IRC_LASTTIME", ParserUtil.getTimeFromStr(IRC_LASTTIMEstr));
    Object IRC_wechatname = map.get("IRC_wechatname");
    String IRC_wechatnamestr = "";
    if (IRC_wechatname != null) {
      IRC_wechatnamestr = String.valueOf(IRC_wechatname);
    }

    datamap.put("IRC_wechatname", ParserUtil.parseStr(IRC_wechatnamestr, InitParam.RN_SM));
    Object IRC_wechatid = map.get("IRC_wechatid");
    String IRC_wechatidstr = "";
    if (IRC_wechatid != null) {
      IRC_wechatidstr = String.valueOf(IRC_wechatid);
    }

    datamap.put("IRC_wechatid", ParserUtil.parseStr(IRC_wechatidstr, InitParam.RN_SM));
    Object IRC_AUTHORS = map.get("IRC_AUTHORS");
    String IRC_AUTHORSstr = "";
    if (IRC_AUTHORS != null) {
      IRC_AUTHORSstr = String.valueOf(IRC_AUTHORS);
    }

    datamap.put("IRC_AUTHORS", ParserUtil.parseStr(IRC_AUTHORSstr, InitParam.RN_SM));
    Object IRC_URLTITLE = map.get("IRC_URLTITLE");
    String IRC_URLTITLEstr = "";
    if (IRC_URLTITLE != null) {
      IRC_URLTITLEstr = String.valueOf(IRC_URLTITLE);
    }

    datamap.put("IRC_URLTITLE", ParserUtil.parseStr(IRC_URLTITLEstr, InitParam.RN_SM));
    Object IRC_abstract = map.get("IRC_abstract");
    String IRC_abstractstr = "";
    if (IRC_abstract != null) {
      IRC_abstractstr = String.valueOf(IRC_abstract);
    }

    datamap.put("IRC_abstract", ParserUtil.parseStr(IRC_abstractstr, InitParam.RN_SM));
    Object IRC_CONTENT = map.get("IRC_CONTENT");
    String IRC_CONTENTstr = "";
    if (IRC_CONTENT != null) {
      IRC_CONTENTstr = String.valueOf(IRC_CONTENT);
    }

    datamap.put("IRC_CONTENT", ParserUtil.parseStr(IRC_CONTENTstr, InitParam.RN_SM));
    Object IRC_URLNAME = map.get("IRC_URLNAME");
    String IRC_URLNAMEstr = "";
    if (IRC_URLNAME != null) {
      IRC_URLNAMEstr = String.valueOf(IRC_URLNAME);
    }

    datamap.put("IRC_URLNAME", ParserUtil.parseStr(IRC_URLNAMEstr, InitParam.RN_SM));
    Object IRC_RANK = map.get("IRC_RANK");
    String IRC_RANKstr = "";
    if (IRC_RANK != null) {
      IRC_RANKstr = String.valueOf(IRC_RANK);
    }

    datamap.put("IRC_RANK", ParserUtil.parseStr(IRC_RANKstr, InitParam.RN_SM));
    Object IRC_RDCOUNT = map.get("IRC_RDCOUNT");
    String IRC_RDCOUNTstr = "";
    if (IRC_RDCOUNT != null) {
      IRC_RDCOUNTstr = String.valueOf(IRC_RDCOUNT);
    }

    datamap.put("IRC_RDCOUNT", ParserUtil.parseStr(IRC_RDCOUNTstr, InitParam.RN_SM));
    Object IRC_PRCOUNT = map.get("IRC_PRCOUNT");
    String IRC_PRCOUNTstr = "";
    if (IRC_PRCOUNT != null) {
      IRC_PRCOUNTstr = String.valueOf(IRC_PRCOUNT);
    }

    datamap.put("IRC_PRCOUNT", ParserUtil.parseStr(IRC_PRCOUNTstr, InitParam.RN_SM));
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
    File f = new File(InitParam.WEIXINTO_PATH + "/" + dateStr);
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

    File newspath = new File(InitParam.WEIXIN_PATH);
    this.oklist = newspath.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith("ok");
      }
    });
  }
}
