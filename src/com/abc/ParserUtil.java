//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang.StringUtils;

public class ParserUtil {
  public static ConcurrentMap<String, Area> areaMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, Industry> industryMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> macroMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> custMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> custriskMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> industryriskMaps = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> areariskMaps = new ConcurrentHashMap();


//  public static ConcurrentMap<String, Area> areaMapsnumkey = new ConcurrentHashMap();
//  public static ConcurrentMap<String, Industry> industryMapsnumkey = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> macroMapsnumkey = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> custMapsnumkey = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> custriskMapsnumkey = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> industryriskMapsnumkey = new ConcurrentHashMap();
  public static ConcurrentMap<String, String> areariskMapsnumkey = new ConcurrentHashMap();

  public ParserUtil() {
  }

  public static void main(String[] args) {
  }

  public static List<String> getAreas(String areastr) {
    List<String> list = new ArrayList();
    if (StringUtils.isBlank(areastr)) {
      return list;
    } else {
      String[] areas = areastr.split(";");
      String[] var6 = areas;
      int var5 = areas.length;

      for(int var4 = 0; var4 < var5; ++var4) {
        String str = var6[var4];
        if (StringUtils.isNotBlank(str) && !list.contains(str)) {
          list.add(str);
        }
      }

      if (list.size() == 1) {
        return list;
      } else {
        Set<String> rmset = new HashSet();
        Iterator var9 = list.iterator();

        String string;
        while(var9.hasNext()) {
          string = (String)var9.next();
          rmset.addAll(rmlist(string));
        }

        var9 = rmset.iterator();

        while(var9.hasNext()) {
          string = (String)var9.next();
          list.remove(string);
        }

        return list;
      }
    }
  }

  private static void putMap(Map<String, String> datamap) {
    datamap.put("APC_BACKUP1", "");
    datamap.put("APC_BACKUP2", "");
    datamap.put("APC_BACKUP3", "");
    datamap.put("APC_BACKUP5", "");
    datamap.put("APC_BACKUP6", "");
    datamap.put("APC_BACKUP7", "");
    datamap.put("APC_BACKUP8", "");
    datamap.put("APC_BACKUP9", "");
    datamap.put("APC_BACKUP10", "");
    datamap.put("APC_BACKUP11", "");
    datamap.put("APC_BACKUP12", "");
  }

