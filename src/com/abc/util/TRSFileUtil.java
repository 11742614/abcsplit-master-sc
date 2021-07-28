package com.abc.util;

import com.abc.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.zip.ZipFile;

/**
 * 对trs文件进行解析
 */
public class TRSFileUtil {
    public static ConcurrentMap<String, String> custMaps = new ConcurrentHashMap();
    public static void main(String[] args) throws IOException {
        File trsfile = new File("D:\\EPRO\\projectFile\\TRS\\nonghang\\wendang\\temp\\parsing\\TRS_NEWS_TRS_20200721143206164_100.trs");
        /**
         * 参数1     文件目录
         * 参数2     编码   GBK  UTF-8。。。
         * 参数3     控制执行完后是否备份文件  true备份   false不备份    InitParam.CP
         * 参数4     备份文件的地址
         */
        List<Map<String, Object>> dataList = TRSParser.readFile(trsfile,"GBK",false, InitParam.NEWSCP_PATH);
//        initListData(dataList);

    }


    public static List initListData(List<Map<String, Object>> dataList,Map<String,String> txtmap) throws IOException {
//        String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
//        txtfilepath = txtfilepath.replaceAll("/","\\\\");
//        File file = new File(txtfilepath);
//        Map<String,String> txtmap = getTxtMap(file);
//        Map<String,String> txtmap = getBigTxtMap(txtfilepath);
        if(txtmap.size()>0 && dataList!=null) {
            for (int i = 0; i < dataList.size(); i++) {
                String trsCust ="";
                if(dataList.get(i).containsKey("trsCust")){
                    trsCust = dataList.get(i).get("trsCust").toString();
                }
                String trsCustJson = "";
                if (StringUtils.isNotBlank(trsCust)) {
                    JSONObject jo = JSONObject.parseObject(new String(trsCust));
                    for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                        String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                        if (txtmap.containsKey(resultstr)) {
                            //如果企业名称在名单里面，就把这个企业的json保存到字符串里面 json格式的
                            trsCustJson += (String) jo.getJSONArray("result").get(j).toString() + ",";
                        }
                    }
                }
                //把最后的逗号去掉
                if(!trsCustJson.equals("")){
                    if(trsCustJson.indexOf(",")!=-1) {
                        trsCustJson = trsCustJson.substring(0, trsCustJson.lastIndexOf(","));
                    }
                    trsCustJson = "{" + "\"result\":[" + trsCustJson + "]}";
                    dataList.get(i).put("trsCust", trsCustJson);
                }else {
                    dataList.get(i).put("trsCust", "");
                }
//            System.out.println(trsCustJson);
            }
        }else {
            if (txtmap.size()<=0) {
                System.out.println("企业名单为空,请检查企业名单");
            }
        }

        if(txtmap.size()>0 && dataList!=null) {
            Iterator<Map<String, Object>> it = dataList.iterator();
            while (it.hasNext()) {
                Map<String, Object> x = it.next();
                if (x.get("trsCust").equals("")) {
                    it.remove();
                }
            }
        }
        return dataList;
    }

    public static List initListSignalData(List<Map<String, Object>> dataList,Map<String,String> txtmap) throws IOException {
//        String txtfilepath = InitParam.CUST_PATH+"\\"+"custname.txt";
//        txtfilepath = txtfilepath.replaceAll("/","\\\\");
//        File file = new File(txtfilepath);
//        Map<String,String> txtmap = getTxtMap(file);
//        Map<String,String> txtmap = getBigTxtMap(txtfilepath);
        if(txtmap.size()>0 && dataList!=null) {
            Iterator<Map<String, Object>> it = dataList.iterator();
            while(it.hasNext()){
                Map<String, Object> x = it.next();
                String fullname = x.get("FULLNAME")+"";
                if(!txtmap.containsKey(fullname)){
                    it.remove();
                }
            }
        }else {
            if (txtmap.size()<=0) {
                System.out.println("企业名单为空,请检查企业名单");
            }
        }

        return dataList;
    }
