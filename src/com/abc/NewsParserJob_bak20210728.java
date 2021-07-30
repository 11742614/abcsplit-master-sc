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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class NewsParserJob_bak20210728 extends TimerTask {
  private static final Logger logger = Logger.getLogger(NewsParserJob_bak20210728.class);
  private File[] oklist = null;
  public static ConcurrentMap<String, String> custMaps = new ConcurrentHashMap();
  public NewsParserJob_bak20210728() {
  }

  public void run() {
//    logger.info("新闻论坛数据拆分任务开始");
//    this.readfilelist();
//    File[] var4 = this.oklist;
//    int var3 = this.oklist.length;
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
//    String newsFile = InitParam.NEWS_PATH+"/";
//    ZipUtil.delAllFile(newsFile);
//    logger.info("新闻论坛数据拆分任务结束");

  }

  public static void newRun(Map<String,String> txtmap) {
    logger.info("新闻论坛数据拆分任务开始-");
    NewsParserJob_bak20210728 newsParserJob = new NewsParserJob_bak20210728();
    newsParserJob.readfilelist();
    File[] var4 = newsParserJob.oklist;
    int var3 = newsParserJob.oklist.length;
    for(int var2 = 0; var2 < var3; ++var2) {
      System.out.println("新闻论坛数据拆共"+var3+"个"+"正在处理第"+var2+"个");
      File okfile = var4[var2];
      try {
        if (newsParserJob.readtrsfile(okfile,txtmap)) {
          okfile.delete();
        }
      } catch (IOException e) {
        logger.info("***************"+e+"******************");
        e.printStackTrace();
      }
    }
    String newsFile = InitParam.NEWS_PATH+"/";
    ZipUtil.delAllFile(newsFile);
    logger.info("新闻论坛数据拆分任务结束");

  }

  private void createblank(boolean one, boolean two, boolean three) {
    String uuid = UUID.randomUUID().toString();
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    String newsfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + ".txt";
    String splitfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + ".txt";
    String unionfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + ".txt";
    String newsgzfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
    String splitgzfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
    String uniongzfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
    String newsokfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
    String splitokfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
    String unionokfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
    FileWriterWithEncoding newswriter = null;
    FileWriterWithEncoding newssplitwriter = null;
    FileWriterWithEncoding newsunionwriter = null;
    FileWriterWithEncoding newsmd5writer = null;
    FileWriterWithEncoding newssplitmd5writer = null;
    FileWriterWithEncoding newsunionmd5writer = null;

    try {
      if (one) {
        newswriter = new FileWriterWithEncoding(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename, InitParam.CHARSET, true);
      }

      if (two) {
        newssplitwriter = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.CHARSET, true);
      }

      if (three) {
        newsunionwriter = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.CHARSET, true);
      }
    } catch (IOException var24) {
      logger.error(var24.getMessage());
    }

    try {
      if (newswriter != null && one) {
        newswriter.append("");
        newswriter.flush();
        newswriter.close();
      }

      if (newssplitwriter != null && two) {
        newssplitwriter.append("");
        newssplitwriter.flush();
        newssplitwriter.close();
      }

      if (newsunionwriter != null && three) {
        newsunionwriter.append("");
        newsunionwriter.flush();
        newsunionwriter.close();
      }
    } catch (IOException var23) {
      logger.error(var23.getMessage());
    }

    try {
      File txtfs;
      if (one) {
        GZIPUtil.compressFile(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename, InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsgzfilename);
        txtfs = new File(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename);
        txtfs.delete();
        newsmd5writer = new FileWriterWithEncoding(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsokfilename, InitParam.CHARSET, true);
        newsmd5writer.write("");
        newsmd5writer.flush();
        newsmd5writer.close();
      }

      if (two) {
        GZIPUtil.compressFile(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.UNION_PATH + "/" + dateStr + "/" + uniongzfilename);
        txtfs = new File(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename);
        txtfs.delete();
        newsunionmd5writer = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionokfilename, InitParam.CHARSET, true);
        newsunionmd5writer.write("");
        newsunionmd5writer.flush();
        newsunionmd5writer.close();
      }

      if (three) {
        GZIPUtil.compressFile(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitgzfilename);
        txtfs = new File(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename);
        txtfs.delete();
        newssplitmd5writer = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitokfilename, InitParam.CHARSET, true);
        newssplitmd5writer.write("");
        newssplitmd5writer.flush();
        newssplitmd5writer.close();
      }
    } catch (IOException var22) {
      logger.error(var22.getMessage());
    }

  }

  private boolean readtrsfile(File okfile,Map<String,String> txtmap) throws IOException {
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    String filename = okfile.getName().replace(".ok", ".trs");
    File trsfile = new File(InitParam.NEWS_PATH + "/" + filename);
    if (!trsfile.exists()) {
      return false;
    } else {
      logger.info(trsfile.getAbsoluteFile());
      List dataList = TRSParser.readFile(trsfile, "GBK", InitParam.CP, InitParam.NEWSCP_PATH);
      //新增用来初始化数据，和企业名单碰撞
      dataList =TRSFileUtil.initListData(dataList,txtmap);
      if (dataList != null && dataList.size() != 0) {
        String uuid = UUID.randomUUID().toString();
        String newsfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + ".txt";
        String splitfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + ".txt";
        String unionfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + ".txt";
        String newsgzfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
        String splitgzfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
        String uniongzfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.GZIP;
        String newsokfilename = InitParam.NEWS_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
        String splitokfilename = InitParam.SP_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
        String unionokfilename = InitParam.UN_TP + "-" + dateStr + "-" + uuid + "." + InitParam.CTRL;
        FileWriterWithEncoding newswriter = null;
        FileWriterWithEncoding newssplitwriter = null;
        FileWriterWithEncoding newsunionwriter = null;
        FileWriterWithEncoding newsmd5writer = null;
        FileWriterWithEncoding newssplitmd5writer = null;
        FileWriterWithEncoding newsunionmd5writer = null;

        try {
          newswriter = new FileWriterWithEncoding(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename, InitParam.CHARSET, true);
          newssplitwriter = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.CHARSET, true);
          newsunionwriter = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.CHARSET, true);
        } catch (IOException var36) {
          logger.error(var36.getMessage());
          return false;
        }

        Iterator var23 = dataList.iterator();

        label124:
        while(true) {
          Map datamap;
          do {
            if (!var23.hasNext()) {
              try {
                if (newswriter != null) {
                  newswriter.flush();
                  newswriter.close();
                }

                if (newsunionwriter != null) {
                  newsunionwriter.flush();
                  newsunionwriter.close();
                }

                if (newssplitwriter != null) {
                  newssplitwriter.flush();
                  newssplitwriter.close();
                }
              } catch (IOException var37) {
                logger.error(var37.getMessage());
                return false;
              }

              try {
                GZIPUtil.compressFile(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename, InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsgzfilename);
                File txtf = new File(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsfilename);
                txtf.delete();
                newsmd5writer = new FileWriterWithEncoding(InitParam.NEWSTO_PATH + "/" + dateStr + "/" + newsokfilename, InitParam.CHARSET, true);
                newsmd5writer.write("");
                newsmd5writer.flush();
                newsmd5writer.close();
                GZIPUtil.compressFile(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename, InitParam.UNION_PATH + "/" + dateStr + "/" + uniongzfilename);
                File txgztf = new File(InitParam.UNION_PATH + "/" + dateStr + "/" + unionfilename);
                txgztf.delete();
                newsunionmd5writer = new FileWriterWithEncoding(InitParam.UNION_PATH + "/" + dateStr + "/" + unionokfilename, InitParam.CHARSET, true);
                newsunionmd5writer.write("");
                newsunionmd5writer.flush();
                newsunionmd5writer.close();
                GZIPUtil.compressFile(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename, InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitgzfilename);
                File txtfs = new File(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitfilename);
                txtfs.delete();
                newssplitmd5writer = new FileWriterWithEncoding(InitParam.SPILTTO_PATH + "/" + dateStr + "/" + splitokfilename, InitParam.CHARSET, true);
                newssplitmd5writer.write("");
                newssplitmd5writer.flush();
                newssplitmd5writer.close();
                logger.info("成功转换数据文件:" + filename);
                return true;
              } catch (IOException var35) {
                logger.error(var35.getMessage());
                return false;
              }
            }

            Map<String, Object> map = (Map)var23.next();
            //新增
            datamap = this.processTRSData(map,txtmap);
            for(Object key:datamap.keySet()){
              String keyy = key.toString();
              String value = datamap.get(keyy).toString();
              if(!StringUtils.isNotBlank(value) || value.equals("null")){
                value = "";
              }
              datamap.put(key,value);
            }
//            datamap = this.parserData(map);
          } while(datamap == null);
          //--1.5分析表----------
          List<Map<String, String>> splits = this.splitData(datamap);

          if(splits.size()==0) {
//            String APC_SARSLTYPE = splits.get(i).get("APC_SARSLTYPE");
            Map tempmap = new HashMap();
            datamap = tempmap;
          }
          //--------1.1-1.3原始表---------
          String line = "";
          int i = 0;
          String[] var30 = InitParam.NEWS_FILEDSES;
          int var29 = InitParam.NEWS_FILEDSES.length;

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
            this.write(newswriter, line);
          }
          //--------1.1-1.3原始表结束---------


//        Map<String, String> dataMaps = this.parserUnionMap(datamap);
          //新增方法

          //----1.4整合表--------
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
            String datatemp = (String)dataMaps.get(param);
            if (StringUtils.isNotBlank(datatemp)) {
              line_ = line_ + (String)dataMaps.get(param);
            } else {
              line_ = line_;
            }

            ++n;
          }

          this.write(newsunionwriter, line_);
          //----1.4整合表结束--------