  public static List<Map<String, String>> parseAreaRisk(List<String> areas, String CATALOG16, String APC_SENDATE, String APC_SENDTIME, String APC_SID, String APC_NEWSKIND, String IRN_URLDATE, int num,String APC_SIMRANK,String APC_SIMID,String APC_SIMV,String APV_SIGNT) {
    List<Map<String, String>> maps = new ArrayList();
    Map<String, List<KeyValue>> maprisks = new HashMap();
    String str;
    if(!CATALOG16.equals("")){
      System.out.println();
    }
    if (!"NULL".equals(CATALOG16) && StringUtils.isNotBlank(CATALOG16)) {
      String[] CATALOG1s = CATALOG16.split(";");
      String[] var14 = CATALOG1s;
      int var13 = CATALOG1s.length;

      for(int var12 = 0; var12 < var13; ++var12) {
        str = var14[var12];
        if (StringUtils.isNotBlank(str)) {
          String[] strs = str.split("_");
          if (strs.length == 3) {
            String area = strs[0];
            String arearisk = strs[1];
            String riskpoint = strs[2];
            if (maprisks.containsKey(area)) {
              ((List)maprisks.get(area)).add(new KeyValue(area, arearisk, riskpoint));
            } else {
              List<KeyValue> list = new ArrayList();
              list.add(new KeyValue(area, arearisk, riskpoint));
              maprisks.put(area, list);
            }
          }
        }
      }
    }

    for(int i = 0; i < areas.size(); ++i) {
      str = (String)areas.get(i);
      if (!StringUtils.isBlank(str) && !"NULL".equals(str)) {
        if (maprisks.containsKey(str)) {
          List<KeyValue> lists = (List)maprisks.get(str);

          for(Iterator var24 = lists.iterator(); var24.hasNext(); ++num) {
            KeyValue risk = (KeyValue)var24.next();
            Map<String, String> map = new HashMap();
            map.put("APC_SID", APC_SID);
            map.put("APC_NEWSKIND", APC_NEWSKIND);
            map.put("APC_SN", String.valueOf(num));
            map.put("APC_OBJKIND", "03");
            map.put("APC_OBJCODETYPE", str);
            map.put("APC_OBJCODE", str);
            putMap(map);
            map.put("APC_BACKUP4", "");
            if (areaMaps.get(str) != null) {
              map.put("APC_OBJCNAME", ((Area)areaMaps.get(str)).name);
            } else {
              map.put("APC_OBJCNAME", "");
            }

            map.put("APC_OBJENAME", "");
            map.put("APC_SARSLTYPE", "20");
            if (areariskMaps.containsKey(risk.getKey2())) {
              map.put("APC_SARSLTCODE", (String)areariskMaps.get(risk.getKey2()));
              map.put("APC_SARSLTNAME", risk.getKey2());
              map.put("APC_BACKUP11", risk.getKey3());
            } else if(areariskMapsnumkey.containsKey(risk.getKey2())){
              map.put("APC_SARSLTCODE", risk.getKey2());
              map.put("APC_SARSLTNAME", (String)areariskMapsnumkey.get(risk.getKey2()));
              map.put("APC_BACKUP11", risk.getKey3());
            }else {
              map.put("APC_SARSLTCODE", "");
              map.put("APC_SARSLTNAME", risk.getKey2());
            }
            map.put("APC_SAKWCODE", "");
            map.put("APC_SAKWNAME", "");
            map.put("APC_SENDATE", APC_SENDATE);
            map.put("APC_SENDTIME", APC_SENDTIME);
            map.put("APC_SIMRANK",APC_SIMRANK);
            map.put("APC_SIMID",APC_SIMID);
            map.put("APC_SIMV",APC_SIMV);
            map.put("APV_SIGNT",APV_SIGNT);
            maps.add(map);
          }
        } else {
          Map<String, String> map = new HashMap();
          map.put("APC_SID", APC_SID);
          map.put("APC_NEWSKIND", APC_NEWSKIND);
          map.put("APC_SN", String.valueOf(num));
          map.put("APC_OBJKIND", "03");
          map.put("APC_OBJCODETYPE", str);
          map.put("APC_OBJCODE", str);
          if (areaMaps.get(str) != null) {
            map.put("APC_OBJCNAME", ((Area)areaMaps.get(str)).name);
          } else {
            map.put("APC_OBJCNAME", "");
          }

          map.put("APC_OBJENAME", "");
          map.put("APC_SARSLTYPE", "30");
          map.put("APC_SARSLTCODE", "");
          map.put("APC_SARSLTNAME", "");
          map.put("APC_SAKWCODE", "");
          map.put("APC_SAKWNAME", "");
          map.put("APC_SENDATE", APC_SENDATE);
          map.put("APC_SENDTIME", APC_SENDTIME);
          map.put("APC_SIMRANK",APC_SIMRANK);
          map.put("APC_SIMID",APC_SIMID);
          map.put("APC_SIMV",APC_SIMV);
          map.put("APV_SIGNT",APV_SIGNT);
          putMap(map);
          map.put("APC_BACKUP4", "");
          maps.add(map);
          ++num;
        }
      }
    }

    return maps;
  }