//    /**
//     * 用来吧trsCust字段内容转化成分号分隔的格式
//     * 转化后：企业名1_punum;企业名2_punum
//     * 华为技术有限公司_189;中兴通讯技术有限公司_39#41#137（多个值用#号分隔）
//     * @param jsonStr   trsCust的内容
//     * @return
//     */
//    public static String trsJsonToStr(String strname,String jsonStr){
//        //转化成json格式
//        JSONObject jo;
    //        Map<String, String> map;
//        if (jsonStr.length() != 0 && jsonStr != null && jsonStr.trim().length() != 0) {
//            //转化成json格式
//            jo = JSONObject.parseObject(new String(jsonStr));
//            //用来存放企业名和pnum值的
//            map = new HashMap<String, String>();
//        } else {
//            return "";
//        }
//        if(strname.equals("trsCust")) {
//                //循环存放企业名和pnum值到map
//                for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
//                    String enname = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
//                    for (int i = 0; i < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").size(); i++) {
//                        String point = jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getString("pnum");
//                        //判断map里面有没有存过该企业名称，如果有就修改value值，用#号隔开  企业名称_39#41#137
//                        boolean filterName = map.containsKey(enname);
//                        if (filterName) {
//                            String ennametemp = map.get(enname);
//                            ennametemp = ennametemp + "#" + point;
//                            map.put(enname, ennametemp);
//                        } else {
//                            map.put(enname, point);
//                        }
//                    }
//                }
//        }
//                //记录当前是第几条
////            int nownumber = 0;
//                //存放返回的字符串
//                String enContent = "";
//                //把企业名和pnum进行组合
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    //如果是最后一个就加分号，否则就加分号
////                if (nownumber == map.size() - 1) {
////                    enContent += entry.getKey() + "_" + entry.getValue();
////                } else {
//                    enContent += entry.getKey() + "_" + entry.getValue() + ";";
////                }
////                nownumber++;
//                }
//                return enContent;
//    }



        /**
     * 用来吧字段内容转化成分号分隔的格式
     * 转化后：企业名1;企业名2
     * 华为技术有限公司;中兴通讯技术有限公司

     * @param jsonStr   trsCust的内容
     * @return
     */
    public static String trsJsonToStr(String strname,String jsonStr){
        //转化成json格式
        JSONObject jo;
        Map<String, String> map;
        //企业名称
        String enpName = "";
        if (jsonStr.length() != 0 && jsonStr != null && jsonStr.trim().length() != 0) {
            //转化成json格式
            jo = JSONObject.parseObject(new String(jsonStr));
            //用来存放企业名和pnum值的
            map = new HashMap<String, String>();
        } else {
            return "";
        }
        //只获取resultstr这一层数据    返回格式：企业1;企业2;企业3
        if( strname.equals("trsCust") ||  strname.equals("trsMacrorisk")) {
                for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                    String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                    if(resultstr.indexOf("\\")!=-1){
//                        String[] str = resultstr.split("\\\\");
//                        enpName = enpName + str[str.length-1] +";";
                    }else {
                        enpName = enpName + resultstr +";";
                    }
                }
            //获取resulter和pnum       返回格式：风险1_pnum1;风险2_pnum2
        }else if(strname.equals("trsIndustry")){
            for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                if(resultstr.contains("\\")){
                    resultstr = resultstr.substring(resultstr.lastIndexOf("\\")+1,resultstr.length());
                }
//                System.out.println();
                for (int i = 0; i < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").size(); i++) {
                    String pnum = jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getString("pnum");
                    enpName += resultstr + "_" + pnum+";";
                }
            }
        }else if(strname.equals("trsArea")){
            for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                if(resultstr.contains("\\")){
                    resultstr = resultstr.substring(resultstr.indexOf("\\")+1,resultstr.length());
                }
                for (int i = 0; i < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").size(); i++) {
                   String pnum = jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getString("pnum");
//                   String areaname = jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getJSONArray("pointstr").getJSONObject(0).getString("word");
                   enpName += resultstr + "_" + pnum+";";
                }
            }
            //获取resulter和pnum       返回格式：风险1_pnum1;风险2_pnum2
        }else if(strname.equals("trsArearisk") || strname.equals("trsRisk") || strname.equals("trsInrisk")){
            for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                String pnum = "";
                for (int i = 0; i < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").size(); i++) {
                    pnum += jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getString("pnum")+"#";
                }
                if(pnum.indexOf("#")!=-1){
                    pnum = pnum.substring(0,pnum.lastIndexOf("#"));
                }
//                if(resultstr.indexOf("\\")!=-1){
////                    String[] str = resultstr.split("\\\\");
////                    enpName = enpName +  (str[str.length-1] +"_"+ pnum+";");
//                }else {
                    enpName = enpName + (resultstr +"_"+ pnum+";");
//                }

            }
            //获取resulter和pnum       返回格式：企业名1_风险1_pnum1;企业名2_风险2_pnum2
        }else if(strname.equals("trsCustRisk")){
            for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                String prestr = jo.getJSONArray("result").getJSONObject(j).getString("prestr");
                String sufstr = jo.getJSONArray("result").getJSONObject(j).getString("sufstr");
                String pnum = jo.getJSONArray("result").getJSONObject(j).getString("pnum");
//                if(prestr.indexOf("\\")!=-1){
//                    String[] str = prestr.split("\\\\");
//                    prestr = str[str.length-1];
//                }
//                if(sufstr.indexOf("\\")!=-1){
//                    String[] str = sufstr.split("\\\\");
//                    sufstr = str[str.length-1];
//                }
                enpName =enpName+( prestr+"_"+sufstr+"_"+pnum+";");
            }
        }
        if(enpName.indexOf(";")!=-1){
            enpName = enpName.substring(0,enpName.lastIndexOf(";"));
        }
            return enpName;
    }

    public static String trsJsonToStr(String strname,String jsonStr,String strlist){
        //转化成json格式
        JSONObject jo;
        Map<String, String> map;
        //企业名称
        String enpName = "";
        if (jsonStr.length() != 0 && jsonStr != null && jsonStr.trim().length() != 0) {
            //转化成json格式
            jo = JSONObject.parseObject(new String(jsonStr));
            //用来存放企业名和pnum值的
            map = new HashMap<String, String>();
        } else {
            return "";
        }
        //获取resulter和pnum       返回格式：地区_风险1_pnum1;地区_风险2_pnum2
        if( strname.equals("trsArearisk")) {
            for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                if(resultstr.indexOf("\\")!=-1){
//                        String[] str = resultstr.split("\\\\");
//                        enpName = enpName + str[str.length-1] +";";
                }else {
                    enpName = enpName + resultstr +";";
                }
            }

        }
        return enpName;
    }

    /**
     *  json解析成str后，通过此方法再把中文映射成对应的数字
     * @param strname       字段名称
     * @param strContent    str内容
     * @return
     */
    public static String strFilterToNum(String strname,String strContent) throws IOException {
        if(StringUtils.isNotBlank(strContent)){

            Map<String, String> filtermap = new HashMap<String, String>();
            if(strname.equals("trsCust")){
                File file = new File(InitParam.path + "/" + InitParam.custfilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsMacrorisk")){
                File file = new File(InitParam.path + "/" + InitParam.macrofilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsArea")){
                File file = new File(InitParam.path + "/" + InitParam.areafilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsIndustry")){
                File file = new File(InitParam.path + "/" + InitParam.industryfilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsArearisk")){
                File file = new File(InitParam.path + "/" + InitParam.areariskfilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsRisk")){
                File file = new File(InitParam.path + "/" + InitParam.areariskfilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsInrisk")){
                File file = new File(InitParam.path + "/" + InitParam.industryriskfilename);
                filtermap = getTxtMap(file);
            }else if(strname.equals("trsCustRisk")){
                File file = new File(InitParam.path + "/" + InitParam.custriskfilename);
                filtermap = getTxtMap(file);
            }
            //返回格式为：内容1;内容2;内容3
            if(strname.equals("trsCust")) {
                String[] content = strContent.split(";");
                String retrunStr = "";
                for (int i = 0; i < content.length; i++) {
                    if(custMaps.containsKey(content[i])){
                        content[i] = custMaps.get(content[i]);
                    }
                    retrunStr += content[i]+";";
                }
                    retrunStr = retrunStr.substring(0,retrunStr.lastIndexOf(";"));
                return retrunStr;

            }else if(strname.equals("trsMacrorisk")) {
                String[] content = strContent.split(";");
                String retrunStr = "";
                for (int i = 0; i < content.length; i++) {
                    if(filtermap.containsKey(content[i])){
                        content[i] = filtermap.get(content[i]);
                    }
                    retrunStr += content[i]+";";
                }
                retrunStr = retrunStr.substring(0,retrunStr.lastIndexOf(";"));
                return retrunStr;

            }else if(strname.equals("trsArea")) {
//                String[] content = strContent.split(";");
//                String retrunStr = "";
//                for (int i = 0; i < content.length; i++) {
//                    //判断是否是省或者市结尾的，如果不是就加上省市，然后判断是否存在，如果存在就加上省市，不存在就跳过
//                    if (content[i].indexOf("省") == -1 || content[i].indexOf("市") == -1) {
//                        if (filtermap.containsKey(content[i] + "省")) {
//                            content[i] = content[i] + "省";
//                        } else if (filtermap.containsKey(content[i] + "市")) {
//                            content[i] = content[i] + "市";
//                        }
//                    }
//                    if(filtermap.containsKey(content[i])){
//                        content[i] = filtermap.get(content[i]);
//                    }
//                    retrunStr += content[i]+";";
//            }

                    String[] content = strContent.split(";");
                    String retrunStr = "";
                    for (int i = 0; i < content.length; i++) {
                        String nowcontent = content[i];
                        if(StringUtils.isNotBlank(nowcontent) && nowcontent.contains("_")){
                            nowcontent = nowcontent.substring(0,nowcontent.indexOf("_"));
                            String num = content[i].substring(content[i].indexOf("_"),content[i].length());
                            if(filtermap.containsKey(nowcontent)){
                                nowcontent = filtermap.get(nowcontent)+num;
                            }else if(filtermap.containsKey(nowcontent+"市")){
                                nowcontent = filtermap.get(nowcontent+"市")+num;
                            }else if(filtermap.containsKey(nowcontent+"省")){
                                nowcontent = filtermap.get(nowcontent+"省")+num;
                            }else if(filtermap.containsKey(nowcontent+"区")){
                                nowcontent = filtermap.get(nowcontent+"区")+num;
                            }else if(filtermap.containsKey(nowcontent+"县")){
                                nowcontent = filtermap.get(nowcontent+"县")+num;
                            }else if(filtermap.containsKey(nowcontent+"自治区")){
                                nowcontent = filtermap.get(nowcontent+"自治区")+num;
                            }else if(filtermap.containsKey(nowcontent+"自治州")){
                                nowcontent = filtermap.get(nowcontent+"自治州")+num;
                            }else if(filtermap.containsKey(nowcontent+"自治县")){
                                nowcontent = filtermap.get(nowcontent+"自治县")+num;
                            }else if(filtermap.containsKey(nowcontent+"特别行政区")){
                                nowcontent = filtermap.get(nowcontent+"特别行政区")+num;
                            }
                        }
                        retrunStr += nowcontent+";";
                    }
                retrunStr = retrunStr.substring(0,retrunStr.lastIndexOf(";"));
                return retrunStr;

            }else if(strname.equals("trsArearisk") || strname.equals("trsRisk") || strname.equals("trsInrisk")  ||  strname.equals("trsIndustry")) {
                //获取resulter和pnum       返回格式：风险1_pnum1;风险2_pnum2
                String[] content = strContent.split(";");
                String retrunStr = "";
                for (int i = 0; i < content.length; i++) {
                    String []content2 = content[i].split("_");
                    if(filtermap.containsKey(content2[0])){
                        content2[0] = filtermap.get(content2[0]);
                    }
                    retrunStr += content2[0]+"_"+content2[1]+";";
                }
                retrunStr = retrunStr.substring(0,retrunStr.lastIndexOf(";"));
                return retrunStr;
                //获取resulter和pnum       返回格式：企业名1_风险1_pnum1;企业名2_风险2_pnum2
            }else if(strname.equals("trsCustRisk")){
                String[] content = strContent.split(";");
                String retrunStr = "";
//                File file2 = new File(InitParam.path + "/" + InitParam.custfilename);
//                Map<String, String> filtercust = getTxtMap(file2);
                for (int i = 0; i < content.length; i++) {
                    String []content2 = content[i].split("_");
                    if(custMaps.containsKey(content2[0])){
                        content2[0] = custMaps.get(content2[0]);
                    }
                    if(filtermap.containsKey(content2[1])){
                        content2[1] = filtermap.get(content2[1]);
                    }
                    retrunStr += content2[0]+"_"+content2[1]+"_"+content2[2]+";";
                }
                retrunStr = retrunStr.substring(0,retrunStr.lastIndexOf(";"));
                return retrunStr;
            }
        }
        return "";
    }





    /**
     * 判断正负面的，传入负面值和正面值   返回正面或者负面  字符串
     * @param trsBankAppraise
     * @param trsCustInvestment
     * @return
     */
    public static String positiveOrNegative(String trsBankAppraise,String trsCustInvestment){
        //转化成json格式
        JSONObject trsBankAppraiseJson = new JSONObject();
        //转化成json格式
        JSONObject trsCustInvestmentJson  = new JSONObject();
        if(!StringUtils.isBlank(trsBankAppraise)){
            //转化成json格式
            trsBankAppraiseJson = JSONObject.parseObject(new String(trsBankAppraise));
        }
        if(!StringUtils.isBlank(trsCustInvestment)){
            //转化成json格式
            trsCustInvestmentJson = JSONObject.parseObject(new String(trsCustInvestment));
        }
        //1、文章（trsBankAppraise 有值 &&  trsCustInvestment 没有值） == 负面
        if(!StringUtils.isBlank(trsBankAppraise) && StringUtils.isBlank(trsCustInvestment)){
            return "负面";
        }
        //2、文章（trsBankAppraise 没有值 &&  trsCustInvestment 有值） == 正面
        if(StringUtils.isBlank(trsBankAppraise) && !StringUtils.isBlank(trsCustInvestment)){
            return "正面";
        }
        //3、文章（trsBankAppraise 没有值 &&  trsCustInvestment 没有值） == 中性
        if(StringUtils.isBlank(trsBankAppraise) && StringUtils.isBlank(trsCustInvestment)){
            return "中性";
        }


        if(!StringUtils.isBlank(trsBankAppraise) && !StringUtils.isBlank(trsCustInvestment)){
            //负面个数
            int trsbankNum = trsBankAppraiseJson.getJSONArray("result").size();
            //正面个数
            int trscustNum = trsCustInvestmentJson.getJSONArray("result").size();
            //4、文章（trsBankAppraise 有值 &&  trsCustInvestment 有值 &&
            //负面个数>正面个数 ） == 负面
            if(trsbankNum > trscustNum){
                return "负面";

            }else if(trsbankNum < trscustNum){
                //负面个数<正面个数 ） == 正面
                return "正面";
            }else if(trsbankNum == trscustNum){
                //正面个数=负面个数 ） == 正面
                return "正面";
            }

        }


        return "";
    }




    /**
     * 通过映射txt把内容换成对应的映射值       一次映射一条（一个字段）
     * @param filterName        映射字段名
     * @param filterContent     映射内容
     * @return
     * @throws IOException
     */
    public static String filterMapping(String filterName , String filterContent) throws IOException {

        //转化成json格式
        JSONObject jo = JSONObject.parseObject(new String(filterContent));

        if(!org.apache.commons.lang.StringUtils.isBlank(filterContent)) {
            if (filterName.equals("trsArea")) {
                File file = new File(InitParam.path + "/" + InitParam.areafilename);
                //获取映射txt里面的名称和对应的值组成的map
                Map<String, String> filtermap = getTxtMap(file);
                for (int j = 0; j < jo.getJSONArray("result").size(); j++) {
                    String resultstr = jo.getJSONArray("result").getJSONObject(j).getString("resultstr");
                    if (resultstr.indexOf("\\") != -1) {
                        //用\拆分省市区
                        String[] restr = resultstr.split("\\\\");

                        //组合
                        String allnum = "";
                        for (int i = 0; i < restr.length; i++) {
                            //判断是否是省或者市结尾的，如果不是就加上省市，然后判断是否存在，如果存在就加上省市，不存在就跳过
                            if (restr[i].indexOf("省") == -1 && restr[i].indexOf("市") == -1) {
                                if (filtermap.containsKey(restr[i] + "省")) {
                                    restr[i] = restr[i] + "省";
                                } else if (filtermap.containsKey(restr[i] + "市")) {
                                    restr[i] = restr[i] + "市";
                                }
                            }
                            if (filtermap.containsKey(restr[i])) {
                                if (i == restr.length - 1) {
                                    allnum += filtermap.get(restr[i]);
                                } else {
                                    allnum += filtermap.get(restr[i]) + "\\";
                                }
                            }

                        }
                        jo.getJSONArray("result").getJSONObject(j).put("resultstr", allnum);

                    } else {

                        if (filtermap.containsKey(resultstr)) {
                            jo.getJSONArray("result").getJSONObject(j).put("resultstr", filtermap.get(resultstr));
                        } else {
                            //判断是否是省或者市结尾的，如果不是就加上省市，然后判断是否存在，如果存在就加上省市，不存在就跳过
                            if (resultstr.indexOf("省") == -1 && resultstr.indexOf("市") == -1) {
                                if (filtermap.containsKey(resultstr + "省")) {
                                    resultstr = resultstr + "省";
                                } else if (filtermap.containsKey(resultstr + "市")) {
                                    resultstr = resultstr + "市";
                                }
                            }
                            jo.getJSONArray("result").getJSONObject(j).put("resultstr", filtermap.get(resultstr));
                        }
                    }

                    for (int i = 0; i < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").size(); i++) {
                        for (int k = 0; k < jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getJSONArray("pointstr").size(); k++) {
                            String word = jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getJSONArray("pointstr").getJSONObject(k).getString("word");
                            jo.getJSONArray("result").getJSONObject(j).getJSONArray("point").getJSONObject(i).getJSONArray("pointstr").getJSONObject(k).put("word", filtermap.get(word));
                        }
                    }
                }
            } else if (filterName.equals("trsArearisk") || filterName.equals("trsCust") || filterName.equals("trsMacrorisk")|| filterName.equals("trsRisk")) {
                String filePath = "";
                if(filterName.equals("trsArearisk") || filterName.equals("trsRisk")){
                    filePath = InitParam.path + "/" + InitParam.areariskfilename;
                }else if(filterName.equals("trsCust")){
                    filePath = InitParam.path + "/" + InitParam.custfilename;
                }else if(filterName.equals("trsMacrorisk")){
                    filePath = InitParam.path + "/" + InitParam.macrofilename;
                }
                File file = new File(filePath);
                Map<String, String> filtermap = getTxtMap(file);
                for (int i = 0; i < jo.getJSONArray("result").size(); i++) {
                    String resultstr = jo.getJSONArray("result").getJSONObject(i).getString("resultstr");
                    if(filtermap.containsKey(resultstr)){
                        jo.getJSONArray("result").getJSONObject(i).put("resultstr", filtermap.get(resultstr));
                    }
                }

            } else if (filterName.equals("trsCustRisk")) {
                File file = new File(InitParam.path + "/" + InitParam.custriskfilename);
                Map<String, String> filtermap = getTxtMap(file);
                for (int i = 0; i < jo.getJSONArray("result").size(); i++) {
                    String sufstr = jo.getJSONArray("result").getJSONObject(i).getString("sufstr");
                    if(filtermap.containsKey(sufstr)){
                        jo.getJSONArray("result").getJSONObject(i).put("sufstr", filtermap.get(sufstr));
                    }
                }
            } else if (filterName.equals("trsIndustry") || filterName.equals("trsInrisk")) {
                String filePath = "";
                if(filterName.equals("trsIndustry")){
                    filePath = InitParam.path + "/" + InitParam.industryfilename;
                }else if(filterName.equals("trsInrisk")){
                    filePath = InitParam.path + "/" + InitParam.industryriskfilename;
                }
                File file = new File(filePath);
                Map<String, String> filtermap = getTxtMap(file);
                for (int i = 0; i < jo.getJSONArray("result").size(); i++) {
                    String resultstr = jo.getJSONArray("result").getJSONObject(i).getString("resultstr");
                    if(resultstr.indexOf("\\") != -1){
                        String[] restr = resultstr.split("\\\\");
                        //组合
                        String allnum = "";
                        for (int j = 0; j < restr.length; j++) {
                            if(filtermap.containsKey(restr[j])){
                                allnum += filtermap.get(restr[j])+"\\";
                            }else {
                                allnum += restr[j]+"\\";
                            }
                        }
                        allnum = allnum.substring(0,allnum.lastIndexOf("\\"));
                        jo.getJSONArray("result").getJSONObject(i).put("resultstr", allnum);
                    }else if(filtermap.containsKey(resultstr)){
                        jo.getJSONArray("result").getJSONObject(i).put("resultstr", filtermap.get(resultstr));
                    }
                }
            }
            String jsonstr = jo.toString();
            return jsonstr;
        }
        return "";
    }

    /**
     * 获取txt文件内容   存放到map集合里面   id放在value  值放到key
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String,String> getTxtMap(File file) throws IOException {
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
//        LineIterator it = FileUtils.lineIterator(file, "GBK");
        String line = "";
        Map<String,String> filtermap = new HashMap<String,String>();
        while(it.hasNext()) {
            line = it.nextLine();
            if (!StringUtils.isBlank(line)) {
                String[] lines = line.split("\t");
                if (lines.length == 2) {
                    String code = lines[0].trim();
                    String name = lines[1].trim();
                    filtermap.put(name,code);
                }
            }
        }
        return filtermap;
    }


    /**
     * 获取txt文件内容   存放到map集合里面   id放在value  值放到key
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<String,String> getBigTxtMap(String filepath) throws IOException {

        Map<String,String> filtermap = new HashMap<String,String>();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(filepath)));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "UTF-8"), 10 * 1024 * 1024);// 10M缓存
            while (in.ready()) {
                String line = in.readLine();
                if (!StringUtils.isBlank(line)) {
                    String[] lines = line.split("\t");
                    if (lines.length == 2) {
                        String code = lines[0].trim();
                        String name = lines[1].trim();
                        filtermap.put(name,code);
                        ParserUtil.custMaps.put(name, code);
                        TRSFileUtil.custMaps.put(name, code);
                        ParserUtil.custMapsnumkey.put(code, name);
                        SignalParserJob.custMaps.put(name,code);
                        ZipFileJob.custMaps.put(name,code);
                        ZipFileJob.CustMapsISnot.put("isnot","1");
                    }
                }
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filtermap;
    }
    /**
     * 获取txt文件内容   存放到map集合里面   id放在key  值放到value
     * @return
     * @throws IOException
     */
    public static Map<String,String> getTxtMapKV(File file) throws IOException {
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        String line = "";
        Map<String,String> filtermap = new HashMap<String,String>();
        while(it.hasNext()) {
            line = it.nextLine();
            if (!StringUtils.isBlank(line)) {
                String[] lines = line.split("\t");
                if (lines.length == 2) {
                    String code = lines[0].trim();
                    String name = lines[1].trim();
                    filtermap.put(code,name);
                }
            }
        }
        return filtermap;
    }

}
