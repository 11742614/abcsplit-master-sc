package com.abc;
import org.apache.log4j.Logger;

import java.io.*;
import java.io.File;


public class FileTUtils {
    private static final Logger logger = Logger.getLogger(FileTUtils.class);

    public static void initThreadNum(){

        try {
            FileTUtils.replacTextContent(InitParam.ThreadNEWS,"0","NEWS");
        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }

        try {
            FileTUtils.replacTextContent(InitParam.ThreadWX,"2","WX");
        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }

        try {
            FileTUtils.replacTextContent(InitParam.ThreadWB,"1","WB");
        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }

        try {
            FileTUtils.replacTextContent(InitParam.ThreadXH,"3","XH");
        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }
        ZipFileJob.CustMapsISnot.put("isnot","0");
    }


    public static void replacTextContent(String path,String srcStr, String replaceStr) throws IOException{
        //原有的内容
//        String srcStr = "name:";
//        //要替换的内容
//        String replaceStr = "userName:";
        // 读
        File file = new File(path);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter  tempStream = new CharArrayWriter();
        // 替换
        String line = null;
        boolean isinit = false;
        while ( (line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            line = line.replaceAll(srcStr, replaceStr);
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
            if(line.contains("0") || line.contains("1") || line.contains("2") || line.contains("3") ||
            line.contains("NEWS") || line.contains("WB") || line.contains("WX") || line.contains("XH")){
                isinit = true;
            }
        }
        if (!isinit){
            System.out.println("=======================初始化文件内容有误，请检查初始化文件==============================");
            logger.info("=======================初始化文件内容有误，请检查初始化文件==============================");
        }
        // 关闭 输入流
        bufIn.close();
        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
//        System.out.println("====path:"+path);

    }


    public static String getFileContent(String path){
        File file = new File(path);
        StringBuilder result = new StringBuilder();
        String content = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
                s = s + "";
                content += s;
            }

            br.close();
        }catch(Exception e){
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) throws IOException {
//        replacTextContent("D:\\datatemp\\num.txt","NEWS","0");

        String aaa = getFileContent("D:/datatemp/ThreadNum/news.txt");
        System.out.println(aaa);
    }
}