  public static List<Map<String, String>> parseIndustryRisk(List<String> industries, String CATALOG11, String APC_SENDATE, String APC_SENDTIME, String APC_SID, String APC_NEWSKIND, String IRN_URLDATE, int num,String APC_SIMRANK,String APC_SIMID,String APC_SIMV,String APV_SIGNT) {
    List<Map<String, String>> maps = new ArrayList();
    Map<String, List<KeyValue>> maprisks = new HashMap();
    String str;
    if (!"NULL".equals(CATALOG11) && StringUtils.isNotBlank(CATALOG11)) {
      String[] CATALOG11s = CATALOG11.split(";");
      String[] var14 = CATALOG11s;
      int var13 = CATALOG11s.length;

      for(int var12 = 0; var12 < var13; ++var12) {
        str = var14[var12];
        if (StringUtils.isNotBlank(str)) {
          String[] strs = str.split("_");
          if (strs.length == 3) {
            String area = strs[0];
            String arearisk = strs[1];
            String riskpoint = strs[2];
            if (maprisks.containsKey(area)) {
              ((List)maprisks.get(area)).add(new KeyValue(area, arearisk, riskpoint));
            } else {
              List<KeyValue> list = new ArrayList();
              list.add(new KeyValue(area, arearisk, riskpoint));
              maprisks.put(area, list);
            }
          }
        }
      }
    }

    for(int i = 0; i < industries.size(); ++i) {
      str = (String)industries.get(i);
      if (!StringUtils.isBlank(str) && !"NULL".equals(str)) {
        if (maprisks.containsKey(str)) {
          List<KeyValue> lists = (List)maprisks.get(str);

          for(Iterator var24 = lists.iterator(); var24.hasNext(); ++num) {
            KeyValue risk = (KeyValue)var24.next();
            Map<String, String> map = new HashMap();
            map.put("APC_SID", APC_SID);
            map.put("APC_NEWSKIND", APC_NEWSKIND);
            map.put("APC_SN", String.valueOf(num));
            map.put("APC_OBJKIND", "02");
            map.put("APC_OBJCODETYPE", str);
            map.put("APC_OBJCODE", str);
            putMap(map);
            map.put("APC_BACKUP4", "");
            if (industryMaps.get(str) != null) {
              map.put("APC_OBJCNAME", ((Industry)industryMaps.get(str)).name);
            } else {
              map.put("APC_OBJCNAME", "");
            }

            map.put("APC_OBJENAME", "");
            map.put("APC_SARSLTYPE", "20");
            if (industryriskMaps.containsKey(risk.getKey2())) {
              map.put("APC_SARSLTCODE", (String)industryriskMaps.get(risk.getKey2()));
              map.put("APC_BACKUP11", risk.getKey3());
            } else {
              map.put("APC_SARSLTCODE", "");
            }

            map.put("APC_SARSLTNAME", risk.getKey2());
            map.put("APC_SAKWCODE", "");
            map.put("APC_SAKWNAME", "");
            map.put("APC_SENDATE", APC_SENDATE);
            map.put("APC_SENDTIME", APC_SENDTIME);
            map.put("APC_SIMRANK",APC_SIMRANK);
            map.put("APC_SIMID",APC_SIMID);
            map.put("APC_SIMV",APC_SIMV);
            map.put("APV_SIGNT",APV_SIGNT);
            maps.add(map);
          }
        } else {
          Map<String, String> map = new HashMap();
          map.put("APC_SID", APC_SID);
          map.put("APC_NEWSKIND", APC_NEWSKIND);
          map.put("APC_SN", String.valueOf(num));
          map.put("APC_OBJKIND", "02");
          map.put("APC_OBJCODETYPE", str);
          map.put("APC_OBJCODE", str);
          if (industryMaps.get(str) != null) {
            map.put("APC_OBJCNAME", ((Industry)industryMaps.get(str)).name);
          } else {
            map.put("APC_OBJCNAME", "");
          }

          map.put("APC_OBJENAME", "");
          map.put("APC_SARSLTYPE", "30");
          map.put("APC_SARSLTCODE", "");
          map.put("APC_SARSLTNAME", "");
          map.put("APC_SAKWCODE", "");
          map.put("APC_SAKWNAME", "");
          map.put("APC_SENDATE", APC_SENDATE);
          map.put("APC_SENDTIME", APC_SENDTIME);
          map.put("APC_SIMRANK",APC_SIMRANK);
          map.put("APC_SIMID",APC_SIMID);
          map.put("APC_SIMV",APC_SIMV);
          map.put("APV_SIGNT",APV_SIGNT);
          putMap(map);
          map.put("APC_BACKUP4", "");
          maps.add(map);
          ++num;
        }
      }
    }

    return maps;
  }

