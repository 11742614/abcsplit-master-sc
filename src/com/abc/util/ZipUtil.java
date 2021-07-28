package com.abc.util;

import com.abc.*;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    private static final Logger logger = Logger.getLogger(ZipUtil.class);
    public static void main(String[] args) throws IOException, ParseException {
        //原始文件
        String p1 = "D:\\datatemp\\init_data\\TRS_NEWS_TRS_20200721143206164_100.zip";
        //解压后
        String p2 = "D:\\datatemp\\init_data\\12\\";
//        unZipFile(p1,p2);
//        unZipFileToConfigPath();
//        moveZipFile();
//        delFile("D:\\datatemp\\init_data\\init_data_cp\\2020-07-31\\TRS_NEWS_TRS_20200721143206241_100.zip");
//        File file = new File("D:\\datatemp\\init_data\\init_data_cp\\2020-07-31");
//        delFileDir(file);
//        TimeDelDir();
        moveZipFile();
    }


    /**
     *
     */
    public static void TimeDelDir() throws ParseException {

        ArrayList <String> list = getInitFileCPPath();
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            if (file.exists()) {
                //文件名
                String dirname = list.get(i).substring(list.get(i).lastIndexOf("\\")+1,list.get(i).length());
                int deltime = (int)InitParam.CPFile_Time;
                String deldirname= getPastDate(deltime);
                String nowTime=new String(dirname);
                String delTime=new String(deldirname);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date sd1=df.parse(nowTime);
                Date sd2=df.parse(delTime);
                //如果文件夹日期是7天前的就删除
                if(sd1.before(sd2)){
                    delFileDir(file);
                }
            }
        }

    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    /**
     * 获取过去第几天的日期格式yyyyMMdd
     *
     * @param past
     * @return
     */
    public static String getPastDateTwo(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }



    /**
     * 删除目录下所有文件
     * @param file  文件夹名
     * @return
     */
    public static boolean delFileDir(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFileDir(f);
            }
        }
        return file.delete();
    }


    public static void moveDelete(){
        System.out.println("----------原文件删除中-----------");
        ArrayList <String> list = getInitFilePath();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).toString().indexOf(".zip")!=-1 || list.get(i).toString().indexOf(".ok")!=-1){
                deleteFile(list.get(i).toString());
            }
        }
        System.out.println("----------原文件删除完成，已删除文件个数："+list.size()+"-----------");
    }

    /**
     * 当解压程序执行完后执行，把zip目录移动到备份目录下面，并建立当前日期的文件夹
     */
    public static void moveZipFile(){
//        ArrayList <String> list = getFile(InitParam.InitDate_PATH);
        System.out.println("----------正在把原文件复制到备份目录中-----------");
        String indata_path = InitParam.InitDate_PATH.toString();
        ArrayList <String> list = getInitFilePath();
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // new Date()为获取当前系统时间
        String datatime = df.format(new Date());
        for (int i = 0; i < list.size(); i++) {
            //原路径的类型   比如news   weibo等
            String typename = "";
            String filename = list.get(i).substring(list.get(i).lastIndexOf("\\")+1,list.get(i).length());
            if (filename.contains("NEWS")){
                typename = "news";
            }else if(filename.contains("WB")){
                typename = "weibo";
            }else if(filename.contains("WX")){
                typename = "weixin";
            }else if(filename.contains("XH") || filename.contains("ZQ")){
                typename = "xinhao";
            }
            //目标路径 = 原路径 + 时间日期
            String path = InitParam.InitDateCP_PATH.toString();
            String filepath = path + "/" + typename + "/" + datatime;
            if(list.get(i).toString().indexOf(".zip")!=-1 || list.get(i).toString().indexOf(".ok")!=-1){
                moveFile(list.get(i).toString(),filepath);
            }
        }
        System.out.println("----------原文件备份完成，已备份文件个数："+list.size()+"-----------");
    }

    /**
     * 把文件移动到指定目录
     * @param startPath      文件路径和名称   "E:\path1\demo.txt"
     * @param endPath        目标目录文件路径   "E:\path2"
     */
    public static void moveFile(String startPath,String endPath){
        //源文件路径
        File startFile=new File(startPath);

        //目的目录路径
        File endDirection=new File(endPath);
        //如果目的目录路径不存在，则进行创建
        boolean fileexists = true;
        if(!endDirection.exists()) {
            endDirection.mkdirs();
            fileexists = false;
        }

        //目的文件路径=目的目录路径+源文件名称
        String topath = endDirection+ File.separator+ startFile.getName();
        File endFile=new File(topath);
        //使用apache的FileUtils工具
        try {
           copyFileUsingFileChannels(startFile,endFile);
            deleteFile(startFile.toString());
//            if (startPath.contains("NEWS") && startPath.indexOf(".ok")!=-1){
//                String newpath = InitParam.NEWS_PATH;
//                newpath = newpath.replaceAll("/","\\\\");
//                if(newpath.lastIndexOf("\\")!=newpath.length()){
//                    newpath = newpath+"\\";
//                }
//                newpath = newpath+startFile.getName();
//                File newsend=new File(newpath);
//                copyFileUsingFileChannels(startFile,newsend);
//                deleteFile(startFile.toString());
//            }else if(startPath.contains("WB") && startPath.indexOf(".ok")!=-1){
//                String newpath = InitParam.WEIBO_PATH;
//                newpath = newpath.replaceAll("/","\\\\");
//                if(newpath.lastIndexOf("\\")!=newpath.length()){
//                    newpath = newpath+"\\";
//                }
//                newpath = newpath+startFile.getName();
//                File weiboend=new File(newpath);
//                copyFileUsingFileChannels(startFile,weiboend);
//                deleteFile(startFile.toString());
//            }else if(startPath.contains("WX") && startPath.indexOf(".ok")!=-1){
//                String newpath = InitParam.WEIXIN_PATH;
//                newpath = newpath.replaceAll("/","\\\\");
//                if(newpath.lastIndexOf("\\")!=newpath.length()){
//                    newpath = newpath+"\\";
//                }
//                newpath = newpath+startFile.getName();
//                File weixinend=new File(newpath);
//                copyFileUsingFileChannels(startFile,weixinend);
//                deleteFile(startFile.toString());
//            }else if((startPath.contains("XH") || startPath.contains("ZQ") )&& startPath.indexOf(".ok")!=-1){
//                String newpath = InitParam.SIGNAL_PATH;
//                newpath = newpath.replaceAll("/","\\\\");
//                if(newpath.lastIndexOf("\\")!=newpath.length()){
//                    newpath = newpath+"\\";
//                }
//                newpath = newpath+startFile.getName();
//                File signalend=new File(newpath);
//                copyFileUsingFileChannels(startFile,signalend);
//                deleteFile(startFile.toString());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * @param source           原始目录
     * @param dest              目标
     * @throws IOException
     */
    public static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }



    /**
     * 把配置文件里面的原始文件解压到各目录
     *
     */
    public static void unZipFileToConfigPath() throws IOException {
        ArrayList <String> list = getInitFilePath();
        System.out.println("----------原文件解压中-----------");
        for (int i = 0; i < list.size(); i++) {
            //文件名
            String filename = list.get(i).substring(list.get(i).lastIndexOf("\\")+1,list.get(i).length());
            File file = new File(list.get(i).toString());
            String zipname = filename.substring(filename.lastIndexOf("."),filename.length());
            if (filename.contains("NEWS") && zipname.indexOf(".zip")!=-1){
                unZip(file,InitParam.NEWS_PATH);
            }else if(filename.contains("WB") && zipname.indexOf(".zip")!=-1){
                unZip(file,InitParam.WEIBO_PATH);
            }else if(filename.contains("WX") && zipname.indexOf(".zip")!=-1){
                unZip(file,InitParam.WEIXIN_PATH);
            }else if(filename.contains("XH") || filename.contains("ZQ") && zipname.indexOf(".zip")!=-1){
                unZip(file,InitParam.SIGNAL_PATH);
            }else if(filename.contains("NEWS") && zipname.indexOf(".ok")!=-1){
                moveFile(file.toString(),InitParam.NEWS_PATH);
            }else if(filename.contains("WB") && zipname.indexOf(".ok")!=-1){
                moveFile(file.toString(),InitParam.WEIBO_PATH);
            }else if(filename.contains("WX") && zipname.indexOf(".ok")!=-1){
                moveFile(file.toString(),InitParam.WEIXIN_PATH);
            }else if(filename.contains("XH") && zipname.indexOf(".ok")!=-1){
                moveFile(file.toString(),InitParam.SIGNAL_PATH);
            }
        }
        System.out.println("----------原文件解压完成，已解压文件个数："+list.size()+"-----------");
    }

public static void unZipAllThread(){
    //----------
//
//    Thread t1 = new Thread() { public void run() {
//        try {
//            ZipUtil.newsUnZipFileToConfigPath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } };
//
//    Thread t2 = new Thread() { public void run() {
//        try {
//            ZipUtil.wxUnZipFileToConfigPathThread();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } };
//
//    Thread t3 = new Thread() { public void run() {
//        try {
//            ZipUtil.wbUnZipFileToConfigPathThread();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } };
//
//    Thread t4 = new Thread() { public void run() {
//        try {
//            ZipUtil.xhUnZipFileToConfigPathThread();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } };
//
//    t1.start();
//
//    try {
//        Thread.sleep(0);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//
//    t2.start();
//
//    try {
//        Thread.sleep(0);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//
//    System.out.println(t2.getState());
//
//
//    t3.start();
//
//    try {
//        Thread.sleep(0);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }
//
//
//    t4.start();
//
//    try {
//        Thread.sleep(0);
//    } catch (InterruptedException e) {
//        e.printStackTrace();
//    }


    //==========
}

    /**
     * 把配置文件里面的原始文件解压到各目录
     *
     */
    public static void xhUnZipFileToConfigPathThread(Map<String,String> txtmap) throws IOException {
        SimpleDateFormat formatter2= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date3 = new Date(System.currentTimeMillis());
        System.out.println("信号处理开始时间——"+formatter2.format(date3));
        logger.info("信号处理开始时间——"+formatter2.format(date3));
        try {
            FileTUtils.replacTextContent(InitParam.ThreadXH,"XH","3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> xhlist = getXinHaoInitFilePath();
        System.out.println("----------信号原文件解压中，文件个数："+xhlist.size()+"-----------");

        for (int i = 0; i < xhlist.size(); i++) {
            //文件名
            String filename = xhlist.get(i).substring(xhlist.get(i).lastIndexOf("\\")+1,xhlist.get(i).length());
            if(filename.contains(".zip") || filename.contains(".ok")) {
                File file = new File(xhlist.get(i).toString());
                String zipname = filename.substring(filename.lastIndexOf("."), filename.length());
                if (zipname.indexOf(".zip") != -1) {
                    unZip(file, InitParam.SIGNAL_PATH);
                }
//                else if (zipname.indexOf(".ok") != -1) {
//                    moveFile(file.toString(), InitParam.SIGNAL_PATH);
//                }
                if (zipname.indexOf(".zip") != -1 || zipname.indexOf(".ok") != -1) {
                    //目标路径 = 原路径新闻论坛数据拆共 + 时间日期
                    String path = InitParam.InitDateCP_PATH.toString();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String datatime = df.format(new Date());
                    String filepath = path + "/" + "xinhao" + "/" + datatime;
                    moveFile(file.toString(), filepath);
                }
            }
        }
        System.out.println("----------信号原文件解压并备份完成，文件个数："+xhlist.size()+"-----------");
        logger.info("***************信号原文件解压并备份完成，文件个数："+xhlist.size()+"******************");


        SignalParserJob.XinHaorun(txtmap);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println("信号结束时间——"+formatter.format(date2));
        logger.info("信号结束时间——"+formatter.format(date2));
    }



    /**
     * 把配置文件里面的原始文件解压到各目录
     *
     */
    public static void wxUnZipFileToConfigPathThread(Map<String,String> txtmap) throws IOException {
        try {
            FileTUtils.replacTextContent(InitParam.ThreadWX,"WX","2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> wxlist = getWeiXinInitFilePath();
        System.out.println("----------微信原文件解压中，文件个数："+wxlist.size()+"-----------");
        SimpleDateFormat formatter2= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date3 = new Date(System.currentTimeMillis());
        logger.info("微信处理开始时间————"+formatter2.format(date3));
        for (int i = 0; i < wxlist.size(); i++) {
            //文件名
            String filename = wxlist.get(i).substring(wxlist.get(i).lastIndexOf("\\")+1,wxlist.get(i).length());
            if(filename.contains(".zip") || filename.contains(".ok")) {
                File file = new File(wxlist.get(i).toString());
                String zipname = filename.substring(filename.lastIndexOf("."), filename.length());
                if (filename.contains("WX") && zipname.indexOf(".zip") != -1) {
                    unZip(file, InitParam.WEIXIN_PATH);
                }
//                else if (filename.contains("WX") && zipname.indexOf(".ok") != -1) {
//                    moveFile(file.toString(), InitParam.WEIXIN_PATH);
//                }
                if (zipname.indexOf(".zip") != -1 || zipname.indexOf(".ok") != -1) {
                    //目标路径 = 原路径新闻论坛数据拆共 + 时间日期
                    String path = InitParam.InitDateCP_PATH.toString();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String datatime = df.format(new Date());
                    String filepath = path + "/" + "weixin" + "/" + datatime;
                    moveFile(file.toString(), filepath);
//                    deleteFile(wxlist.get(i).toString());
                }
            }
        }

        System.out.println("----------微信原文件解压并备份完成，文件个数："+wxlist.size()+"-----------");
        logger.info("***************微信原文件解压并备份完成，文件个数："+wxlist.size()+"******************");

//
//        System.out.println("----------正在把微信原文件复制到备份目录中-----------");
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        // new Date()为获取当前系统时间
//        String datatime = df.format(new Date());
//        for (int i = 0; i < wxlist.size(); i++) {
//            //原路径的类型   比如news   weibo等
//            String typename = "";
//            String filename = wxlist.get(i).substring(wxlist.get(i).lastIndexOf("\\")+1,wxlist.get(i).length());
//            if(filename.contains("WX")){
//                typename = "weixin";
//            }
//            //目标路径 = 原路径 + 时间日期
//            String path = InitParam.InitDateCP_PATH.toString();
//            String filepath = path + "/" + typename + "/" + datatime;
//            if(wxlist.get(i).toString().indexOf(".zip")!=-1 || wxlist.get(i).toString().indexOf(".ok")!=-1){
//                moveFile(wxlist.get(i).toString(),filepath);
//            }
//        }
//        System.out.println("----------微信原文件备份完成，已备份文件个数："+wxlist.size()+"-----------");
//
//
//
//        System.out.println("----------微信原文件删除中-----------");
//        for (int i = 0; i < wxlist.size(); i++) {
//            if(wxlist.get(i).toString().indexOf(".zip")!=-1 || wxlist.get(i).toString().indexOf(".ok")!=-1){
//                deleteFile(wxlist.get(i).toString());
//            }
//        }
//        System.out.println("----------微信原文件删除完成，已删除文件个数："+wxlist.size()+"-----------");


        WeixinParserJob.WeiXinrun(txtmap);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println("微信处理结束，结束时间——"+formatter.format(date2));
        logger.info("微信处理结束，结束时间——"+formatter.format(date2));
    }



    /**
     * 把配置文件里面的原始文件解压到各目录
     *
     */
    public static void wbUnZipFileToConfigPathThread(Map<String,String> txtmap) throws IOException {
        try {
            FileTUtils.replacTextContent(InitParam.ThreadWB,"WB","1");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> wblist = getWeiBoInitFilePath();
        System.out.println("----------微博原文件解压中，文件个数："+wblist.size()+"-----------");
        SimpleDateFormat formatter2= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date3 = new Date(System.currentTimeMillis());
        logger.info("微博处理开始时间————"+formatter2.format(date3));

        for (int i = 0; i < wblist.size(); i++) {
            //文件名
            String filename = wblist.get(i).substring(wblist.get(i).lastIndexOf("\\")+1,wblist.get(i).length());
            if(filename.contains(".zip") || filename.contains(".ok")) {
                File file = new File(wblist.get(i).toString());
                String zipname = filename.substring(filename.lastIndexOf("."), filename.length());
                if (filename.contains("WB") && zipname.indexOf(".zip") != -1) {
                    unZip(file, InitParam.WEIBO_PATH);
                }
//                else if (filename.contains("WB") && zipname.indexOf(".ok") != -1) {
//                    moveFile(file.toString(), InitParam.WEIBO_PATH);
//                }
                if (zipname.indexOf(".zip") != -1 || zipname.indexOf(".ok") != -1) {
                    //目标路径 = 原路径新闻论坛数据拆共 + 时间日期
                    String path = InitParam.InitDateCP_PATH.toString();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String datatime = df.format(new Date());
                    String filepath = path + "/" + "weibo" + "/" + datatime;
                    moveFile(file.toString(), filepath);
//                    deleteFile(wblist.get(i).toString());
                }
            }
        }
        System.out.println("----------微博原文件解压并备份完成，已解压文件个数："+wblist.size()+"-----------");
        logger.info("----------微博原文件解压并备份完成，已解压文件个数："+wblist.size()+"-----------");
//
//        System.out.println("----------正在把微博原文件复制到备份目录中-----------");
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        // new Date()为获取当前系统时间
//        String datatime = df.format(new Date());
//        for (int i = 0; i < wblist.size(); i++) {
//            //原路径的类型   比如news   weibo等
//            String typename = "";
//            String filename = wblist.get(i).substring(wblist.get(i).lastIndexOf("\\")+1,wblist.get(i).length());
//            if(filename.contains("WB")){
//                typename = "weibo";
//            }
//            //目标路径 = 原路径 + 时间日期
//            String path = InitParam.InitDateCP_PATH.toString();
//            String filepath = path + "/" + typename + "/" + datatime;
//            if(wblist.get(i).toString().indexOf(".zip")!=-1 || wblist.get(i).toString().indexOf(".ok")!=-1){
//                moveFile(wblist.get(i).toString(),filepath);
//            }
//        }
//        System.out.println("----------微博原文件备份完成，已备份文件个数："+wblist.size()+"-----------");
//
//
//        System.out.println("----------微博原文件删除中-----------");
//        for (int i = 0; i < wblist.size(); i++) {
//            if(wblist.get(i).toString().indexOf(".zip")!=-1 || wblist.get(i).toString().indexOf(".ok")!=-1){
//                deleteFile(wblist.get(i).toString());
//            }
//        }
//        System.out.println("----------微博原文件删除完成，已删除文件个数："+wblist.size()+"-----------");


        WeiboParserJob.weiBoRun(txtmap);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println("微博结束时间——"+formatter.format(date2));
        logger.info("微博结束时间——"+formatter.format(date2));
    }


    /**
     * 把配置文件里面的原始文件解压到各目录
     *
     */
    public static void newsUnZipFileToConfigPath(Map<String,String> txtmap) throws IOException {
        try {
            FileTUtils.replacTextContent(InitParam.ThreadNEWS,"NEWS","0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> newlist = getNewsInitFilePath();
        System.out.println("----------新闻原文件解压并备份中，文件个数："+newlist.size()+"-----------");
        logger.info("----------新闻原文件解压并备份中，文件个数："+newlist.size()+"-----------");
        for (int i = 0; i < newlist.size(); i++) {
            //文件名
            String filename = newlist.get(i).substring(newlist.get(i).lastIndexOf("\\")+1,newlist.get(i).length());
            if(filename.contains(".zip") || filename.contains(".ok")) {
                File file = new File(newlist.get(i).toString());
                String zipname = filename.substring(filename.lastIndexOf("."), filename.length());
                if (filename.contains("NEWS") && zipname.indexOf(".zip") != -1) {
                    unZip(file, InitParam.NEWS_PATH);
                }
//                else if (filename.contains("NEWS") && zipname.indexOf(".ok") != -1) {
//                    //复制过去
//                    moveFile(file.toString(), InitParam.NEWS_PATH);
//                }
                if (zipname.indexOf(".zip") != -1 || zipname.indexOf(".ok") != -1) {
                    //目标路径 = 原路径新闻论坛数据拆共 + 时间日期
                    String path = InitParam.InitDateCP_PATH.toString();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String datatime = df.format(new Date());
                    String filepath = path + "/" + "news" + "/" + datatime;
                    moveFile(file.toString(), filepath);
//                    deleteFile(newlist.get(i).toString());
                }
            }
        }
        System.out.println("----------新闻原文件解压备份完成，已解压并备份文件个数："+newlist.size()+"-----------");
        logger.info("----------新闻原文件解压备份完成，已解压并备份文件个数："+newlist.size()+"-----------");




//        System.out.println("----------正在把新闻原文件复制到备份目录中-----------");
//        //设置日期格式
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        // new Date()为获取当前系统时间
//        String datatime = df.format(new Date());
//        for (int i = 0; i < newlist.size(); i++) {
//            //原路径的类型   比如news   weibo等
//            String typename = "";
//            String filename = newlist.get(i).substring(newlist.get(i).lastIndexOf("\\")+1,newlist.get(i).length());
//            if (filename.contains("NEWS")){
//                typename = "news";
//            }
//            //目标路径 = 原路径新闻论坛数据拆共 + 时间日期
//            String path = InitParam.InitDateCP_PATH.toString();
//            String filepath = path + "/" + typename + "/" + datatime;
//            if(newlist.get(i).toString().indexOf(".zip")!=-1 || newlist.get(i).toString().indexOf(".ok")!=-1){
//                moveFile(newlist.get(i).toString(),filepath);
//            }
//        }
//        System.out.println("----------新闻原文件备份完成，已备份文件个数："+newlist.size()+"-----------");


//
//        System.out.println("----------新闻原文件删除中-----------");
//        for (int i = 0; i < newlist.size(); i++) {
//            if(newlist.get(i).toString().indexOf(".zip")!=-1 || newlist.get(i).toString().indexOf(".ok")!=-1){
//                deleteFile(newlist.get(i).toString());
//            }
//        }
//        System.out.println("----------新闻原文件删除完成，已删除文件个数："+newlist.size()+"-----------");



        NewsParserJob.newRun(txtmap);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println("新闻结束时间——"+formatter.format(date2));
        logger.info("新闻结束时间——"+formatter.format(date2));
    }

    /**

     * 单个zip解压

     * @param srcFile        zip源文件

     * @param destDirPath     解压后的目标文件夹

     * @throws RuntimeException 解压失败会抛出运行时异常

     */

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {

        long start = System.currentTimeMillis();

        // 判断源文件是否存在

        if (!srcFile.exists()) {

            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");

        }

        // 开始解压

        ZipFile zipFile = null;
        try {

            zipFile = new ZipFile(srcFile);

            Enumeration<?> entries = zipFile.entries();

            while (entries.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) entries.nextElement();

//                System.out.println("解压" + entry.getName());

                // 如果是文件夹，就创建个文件夹

                if (entry.isDirectory()) {

                    String dirPath = destDirPath + "/" + entry.getName();

                    File dir = new File(dirPath);

                    dir.mkdirs();

                } else {

                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去

                    File targetFile = new File(destDirPath + "/" + entry.getName());

                    // 保证这个文件的父文件夹必须要存在

                    if(!targetFile.getParentFile().exists()){

                        targetFile.getParentFile().mkdirs();

                    }

                    targetFile.createNewFile();

                    // 将压缩文件内容写入到这个文件中

                    InputStream is = zipFile.getInputStream(entry);

                    FileOutputStream fos = new FileOutputStream(targetFile);

                    int len;

                    byte[] buf = new byte[1024];

                    while ((len = is.read(buf)) != -1) {

                        fos.write(buf, 0, len);

                    }

                    // 关流顺序，先打开的后关闭

                    fos.close();

                    is.close();

                }

            }

            long end = System.currentTimeMillis();

//            System.out.println("解压完成，耗时：" + (end - start) +" ms");

        } catch (Exception e) {
            String filename = srcFile.getName();
            logger.info("***************"+e+"******************");
            logger.info("**********************该压缩文件有问题，文件名————>>>"+filename+"**********************");
//            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {

            if(zipFile != null){

                try {

                    zipFile.close();

                } catch (IOException e) {
                    logger.info("***************"+e+"******************");
                    e.printStackTrace();

                }

            }

        }

    }


//    //指定单个文件解压
//    //zipFile 压缩位置          descDir解压后的位置
//    public static void unZipFile(String fileDir, String descDir)throws IOException {
//        fileDir = fileDir.replaceAll("/","\\\\");
//        descDir = descDir.replaceAll("/","\\\\");
//        if(descDir.lastIndexOf("\\")!=descDir.length()){
//            descDir = descDir+"\\";
//        }
//
//        File zipFile = new File(fileDir);
//        File pathFile = new File(descDir);
//        if(!pathFile.exists())
//        {
//            pathFile.mkdirs();
//        }
//        //解决zip文件中有中文目录或者中文文件
//        ZipFile zip = new ZipFile(zipFile, Charset.forName("UTF-8"));
//        for(Enumeration entries = zip.entries(); entries.hasMoreElements();)
//        {
//            InputStream in = null;
//            OutputStream out = null;
//            try {
//            ZipEntry entry = (ZipEntry)entries.nextElement();
//            String zipEntryName = entry.getName();
//            in = zip.getInputStream(entry);
//            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");;
//            //判断路径是否存在,不存在则创建文件路径
//            String outTemp = "";
//            if(outPath.indexOf("/")!=-1){
//                outTemp = outPath.substring(0, outPath.lastIndexOf('/'));
//            }else if(outPath.indexOf("\\")!=-1){
//                outTemp = outPath.substring(0, outPath.lastIndexOf('\\'));
//            }
//            File file = new File(outTemp);
//            if(!file.exists())
//            {
//                file.mkdirs();
//            }
//            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
//            if(new File(outPath).isDirectory())
//            {
//                continue;
//            }
//            //输出文件路径信息
////            System.out.println(outPath);
//            out = new FileOutputStream(outPath);
//            byte[] buf1 = new byte[1024];
//            int len;
//            while((len=in.read(buf1))>0)
//            {
//                out.write(buf1,0,len);
//            }
//
//            } finally {
//                in.close();
//                out.close();
//            }
//
//        }
//
//    }


    //批量解压，整个目录
    //zipFile 压缩位置          descDir解压后的位置
//    public static void unZipFiles(String fileDir, String descDir)throws IOException {
//        File zipFile = new File(fileDir);
//
//        if(fileDir != null){
//            File[] f = zipFile.listFiles();
//            if(f != null){
//                for(int i=0;i<f.length;i++){
//                    String filePath = f[i].toString();
//                    if(filePath.indexOf(".zip")!=-1){
//                        System.out.println(f[i]);
//                        unZipFile(filePath,descDir);
//                    }
//                }
//            }
//        }
//        System.out.println("******************解压完毕********************");
//    }


    //目录下获取所有的文件名称，递归
//    public static ArrayList getFileDG(File file,ArrayList list){
//        list = new ArrayList();
//        if(file != null){
//            File[] f = file.listFiles();
//            if(f != null){
//                for(int i=0;i<f.length;i++){
//                    getFileDG(f[i],list);
//                }
//            }else{
//                System.out.println(file);
//                String str = file.toString();
//                list.add(file.toString());
//            }
//        }
//        return list;
//    }


    //目录下获取所有的文件名称，递归
    public static ArrayList<String> ergodic(File file,ArrayList<String> resultFileName){
        File[] files = file.listFiles();
        if(files==null)return resultFileName;// 判断目录下是不是空的
        for (File f : files) {
            if(f.isDirectory()){// 判断是否文件夹
                resultFileName.add(f.getPath());
                ergodic(f,resultFileName);// 调用自身,查找子目录
            }else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }

    //把递归的名称进行筛选，只保留zip和ok文件
    public static ArrayList<String> zipErgodic(File file){
        ArrayList<String> resultFileName = new ArrayList<String>();
        ArrayList<String> finalResult = new ArrayList<String>();
        resultFileName = ergodic(file,resultFileName);
        for (int i = 0; i < resultFileName.size(); i++) {
            if(resultFileName.get(i).indexOf(".zip")!=-1 || resultFileName.get(i).indexOf(".ok")!=-1){
                finalResult.add(resultFileName.get(i).toString());
            }
        }
        return finalResult;
    }

    //读取配置文件原文件的位置，把所有位置整合到list集合里面并返回
    public static ArrayList getInitFilePath(){
        ArrayList<String> allresult = new ArrayList<String>();
        ArrayList<String> newsresult = new ArrayList<String>();
        ArrayList<String> weiboresult = new ArrayList<String>();
        ArrayList<String> weixinresult = new ArrayList<String>();
        ArrayList<String> xinhaoresult = new ArrayList<String>();
        String newsPath = InitParam.InitDate_NEWS_PATH;
        String weixinPath = InitParam.InitDate_WEIXIN_PATH;
        String weiboPath = InitParam.InitDate_WEIBO_PATH;
        String xinhaoPath = InitParam.InitDate_XINHAO_PATH;
        newsresult = getFile(newsPath);
        weiboresult = getFile(weixinPath);
        weixinresult = getFile(weiboPath);
        xinhaoresult = getFile(xinhaoPath);
        for (int i = 0; i < newsresult.size(); i++) {
            allresult.add(newsresult.get(i));
        }
        for (int i = 0; i < weiboresult.size(); i++) {
            allresult.add(weiboresult.get(i));
        }
        for (int i = 0; i < weixinresult.size(); i++) {
            allresult.add(weixinresult.get(i));
        }
        for (int i = 0; i < xinhaoresult.size(); i++) {
            allresult.add(xinhaoresult.get(i));
        }
        return allresult;
    }


    //读取配置文件原文件的位置，把所有位置整合到list集合里面并返回
    public static ArrayList getNewsInitFilePath(){
        ArrayList<String> newsresult = new ArrayList<String>();
        String newsPath = InitParam.InitDate_NEWS_PATH;
        newsresult = getFileMAX(newsPath);
        return newsresult;
    }


    //读取配置文件原文件的位置，把所有位置整合到list集合里面并返回
    public static ArrayList getWeiBoInitFilePath(){
        ArrayList<String> weiboresult = new ArrayList<String>();
        String weiboPath = InitParam.InitDate_WEIBO_PATH;

        weiboresult = getFileMAX(weiboPath);

        return weiboresult;
    }


    //读取配置文件原文件的位置，把所有位置整合到list集合里面并返回
    public static ArrayList getWeiXinInitFilePath(){
        ArrayList<String> weixinresult = new ArrayList<String>();
        String weixinPath = InitParam.InitDate_WEIXIN_PATH;
        weixinresult = getFileMAX(weixinPath);

        return weixinresult;
    }



    //读取配置文件原文件的位置，把所有位置整合到list集合里面并返回
    public static ArrayList getXinHaoInitFilePath(){
        ArrayList<String> xinhaoresult = new ArrayList<String>();
        String xinhaoPath = InitParam.InitDate_XINHAO_PATH;
        xinhaoresult = getFileMAX(xinhaoPath);
        return xinhaoresult;
    }



//读取配置文件原文件的备份位置，把所有位置整合到list集合里面并返回
public static ArrayList getInitFileCPPath() {
    ArrayList<String> allresult = new ArrayList<String>();
    ArrayList<String> newsresult = new ArrayList<String>();
    ArrayList<String> weiboresult = new ArrayList<String>();
    ArrayList<String> weixinresult = new ArrayList<String>();
    ArrayList<String> xinhaoresult = new ArrayList<String>();
    String newsPath = InitParam.InitDate_NEWS_PATH_CP;
    String weixinPath = InitParam.InitDate_WEIXIN_PATH_CP;
    String weiboPath = InitParam.InitDate_WEIBO_PATH_CP;
    String xinhaoPath = InitParam.InitDate_XINHAO_PATH_CP;
    newsresult = getFile(newsPath);
    weiboresult = getFile(weixinPath);
    weixinresult = getFile(weiboPath);
    xinhaoresult = getFile(xinhaoPath);
    for (int i = 0; i < newsresult.size(); i++) {
        allresult.add(newsresult.get(i));
    }
    for (int i = 0; i < weiboresult.size(); i++) {
        allresult.add(weiboresult.get(i));
    }
    for (int i = 0; i < weixinresult.size(); i++) {
        allresult.add(weixinresult.get(i));
    }
    for (int i = 0; i < xinhaoresult.size(); i++) {
        allresult.add(xinhaoresult.get(i));
    }
    return allresult;

}

    //只获取当前目录下的所有文件名
    public static ArrayList getFile(String file){
        File zipFile = new File(file);
        ArrayList list = new ArrayList();
        if(file != null){
            File[] f = zipFile.listFiles();
            if(f != null){
                for(int i=0;i<f.length;i++){
                    list.add(f[i].toString());
                }
            }
        }
        return list;
    }

    //只获取当前目录下的所有文件名
    public static ArrayList getFileMAX(String file){
        File zipFile = new File(file);
        ArrayList list = new ArrayList();
        if(file != null){
            File[] f = zipFile.listFiles();
            if(f != null){
                int flength = f.length;
                int max_file = Integer.parseInt(InitParam.MAX_FILE);
                if(flength >= max_file){
                    flength = max_file;
                }
                for(int i=0;i<flength;i++){
                    list.add(f[i].toString());
                }
            }
        }
        return list;
    }

/*
*       删除该目录下所有的文件，只删除文件不删除文件夹
*
*
* */

    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//              delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断指定的文件删除是否成功
     * @param fileName 文件路径
     * @return true or false 成功返回true，失败返回false
     */
    public static boolean deleteFile(String fileName){


        File file = new File(fileName);//根据指定的文件名创建File对象

        if (  file.exists() && file.isFile() ){ //要删除的文件存在且是文件

            if ( file.delete()){
//                System.out.println("文件"+fileName+"删除成功！");
                return true;
            }else{
//                System.out.println("文件"+fileName+"删除失败！");
                return false;
            }
        }else{

//            System.out.println("文件"+fileName+"不存在，删除失败！" );
            return false;
        }
    }

    public static void logBak() throws ParseException, IOException {
        Properties props = new Properties();
        String configname = "abc.ini";
        try {
            props.load(new InputStreamReader(InitParam.class.getResourceAsStream("/" + configname), "UTF-8"));
        } catch (IOException var28) {
            var28.printStackTrace();
            logger.error("找不到配置文件" + configname);
        }
        String logpath = props.getProperty("logpath");
        String logname = props.getProperty("logname");
        if(logpath==null || logpath=="null" || logpath.equals("")){
            logpath = "logs";
            logname = "abc.log";
        }
        String file2 = logpath;
        String file = logpath+"\\"+logname;
//        ZipUtil.copyFileUsingFileStreams(file,cpfile);

//        ZipUtil.clearInfoForFile(cpfile);
        SimpleDateFormat nowdate= new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat yesdate= new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();


//
//        if (yesdate1.compareTo(nowdate2) > 0) {
//            System.out.println("Date1 is after Date2");
//        } else if (yesdate1.compareTo(nowdate2) < 0) {
//            System.out.println("Date1 is before Date2");
//        } else if (yesdate1.compareTo(nowdate2) == 0) {
//            System.out.println("Date1 is equal to Date2");
//        } else {
//            System.out.println("咋到这的？");
//        }

        ArrayList<String> resultFileName = new ArrayList<String>();
        File file1 = new File(file2);
        /**
         * 判断
         */
        int bakfile = 0;
        resultFileName = ZipUtil.ergodic(file1,resultFileName);
        for (int i = 0; i < resultFileName.size(); i++) {
            String filename = resultFileName.get(i);
            String yestdate = yesdate.format(date).toString();
            boolean isday=filename.contains(yestdate);
            if(!isday){
                bakfile++;
            }
        }

        if(bakfile == resultFileName.size()) {
            String yestdate = yesdate.format(date).toString();
            String cpfile = file + ".bak_" + yestdate;
            copyFileUsingFileStreams(file, cpfile);
            clearInfoForFile(file);
        }

        System.out.println();


    }

    public static void copyFileUsingFileStreams(String sourcestr, String deststr) throws IOException {
        File source = new File(sourcestr);
        File dest = new File(deststr);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf))!= -1) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void TimeDellog() throws ParseException {
        Properties props = new Properties();
        String configname = "abc.ini";
        try {
            props.load(new InputStreamReader(InitParam.class.getResourceAsStream("/" + configname), "UTF-8"));
        } catch (IOException var28) {
            var28.printStackTrace();
            logger.error("找不到配置文件" + configname);
        }
        String logpath = props.getProperty("logpath");
        String logname = props.getProperty("logname");
        if(logpath==null || logpath=="null" || logpath.equals("")){
            logpath = "logs";
            logname = "abc.log";
        }
        String file2 = logpath;
        ArrayList<String> list = new ArrayList<String>();
        File file1 = new File(file2);
        /**
         * 判断
         */
        int bakfile = 0;
        list = ZipUtil.ergodic(file1,list);
        for (int i = 0; i < list.size(); i++) {
            File file = new File(list.get(i));
            if (file.exists()) {
                //文件名
                String dirname = list.get(i).substring(list.get(i).lastIndexOf("\\")+1,list.get(i).length());
                int deltime = (int)InitParam.CPFile_Time;
                String deldirname= getPastDateTwo(deltime);
                if(dirname.contains("bak")) {
                    String nowTime = dirname.substring(dirname.indexOf("_")+1,dirname.length());
                    String delTime = new String(deldirname);
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                    Date sd1 = df.parse(nowTime);
                    Date sd2 = df.parse(delTime);
                    //如果文件夹日期是7天前的就删除
                    if (sd1.before(sd2)) {
                        delFileDir(file);
                    }
                }
            }
        }

    }

}