//--1.5分析表----------
          Iterator var46 = splits.iterator();
          while(true) {
            Map map2;
            do {
              if (!var46.hasNext()) {
                continue label124;
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

            this.write(newssplitwriter, slitLine);
          }
          //--1.5分析表结束----------



        }
      } else {
        return false;
      }
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
    //trsmacrorisk
    String CATALOG20 = (String)data.get("CATALOG20");
    //trsIndustry
    String CATALOG21 = (String)data.get("CATALOG21");
    //trsInrisk
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
    String IRN_SID = (String)data.get("IRN_SID");
    String IRA_SENDATE = (String)data.get("IRN_SENDATE");
    String IRA_SENDTIME = (String)data.get("IRN_SENDTIME");
    List<String> areaes = ParserUtil.getAreas(CATALOG3);
    String IRN_URLDATE = (String)data.get("IRN_URLDATE");
    String APC_SIMRANK = (String)data.get("IRC_SIMRANK");
    String APC_SIMID = (String)data.get("IRC_SIMID");
    String APC_SIMV = (String)data.get("IRC_SIMV");
    String APV_SIGNT = (String)data.get("IRC_SIGNT");
    List industries;
    if (areaes != null && areaes.size() > 0) {
      industries = ParserUtil.parseAreaRisk(areaes, CATALOG16, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "11", IRN_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!industries.isEmpty()) {
        dataList.addAll(industries);
        num += industries.size();
      }
    }

    industries = ParserUtil.getIndustrys(CATALOG21);
    if (industries != null && industries.size() > 0) {
      List<Map<String, String>> list = ParserUtil.parseIndustryRisk(industries, CATALOG11, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "11", IRN_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
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
      List<Map<String, String>> list = ParserUtil.parseMacroRisk(macroes, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "11", IRN_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
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
      list = ParserUtil.parseCustRisk(companies, CATALOG12, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "11", IRN_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
      if (!list.isEmpty()) {
        dataList.addAll(list);
        num += list.size();
      }
    }

//    list = ParserUtil.parseZFMRisk(IRA_publicsentiment, IRA_SENDATE, IRA_SENDTIME, IRN_SID, "11", IRN_URLDATE, num,APC_SIMRANK,APC_SIMID,APC_SIMV,APV_SIGNT);
//    if (!list.isEmpty()) {
//      dataList.addAll(list);
//      int var10000 = num + list.size();
//    }

    return dataList;
  }

  /**
   * 新加方法，用来把trs文件里面的字段变成和数据库对应的，并存到map集合里面
   * @param map
   * @return
   */
  private Map<String, String> processTRSData(Map<String, Object> map,Map<String, String> txtmap) throws IOException {
    Map<String, String> datamap = new HashMap();
    String strtemp = (String)map.get("irhkeybbsnum");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_SID",strtemp);

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
    datamap.put("IRN_NEWSKIND",strtemp);

    strtemp = (String)map.get("irhkeybbsnum");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_HKEY",strtemp);

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
    datamap.put("IRN_URLDATE",strtemp);
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
    datamap.put("IRN_URLTIME",strtemp);

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
    datamap.put("IRN_LOADATE",strtemp);
    datamap.put("IRN_LASTDATE",strtemp);

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
      e.printStackTrace();
    }
    //-------------------
    datamap.put("IRN_LOADTIME",strtemp);
    datamap.put("IRN_LASTTIME",strtemp);



    strtemp = (String)map.get("sitename");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_SITENAME",strtemp);

    strtemp = (String)map.get("channel");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_CHANNEL",strtemp);

    strtemp = (String)map.get("irsrcname");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_SRCNAME",strtemp);

    strtemp = (String)map.get("irauthors");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_AUTHORS",strtemp);

    strtemp = (String)map.get("title");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_URLTITLE",strtemp);

    strtemp = (String)map.get("trsAbstract");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_abstract",strtemp);

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
    datamap.put("IRN_CONTENT",strtemp);

    strtemp = (String)map.get("url");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRN_URLNAME",strtemp);


    strtemp = (String)map.get("groupname");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    if(strtemp.equals("国内新闻")){
      strtemp = "01";
    }else if(strtemp.equals("国内论坛")){
      strtemp = "02";
    }else if(strtemp.equals("国内博客")){
      strtemp = "03";
    }else if(strtemp.equals("APP")){
      strtemp = "04";
    }else {
      strtemp = "01";
    }
    datamap.put("IRN_GROUPNAME",strtemp);
    datamap.put("IRN_BBSNUM","");
    datamap.put("IRN_NRESERVED2","");
    datamap.put("IRN_NRESERVED3","");
    datamap.put("IRN_BACKUP1","");
    datamap.put("IRN_BACKUP2","");
    datamap.put("IRN_BACKUP3","");
    datamap.put("IRN_BACKUP4","");
    datamap.put("IRN_BACKUP5","");
    datamap.put("IRN_BACKUP6","");
    datamap.put("IRN_BACKUP7","");
    datamap.put("IRN_BACKUP8","");
    datamap.put("IRN_BACKUP9","");
    datamap.put("IRN_BACKUP10","");
    datamap.put("IRN_BACKUP11","");
//    datamap.put("IRN_SENDATE","");
//    datamap.put("IRN_SENDTIME","");
    datamap.put("IRN_SENDATE", DateUtils.dateFormat(new Date(), "yyyyMMdd"));
    datamap.put("IRN_SENDTIME", DateUtils.dateFormat(new Date(), "HHmmss"));

    strtemp = (String)map.get("trssimrank");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIMRANK",map.get("trssimrank").toString());

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


    strtemp = (String)map.get("trssignature");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
    datamap.put("IRC_SIGNT",strtemp);


//-------------------------
    strtemp = (String)map.get("trsCust");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
//    strtemp = TRSFileUtil.filterMapping("trsCust" , strtemp);
    strtemp = TRSFileUtil.trsJsonToStr("trsCust",strtemp);
    String custid = TRSFileUtil.strFilterToNum("trsCust",strtemp);
    datamap.put("CATALOG8",custid);
    datamap.put("CATALOG9",custid);

    strtemp = (String)map.get("trsCustRisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
//    strtemp = TRSFileUtil.filterMapping("trsCustRisk" , strtemp);
    strtemp = TRSFileUtil.trsJsonToStr("trsCustRisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsCustRisk",strtemp);
    datamap.put("CATALOG12",strtemp);

    String trsArea = "";
    strtemp = (String)map.get("trsIndustry");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }

//    strtemp = TRSFileUtil.filterMapping("trsIndustry" , strtemp);
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
//    strtemp = TRSFileUtil.filterMapping("trsInrisk" , strtemp);
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
//    strtemp = TRSFileUtil.filterMapping("trsArea" , strtemp);
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
//    strtemp = TRSFileUtil.filterMapping("trsArearisk" , strtemp);
    strtemp = TRSFileUtil.trsJsonToStr("trsArearisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsArearisk",strtemp);
    //添加地区名
    if(!EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp).equals("")){
      strtemp = EnterpriseNameUtil.StringSplitAdd(trsArea,strtemp);
    }
    datamap.put("CATALOG16",strtemp);


    strtemp = (String)map.get("trsRisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
//    strtemp = TRSFileUtil.filterMapping("trsRisk" , strtemp);
    strtemp = TRSFileUtil.trsJsonToStr("trsRisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsRisk",strtemp);
    datamap.put("CATALOG15",strtemp);








    strtemp = (String)map.get("trsMacrorisk");
    if (StringUtils.isBlank(strtemp)) {
      strtemp = "";
    }
//    strtemp = TRSFileUtil.filterMapping("trsMacrorisk" , strtemp);
    strtemp = TRSFileUtil.trsJsonToStr("trsMacrorisk",strtemp);
    strtemp = TRSFileUtil.strFilterToNum("trsMacrorisk",strtemp);
    datamap.put("CATALOG20",strtemp);







    strtemp = TRSFileUtil.positiveOrNegative((String)map.get("trsBankAppraise"),(String)map.get("trsCustInvestment"));
    datamap.put("ZFM",strtemp);



//    //------------------
//    strtemp = (String)map.get("trssimrank");
//    if (StringUtils.isBlank(strtemp)) {
//      strtemp = "";
//    }
//    datamap.put("APC_SIMRANK",strtemp);
//
//
//    strtemp = (String)map.get("trssimid");
//    if (StringUtils.isBlank(strtemp)) {
//      strtemp = "";
//    }
//    datamap.put("APC_SIMID",strtemp);
//
//
//    strtemp = (String)map.get("trssimvalue");
//    if (StringUtils.isBlank(strtemp)) {
//      strtemp = "";
//    }
//    datamap.put("APC_SIMV",strtemp);
//
//
//    strtemp = (String)map.get("trssignature");
//    if (StringUtils.isBlank(strtemp)) {
//      strtemp = "";
//    }
//    datamap.put("APV_SIGNT",strtemp);


    return datamap;
  }



  private Map<String, String> parserData(Map<String, Object> map) {
    Map<String, String> datamap = new HashMap();
    Object IRN_NEWSKIND = map.get("IRN_NEWSKIND");
    String IRN_NEWSKINDstr = "11";
    if (IRN_NEWSKIND != null) {
      IRN_NEWSKINDstr = String.valueOf(IRN_NEWSKIND);
    }

    datamap.put("IRN_NEWSKIND", IRN_NEWSKINDstr);
    Object IRN_HKEY = map.get("IRN_HKEY");
    String IRN_HKEYstr = "";
    if (IRN_HKEY != null) {
      IRN_HKEYstr = String.valueOf(IRN_HKEY);
    }

    datamap.put("IRN_SID", IRN_HKEYstr);
    datamap.put("IRN_HKEY", IRN_HKEYstr);
    Object IRN_URLDATE = map.get("IRN_URLDATE");
    String IRN_URLDATEstr = "";
    if (IRN_URLDATE != null) {
      IRN_URLDATEstr = String.valueOf(IRN_URLDATE);
    }

    datamap.put("IRN_URLDATE", ParserUtil.getDateFromStr(IRN_URLDATEstr));
    Object IRN_URLTIME = map.get("IRN_URLTIME");
    String IRN_URLTIMEstr = "";
    if (IRN_URLTIME != null) {
      IRN_URLTIMEstr = String.valueOf(IRN_URLTIME);
    }

    datamap.put("IRN_URLTIME", ParserUtil.getTimeFromStr(IRN_URLTIMEstr));
    Object IRN_LOADTIME = map.get("IRN_LOADTIME");
    String IRN_LOADTIMEstr = "";
    if (IRN_LOADTIME != null) {
      IRN_LOADTIMEstr = String.valueOf(IRN_LOADTIME);
    }

    datamap.put("IRN_LOADATE", ParserUtil.getDateFromStr(IRN_LOADTIMEstr));
    datamap.put("IRN_LOADTIME", ParserUtil.getTimeFromStr(IRN_LOADTIMEstr));
    Object IRN_LASTTIME = map.get("IRN_LASTTIME");
    String IRN_LASTTIMEstr = "";
    if (IRN_LASTTIME != null) {
      IRN_LASTTIMEstr = String.valueOf(IRN_LASTTIME);
    }

    datamap.put("IRN_LASTDATE", ParserUtil.getDateFromStr(IRN_LASTTIMEstr));
    datamap.put("IRN_LASTTIME", ParserUtil.getTimeFromStr(IRN_LASTTIMEstr));
    Object IRN_SITENAME = map.get("IRN_SITENAME");
    String IRN_SITENAMEstr = "";
    if (IRN_SITENAME != null) {
      IRN_SITENAMEstr = String.valueOf(IRN_SITENAME);
    }

    datamap.put("IRN_SITENAME", IRN_SITENAMEstr);
    Object IRN_CHANNEL = map.get("IRN_CHANNEL");
    String IRN_CHANNELstr = "";
    if (IRN_CHANNEL != null) {
      IRN_CHANNELstr = String.valueOf(IRN_CHANNEL);
    }

    datamap.put("IRN_CHANNEL", IRN_CHANNELstr);
    Object IRN_SRCNAME = map.get("IRN_SRCNAME");
    String IRN_SRCNAMEstr = "";
    if (IRN_SRCNAME != null) {
      IRN_SRCNAMEstr = String.valueOf(IRN_SRCNAME);
    }

    datamap.put("IRN_SRCNAME", ParserUtil.parseStr(IRN_SRCNAMEstr, InitParam.RN_SM));
    Object IRN_AUTHORS = map.get("IRN_AUTHORS");
    String IRN_AUTHORSstr = "";
    if (IRN_AUTHORS != null) {
      IRN_AUTHORSstr = String.valueOf(IRN_AUTHORS);
    }

    datamap.put("IRN_AUTHORS", ParserUtil.parseStr(IRN_AUTHORSstr, InitParam.RN_SM));
    Object IRN_URLTITLE = map.get("IRN_URLTITLE");
    String IRN_URLTITLEstr = "";
    if (IRN_URLTITLE != null) {
      IRN_URLTITLEstr = String.valueOf(IRN_URLTITLE);
    }

    datamap.put("IRN_URLTITLE", ParserUtil.parseStr(IRN_URLTITLEstr, InitParam.RN_SM));
    Object IRN_abstract = map.get("IRN_abstract");
    String IRN_abstractstr = "";
    if (IRN_abstract != null) {
      IRN_abstractstr = String.valueOf(IRN_abstract);
    }

    datamap.put("IRN_abstract", ParserUtil.parseStr(IRN_abstractstr, InitParam.RN_SM));
    Object IRN_CONTENT = map.get("IRN_CONTENT");
    String IRN_CONTENTstr = "";
    if (IRN_CONTENT != null) {
      IRN_CONTENTstr = String.valueOf(IRN_CONTENT);
    }

    datamap.put("IRN_CONTENT", ParserUtil.parseStr(IRN_CONTENTstr, InitParam.RN_SM));
    Object IRN_URLNAME = map.get("IRN_URLNAME");
    String IRN_URLNAMEstr = "";
    if (IRN_URLNAME != null) {
      IRN_URLNAMEstr = String.valueOf(IRN_URLNAME);
    }

    datamap.put("IRN_URLNAME", ParserUtil.parseStr(IRN_URLNAMEstr, InitParam.RN_SM));
    Object IRN_GROUPNAME = map.get("IRN_GROUPNAME");
    String IRN_GROUPNAMEstr = "";
    if (IRN_GROUPNAME != null) {
      IRN_GROUPNAMEstr = String.valueOf(IRN_GROUPNAME);
    }

    if ("国内新闻".equals(IRN_GROUPNAMEstr)) {
      datamap.put("IRN_GROUPNAME", "01");
    } else if ("国内论坛".equals(IRN_GROUPNAMEstr)) {
      datamap.put("IRN_GROUPNAME", "02");
    } else {
      datamap.put("IRN_GROUPNAME", "01");
    }

    Object IRN_BBSNUM = map.get("IRN_BBSNUM");
    String IRN_BBSNUMstr = "";
    if (IRN_BBSNUM != null) {
      IRN_BBSNUMstr = String.valueOf(IRN_BBSNUM);
    }

    datamap.put("IRN_BBSNUM", IRN_BBSNUMstr);
    Object IRN_NRESERVED2 = map.get("IRN_NRESERVED2");
    String IRN_NRESERVED2str = "";
    if (IRN_NRESERVED2 != null) {
      IRN_NRESERVED2str = String.valueOf(IRN_NRESERVED2);
    }

    datamap.put("IRN_NRESERVED2", IRN_NRESERVED2str);
    Object IRN_NRESERVED3 = map.get("IRN_NRESERVED3");
    String IRN_NRESERVED3str = "";
    if (IRN_NRESERVED3 != null) {
      IRN_NRESERVED3str = String.valueOf(IRN_NRESERVED3);
    }

    datamap.put("IRN_NRESERVED3", IRN_NRESERVED3str);
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

    Object IRN_KEYWORD = map.get("IRN_KEYWORD");
    String IRN_KEYWORDstr = "";
    if (ZFM != null) {
      IRN_KEYWORDstr = String.valueOf(IRN_KEYWORD);
      IRN_KEYWORDstr = IRN_KEYWORDstr.replace(";", "|");
    }

    datamap.put("IRN_KEYWORD", IRN_KEYWORDstr);
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
    datamap.put("IRN_BACKUP1", "");
    datamap.put("IRN_BACKUP2", "");
    datamap.put("IRN_BACKUP3", "");
    datamap.put("IRN_BACKUP4", "");
    datamap.put("IRN_BACKUP5", "");
    datamap.put("IRN_BACKUP6", "");
    datamap.put("IRN_BACKUP7", "");
    datamap.put("IRN_BACKUP8", "");
    datamap.put("IRN_BACKUP9", "");
    datamap.put("IRN_BACKUP10", "");
    datamap.put("IRN_BACKUP11", "");
    datamap.put("IRN_SENDATE", DateUtils.dateFormat(new Date(), "yyyyMMdd"));
    datamap.put("IRN_SENDTIME", DateUtils.dateFormat(new Date(), "HHmmss"));
    datamap.put("ZFM", ZFMstr);
    return datamap;
  }


  /**
   * 新增方法
   * @param map
   * @return
   */
  private Map<String, String> parserTRSUnionMap(Map<String, String> map) {
    Map<String, String> datamap = new HashMap();
    //整合表字段，和新闻表字段对应的
    String []mapkey   = {"IRU_SID","IRU_NEWSKIND","IRU_HKEY","IRU_OLDURLNAME","IRU_URLDATE","IRU_URLTIME","IRU_LOADATE","IRU_LOADTIME","IRU_LASTDATE","IRU_LASTTIME","IRU_AUTHORS","IRU_URLTITLE","IRU_abstract","IRU_SITENAME","channel","IRU_SRCNAME","IRU_GROUPNAME","IRU_BBSNUM","IRU_NRESERVED2","IRU_NRESERVED3","IRU_KEYWORD","IRU_CONTENT","IRU_BACKUP1","IRU_BACKUP2","IRU_BACKUP3","IRU_BACKUP4","IRU_BACKUP5","IRU_BACKUP6","IRU_BACKUP7","IRU_BACKUP8","IRU_BACKUP9","IRU_BACKUP10","IRU_BACKUP11","IRU_SENDATE","IRU_SENDTIME","IRU_SIMRANK","IRU_SIMID","IRU_SIMV","IRU_SIGNT"};
    //新闻表字段  和整合表字段对应的
    String []mapvalue = {"IRN_SID","IRN_NEWSKIND","IRN_HKEY","IRN_URLNAME","IRN_URLDATE","IRN_URLTIME","IRN_LOADATE","IRN_LOADTIME","IRN_LASTDATE","IRN_LASTTIME","IRN_AUTHORS","IRN_URLTITLE","IRN_abstract","IRN_SITENAME","IRN_CHANNEL","IRN_SRCNAME","IRN_GROUPNAME","IRN_BBSNUM","IRN_NRESERVED2","IRN_NRESERVED3","IRN_KEYWORD","IRN_CONTENT","IRN_BACKUP1","IRN_BACKUP2","IRN_BACKUP3","IRN_BACKUP4","IRN_BACKUP5","IRN_BACKUP6","IRN_BACKUP7","IRN_BACKUP8","IRN_BACKUP9","IRN_BACKUP10","IRN_BACKUP11","IRN_SENDATE","IRN_SENDTIME","IRC_SIMRANK","IRC_SIMID","IRC_SIMV","IRC_SIGNT"};

    for (int j = 0; j < mapkey.length; j++) {
      String trsstr = (String)map.get(mapvalue[j]);
      if (trsstr == null) {
        trsstr = "";
      }
      datamap.put(mapkey[j], trsstr);
    }
    //整合表里面没有值的   全部设置为空
    String []mapkon = {"IRU_URLNAME","IRU_AUTHORID","IRU_RETWEETED_SCREEN_ID","IRU_RETWEETED_SCREEN_NAME","IRU_RETWEETED_URL","IRU_VIA","IRU_RTTCOUNT","IRU_COMMTCOUNT","IRU_RDCOUNT","IRU_PRCOUNT","IRU_RANK","IRU_BACKUP12"};
    for (int j = 0; j < mapkon.length; j++) {
      datamap.put(mapkon[j], "");
    }

    return datamap;
  }





  private Map<String, String> parserUnionMap(Map<String, String> map) {
    Map<String, String> _map = new HashMap();
    String IRN_SID = (String)map.get("IRN_SID");
    if (IRN_SID == null) {
      IRN_SID = "";
    }

    _map.put("IRC_SID", IRN_SID);
    String IRN_NEWSKIND = (String)map.get("IRN_NEWSKIND");
    if (IRN_NEWSKIND == null) {
      IRN_NEWSKIND = "";
    }

    _map.put("IRC_NEWSKIND", IRN_NEWSKIND);
    String IRN_HKEY = (String)map.get("IRN_HKEY");
    if (IRN_HKEY == null) {
      IRN_HKEY = "";
    }

    _map.put("IRC_HKEY", IRN_HKEY);
    _map.put("IRB_URLNAME", "");
    String IRN_URLDATE = (String)map.get("IRN_URLDATE");
    if (IRN_URLDATE == null) {
      IRN_URLDATE = "";
    }

    _map.put("IRC_URLDATE", IRN_URLDATE);
    String IRN_URLTIME = (String)map.get("IRN_URLTIME");
    if (IRN_URLTIME == null) {
      IRN_URLTIME = "";
    }

    _map.put("IRC_URLTIME", IRN_URLTIME);
    String IRN_LOADATE = (String)map.get("IRN_LOADATE");
    if (IRN_LOADATE == null) {
      IRN_LOADATE = "";
    }

    _map.put("IRC_LOADATE", IRN_LOADATE);
    String IRN_LOADTIME = (String)map.get("IRN_LOADTIME");
    if (IRN_LOADTIME == null) {
      IRN_LOADTIME = "";
    }

    _map.put("IRC_LOADTIME", IRN_LOADTIME);
    String IRN_LASTDATE = (String)map.get("IRN_LASTDATE");
    if (IRN_LASTDATE == null) {
      IRN_LASTDATE = "";
    }
    _map.put("IRC_LASTDATE", IRN_LASTDATE);
    String IRN_LASTTIME = (String)map.get("IRN_LASTTIME");
    if (IRN_LASTTIME == null) {
      IRN_LASTTIME = "";
    }

    _map.put("IRC_LASTTIME", IRN_LASTTIME);
    String IRN_SITENAME = (String)map.get("IRN_SITENAME");
    if (IRN_SITENAME == null) {
      IRN_SITENAME = "";
    }

    _map.put("IRN_SITENAME", IRN_SITENAME);
    String IRN_CHANNEL = (String)map.get("IRN_CHANNEL");
    if (IRN_CHANNEL == null) {
      IRN_CHANNEL = "";
    }

    _map.put("IRN_CHANNEL", IRN_CHANNEL);
    String IRN_SRCNAME = (String)map.get("IRN_SRCNAME");
    if (IRN_SRCNAME == null) {
      IRN_SRCNAME = "";
    }

    _map.put("IRN_SRCNAME", IRN_SRCNAME);
    String IRN_AUTHORS = (String)map.get("IRN_AUTHORS");
    if (IRN_AUTHORS == null) {
      IRN_AUTHORS = "";
    }

    _map.put("IRN_AUTHORS", IRN_AUTHORS);
    String IRN_URLTITLE = (String)map.get("IRN_URLTITLE");
    if (IRN_URLTITLE == null) {
      IRN_URLTITLE = "";
    }

    _map.put("IRN_URLTITLE", IRN_URLTITLE);
    String IRN_abstract = (String)map.get("IRN_abstract ");
    if (IRN_abstract == null) {
      IRN_abstract = "";
    }

    _map.put("IRN_abstract", IRN_abstract);
    String IRN_KEYWORD = (String)map.get("IRN_KEYWORD");
    if (IRN_KEYWORD == null) {
      IRN_KEYWORD = "";
    }

    _map.put("IRB_KEYWORD", IRN_KEYWORD);
    String IRN_CONTENT = (String)map.get("IRN_CONTENT");
    if (IRN_CONTENT == null) {
      IRN_CONTENT = "";
    }

    _map.put("IRN_CONTENT", IRN_CONTENT);
    String IRN_URLNAME = (String)map.get("IRN_URLNAME");
    if (IRN_URLNAME == null) {
      IRN_URLNAME = "";
    }

    _map.put("IRN_URLNAME", IRN_URLNAME);
    String IRN_GROUPNAME = (String)map.get("IRN_GROUPNAME");
    if (IRN_GROUPNAME == null) {
      IRN_GROUPNAME = "";
    }

    _map.put("IRN_GROUPNAME", IRN_GROUPNAME);
    String IRN_BBSNUM = (String)map.get("IRN_BBSNUM");
    if (IRN_BBSNUM == null) {
      IRN_BBSNUM = "";
    }

    _map.put("IRN_BBSNUM", IRN_BBSNUM);
    String IRN_NRESERVED2 = (String)map.get("IRN_NRESERVED2");
    if (IRN_NRESERVED2 == null) {
      IRN_NRESERVED2 = "";
    }

    _map.put("IRN_NRESERVED2", IRN_NRESERVED2);
    String IRN_NRESERVED3 = (String)map.get("IRN_NRESERVED3");
    if (IRN_NRESERVED3 == null) {
      IRN_NRESERVED3 = "";
    }

    _map.put("IRN_NRESERVED3", IRN_NRESERVED3);
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

  private void readfilelist() {
    String dateStr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
    File f = new File(InitParam.NEWSTO_PATH + "/" + dateStr);
    boolean one = false;
    boolean two = false;
    boolean three = false;
    if (!f.exists()) {
      f.mkdirs();
    }

    if (f.listFiles() == null || f.listFiles().length == 0) {
      one = true;
    }

    File fs = new File(InitParam.SPILTTO_PATH + "/" + dateStr);
    if (!fs.exists()) {
      fs.mkdirs();
    }

    if (fs.listFiles() == null || fs.listFiles().length == 0) {
      two = true;
    }

    File fsu = new File(InitParam.UNION_PATH + "/" + dateStr);
    if (!fsu.exists()) {
      fsu.mkdirs();
    }

    if (fsu.listFiles() == null || fsu.listFiles().length == 0) {
      three = true;
    }

    this.createblank(one, two, three);
    File newspath = new File(InitParam.NEWS_PATH);
    this.oklist = newspath.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
//        System.out.println("---->>>"+pathname.getName());
        return pathname.getName().endsWith("ok");
      }
    });
  }
}