  public static List<Map<String, String>> parseCustRisk(String[] custs, String CATALOG12, String APC_SENDATE, String APC_SENDTIME, String APC_SID, String APC_NEWSKIND, String IRN_URLDATE, int num,String APC_SIMRANK,String APC_SIMID,String APC_SIMV,String APV_SIGNT) {
    List<Map<String, String>> maps = new ArrayList();
    Map<String, List<KeyValue>> maprisks = new HashMap();
    String str;

    if (!"NULL".equals(CATALOG12) && StringUtils.isNotBlank(CATALOG12)) {
      String[] CATALOG12s = CATALOG12.split(";");
      String[] var14 = CATALOG12s;
      int var13 = CATALOG12s.length;

      for(int var12 = 0; var12 < var13; ++var12) {
        str = var14[var12];
        if (StringUtils.isNotBlank(str)) {
          String[] strs = str.split("_");
          if (strs.length == 3) {
            String area = strs[0];
            String arearisk = strs[1];
            String riskpoint = strs[2];
            if (maprisks.containsKey(area)) {
              ((List)maprisks.get(area)).add(new KeyValue(area, arearisk, riskpoint));
            } else {
              List<KeyValue> list = new ArrayList();
              list.add(new KeyValue(area, arearisk, riskpoint));
              maprisks.put(area, list);
            }
          }
        }
      }
    }

    for(int i = 0; i < custs.length; ++i) {
      str = custs[i];
      if (!StringUtils.isBlank(str) && !"NULL".equals(str)) {
        if (maprisks.containsKey(str)) {
          List<KeyValue> lists = (List)maprisks.get(str);

          for(Iterator var24 = lists.iterator(); var24.hasNext(); ++num) {
            KeyValue risk = (KeyValue)var24.next();
            Map<String, String> map = new HashMap();
            map.put("APC_SID", APC_SID);
            map.put("APC_NEWSKIND", APC_NEWSKIND);
            map.put("APC_SN", String.valueOf(num));
            map.put("APC_OBJKIND", "01");
            map.put("APC_OBJCODETYPE", "客户农行编码");
            putMap(map);
            map.put("APC_BACKUP4", "");
            if (custMaps.containsKey(str)) {
              map.put("APC_OBJCODE", (String)custMaps.get(str));
              map.put("APC_OBJCNAME", str);
            }else if(custMapsnumkey.containsKey(str)){
              map.put("APC_OBJCODE", str);
              map.put("APC_OBJCNAME", (String)custMapsnumkey.get(str));
            } else {
              map.put("APC_OBJCODE", "");
              map.put("APC_OBJCNAME", str);
            }

            map.put("APC_OBJENAME", "");
            map.put("APC_SARSLTYPE", "20");
            if (custriskMaps.containsKey(risk.getKey2())) {
              map.put("APC_SARSLTCODE", (String)custriskMaps.get(risk.getKey2()));
              map.put("APC_SARSLTNAME", risk.getKey2());
              map.put("APC_BACKUP11", risk.getKey3());
            } else if(custriskMapsnumkey.containsKey(risk.getKey2())){
              map.put("APC_SARSLTCODE", risk.getKey2());
              map.put("APC_SARSLTNAME",(String)custriskMapsnumkey.get(risk.getKey2()));
              map.put("APC_BACKUP11", risk.getKey3());
            }else {
              map.put("APC_SARSLTCODE", "");
              map.put("APC_SARSLTNAME", risk.getKey2());
            }
            map.put("APC_SAKWCODE", "");
            map.put("APC_SAKWNAME", "");
            map.put("APC_SENDATE", APC_SENDATE);
            map.put("APC_SENDTIME", APC_SENDTIME);
            map.put("APC_SIMRANK",APC_SIMRANK);
            map.put("APC_SIMID",APC_SIMID);
            map.put("APC_SIMV",APC_SIMV);
            map.put("APV_SIGNT",APV_SIGNT);
            maps.add(map);
          }
        } else {
          Map<String, String> map = new HashMap();
          map.put("APC_SID", APC_SID);
          map.put("APC_NEWSKIND", APC_NEWSKIND);
          map.put("APC_SN", String.valueOf(num));
          map.put("APC_OBJKIND", "01");
          map.put("APC_OBJCODETYPE", "客户农行编码");

          if (custMaps.containsKey(str)) {
            map.put("APC_OBJCODE", (String)custMaps.get(str));
            map.put("APC_OBJCNAME", str);
          }else if (custMapsnumkey.containsKey(str)) {
            map.put("APC_OBJCODE",str);
            map.put("APC_OBJCNAME", (String)custMapsnumkey.get(str));
          }  else {
            map.put("APC_OBJCODE", "");
            map.put("APC_OBJCNAME", str);
          }

          map.put("APC_OBJENAME", "");
          map.put("APC_SARSLTYPE", "30");
          map.put("APC_SARSLTCODE", "");
          map.put("APC_SARSLTNAME", "");
          map.put("APC_SAKWCODE", "");
          map.put("APC_SAKWNAME", "");
          map.put("APC_SENDATE", APC_SENDATE);
          map.put("APC_SENDTIME", APC_SENDTIME);
          map.put("APC_SIMRANK",APC_SIMRANK);
          map.put("APC_SIMID",APC_SIMID);
          map.put("APC_SIMV",APC_SIMV);
          map.put("APV_SIGNT",APV_SIGNT);
          putMap(map);
//          map.put("APC_BACKUP4", IRN_URLDATE);
          map.put("APC_BACKUP4", "");
          maps.add(map);
          ++num;
        }
      }
    }

    return maps;
  }

  public static List<Map<String, String>> parseMacroRisk(String[] macroes, String APC_SENDATE, String APC_SENDTIME, String APC_SID, String APC_NEWSKIND, String IRN_URLDATE, int num,String APC_SIMRANK,String APC_SIMID,String APC_SIMV,String APV_SIGNT) {
    List<Map<String, String>> maps = new ArrayList();

    for(int i = 0; i < macroes.length; ++i) {
      String str = macroes[i];
      if (!StringUtils.isBlank(str) && !"NULL".equals(str)) {
        Map<String, String> map = new HashMap();
        map.put("APC_SID", APC_SID);
        map.put("APC_NEWSKIND", APC_NEWSKIND);
        map.put("APC_SN", String.valueOf(num));
        map.put("APC_OBJKIND", "04");
        map.put("APC_OBJCODETYPE", str);
        if (macroMaps.get(str) != null) {
          map.put("APC_OBJCNAME", (String)macroMaps.get(str));
          map.put("APC_OBJCODE", str);
        } else if(macroMapsnumkey.containsKey(str)){
          map.put("APC_OBJCNAME", str);
          map.put("APC_OBJCODE", (String)macroMapsnumkey.get(str));
        }else {
          map.put("APC_OBJCNAME", str);
          map.put("APC_OBJCODE", "");
        }

        map.put("APC_OBJENAME", "");
        map.put("APC_SARSLTYPE", "20");
        if (macroMaps.get(str) != null) {
          map.put("APC_SARSLTCODE", str);
          map.put("APC_SARSLTNAME", (String)macroMaps.get(str));
        }else if (macroMapsnumkey.get(str) != null) {
          map.put("APC_SARSLTCODE", (String)macroMapsnumkey.get(str));
          map.put("APC_SARSLTNAME", str);
        } else {
          map.put("APC_SARSLTNAME", str);
          map.put("APC_SARSLTCODE", "");
        }

        map.put("APC_SAKWCODE", "");
        map.put("APC_SAKWNAME", "");
        map.put("APC_SENDATE", APC_SENDATE);
        map.put("APC_SENDTIME", APC_SENDTIME);
        map.put("APC_SIMRANK",APC_SIMRANK);
        map.put("APC_SIMID",APC_SIMID);
        map.put("APC_SIMV",APC_SIMV);
        map.put("APV_SIGNT",APV_SIGNT);
        putMap(map);
        map.put("APC_BACKUP4", "");
        maps.add(map);
        ++num;
      }
    }

    return maps;
  }

  public static List<Map<String, String>> parseZFMRisk(String ZFM, String APC_SENDATE, String APC_SENDTIME, String APC_SID, String APC_NEWSKIND, String IRN_URLDATE, int num,String APC_SIMRANK,String APC_SIMID,String APC_SIMV,String APV_SIGNT) {
    List<Map<String, String>> maps = new ArrayList();
    Map<String, String> map = new HashMap();
    map.put("APC_SID", APC_SID);
    map.put("APC_NEWSKIND", APC_NEWSKIND);
    map.put("APC_SN", String.valueOf(num));
    map.put("APC_OBJKIND", "");
    map.put("APC_OBJCODETYPE", "");
    map.put("APC_OBJCODE", "");
    map.put("APC_OBJCNAME", "");
    map.put("APC_OBJENAME", "");
    map.put("APC_SARSLTYPE", "10");
    map.put("APC_SARSLTCODE", ZFM);
    if ("01".equals(ZFM)) {
      map.put("APC_SARSLTNAME", "正面");
    } else if ("02".equals(ZFM)) {
      map.put("APC_SARSLTNAME", "负面");
    } else if ("03".equals(ZFM)) {
      map.put("APC_SARSLTNAME", "中性");
    } else {
      map.put("APC_SARSLTNAME", "未判定");
    }

    map.put("APC_SAKWCODE", "");
    map.put("APC_SAKWNAME", "");
    map.put("APC_SENDATE", APC_SENDATE);
    map.put("APC_SENDTIME", APC_SENDTIME);
    map.put("APC_SIMRANK",APC_SIMRANK);
    map.put("APC_SIMID",APC_SIMID);
    map.put("APC_SIMV",APC_SIMV);
    map.put("APV_SIGNT",APV_SIGNT);
    putMap(map);
    map.put("APC_BACKUP4", IRN_URLDATE);
    maps.add(map);
    ++num;
    return maps;
  }

  public static List<String> getIndustrys(String industrystr) {
    List<String> list = new ArrayList();
    if (StringUtils.isBlank(industrystr)) {
      return list;
    } else {
      String[] industrys = industrystr.split(";");
      String[] var6 = industrys;
      int var5 = industrys.length;

      for(int var4 = 0; var4 < var5; ++var4) {
        String str = var6[var4];
        if (StringUtils.isNotBlank(str) && !list.contains(str)) {
          list.add(str);
        }
      }

      if (list.size() == 1) {
        return list;
      } else {
        Set<String> rmset = new HashSet();
        Iterator var9 = list.iterator();

        String string;
        while(var9.hasNext()) {
          string = (String)var9.next();
          rmset.addAll(rmindustrylist(string));
        }

        var9 = rmset.iterator();

        while(var9.hasNext()) {
          string = (String)var9.next();
          list.remove(string);
        }

        return list;
      }
    }
  }

  public static List<String> rmlist(String code) {
    List<String> rmlist = new ArrayList();

    String prdcode;
    for(Area area = (Area)areaMaps.get(code); area != null; rmlist.add(prdcode)) {
      prdcode = area.getPrdcode();
      if (prdcode != null) {
        area = (Area)areaMaps.get(prdcode);
      } else {
        area = null;
      }
    }

    return rmlist;
  }

  public static String parseStr(String value, String repvalue) {
    return value;
  }

  public static List<String> rmindustrylist(String code) {
    List<String> rmlist = new ArrayList();

    String prdcode;
    for(Industry area = (Industry)industryMaps.get(code); area != null; rmlist.add(prdcode)) {
      prdcode = area.getPrdcode();
      if (prdcode != null) {
        area = (Industry)industryMaps.get(prdcode);
      } else {
        area = null;
      }
    }

    return rmlist;
  }

  public static String getDateFromStr(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    } else {
      String todate = DateUtils.changeFormatStr(str, "yyyyMMdd");
      return todate;
    }
  }

  public static String getTimeFromStr(String str) {
    if (StringUtils.isBlank(str)) {
      return "";
    } else {
      String todate = DateUtils.changeFormatStr(str, "HHmmss");
      return todate;
    }
  }
}
