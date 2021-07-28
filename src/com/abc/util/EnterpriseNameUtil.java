package com.abc.util;
import com.abc.EnterpriseNameJob;
import com.abc.InitParam;
import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

/**
 * 读取的是|!分隔的文件
 */
public class EnterpriseNameUtil {
    private static final Logger logger = Logger.getLogger(EnterpriseNameUtil.class);
    public static void main(String[] args) throws IOException {


//        makeEnterpriseNameFile(enterpriseNameFilePath,txtpath,guolvFile);
    }

    public static void initMakeCustFile() throws IOException {
//        String zipfile = InitParam.CUST_MODE_PATH;
        ArrayList<String> list = ZipUtil.getFile(InitParam.CUST_MODE_PATH);

        if(list.size()>0) {

            for (int i = 0; i < list.size(); i++) {
                File tempFile =new File( list.get(i).trim());
                String fileName = tempFile.getName();

                //nas上的路径
                File CUST_MODE_PATH = new File(InitParam.CUST_MODE_PATH+"/"+fileName);
                //本地路径
                File LOCAL_CUST_MODE_PATH = new File(InitParam.LOCAL_CUST_MODE_PATH+"/"+fileName);
                //从nas复制到本地
                ZipUtil.copyFileUsingFileChannels(CUST_MODE_PATH,LOCAL_CUST_MODE_PATH);
            }

            ArrayList<String> locallist = ZipUtil.getFile(InitParam.LOCAL_CUST_MODE_PATH);
            String gzfile = "";
            for (int i = 0; i < locallist.size(); i++) {
                if(locallist.get(i).contains("gz") && !locallist.get(i).contains("ctl")){
                    gzfile = locallist.get(i).toString();
                    break;
                }
            }
            doUncompressFile(gzfile);
        //解压后再读一遍
        list = ZipUtil.getFile(InitParam.LOCAL_CUST_MODE_PATH);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // new Date()为获取当前系统时间
        String datatime = df.format(new Date());
        for (int i = 0; i < list.size(); i++) {
            File nowfile = new File(list.get(i).toString());
            //如果不是压缩文件
            if(list.get(i).indexOf(".gz")!=-1){
                String endpath = InitParam.CUST_MODECP_PATH;
                String filename = list.get(i).toString().substring(list.get(i).lastIndexOf("\\"),list.get(i).length());
                if(endpath.lastIndexOf("\\")!=endpath.length()){
                    endpath = endpath+"\\";
                }
                endpath = endpath + datatime;
                File endfile = new File(endpath);
                if(!endfile.exists()) {
                    endfile.mkdirs();
                }
                endpath = endpath +  filename;
                endpath = endpath.replaceAll("/","\\\\");

                endfile = new File(endpath);
                //目的目录路径
                //如果目的目录路径不存在，则进行创建

                ZipUtil.copyFileUsingFileChannels(nowfile,endfile);
            }else {
                //txt文件生成的位置
                String txtfile = InitParam.LOCAL_CUST_MODE_PATH + "\\" + "custname.txt";
                //过滤文件的位置
                String guolvFile = InitParam.LOCAL_CUST_FILTER_PATH + "\\" + "custfilter_名单过滤词.txt";
                makeEnterpriseNameFile(list.get(i).toString(),txtfile,guolvFile);
                //nas上的路径
                File CUST_MODE_PATH = new File(InitParam.CUST_PATH+"/"+ "custname.txt");
                //从nas复制到本地
                File tFile = new File(txtfile);
                ZipUtil.copyFileUsingFileChannels(tFile,CUST_MODE_PATH);
                logger.info("***************企业名单已复制到nas******************");
                ZipUtil.deleteFile(txtfile);
                logger.info("***************本地企业名单txt已删除******************");
                System.out.println("-------------企业名单已复制到nas,本地企业名单txt已删除----------------------");
            }
                ZipUtil.deleteFile(list.get(i).toString());
        }
            list = ZipUtil.getFile(InitParam.CUST_MODE_PATH);
            for (int i = 0; i < list.size(); i++) {
                ZipUtil.deleteFile(list.get(i).toString());
            }
            System.out.println("-------------企业名单已更新----------------------");
            logger.info("***************企业名单已更新******************");
        }

    }




    /**获取企业名单里面的内容，把id和企业名存到map集合里面
     *
     * @param filepath  企业名单的位置
     * @param filterPath    过滤文件的位置
     * @return
     * @throws IOException
     */
    public static HashMap<String,String> fileContent(String filepath,String filterPath) throws IOException {
        File file = new File(filepath);
        StringBuilder localStrBulider = new StringBuilder();
        HashMap<String,String> hashMap = new HashMap<String,String>();
        HashMap<Integer,String> filterMap=filterName(filterPath);
        if(file.isFile() && file.exists()) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader bufferReader = new BufferedReader(inputStreamReader);
//                BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
//                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis,"GBK"),5*1024*1024);// 用5M的缓冲读取文本文件

                String lineStr = null;
                try {

                    while((lineStr = bufferReader.readLine()) != null) {
                        localStrBulider.append(lineStr);
                        String[] sourceArray = lineStr.split("\\|!");
                        if(!sourceArray[0].equals("") && sourceArray.length>=27) {
                            //判断企业名称在不在过滤名单里面
                            boolean filterName = filterMap.containsValue(sourceArray[27]);
                            String regex = "^[0-9]+$";
                            if(sourceArray[1].matches(regex)) {
                                hashMap.put(sourceArray[1], sourceArray[27]);
//                                System.out.println(sourceArray[0] + "<-------->" + sourceArray[1] + "<-------->" + sourceArray[27]);
                            }

                        }
                    }

                    bufferReader.close();
                    inputStreamReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("file read error!");
                    logger.info("***************file read error!******************");
                    logger.info("***************"+e+"******************");
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                logger.info("***************file catch unsupported encoding!******************");
                logger.info("***************"+e+"******************");
                System.out.println("file catch unsupported encoding!");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                logger.info("***************file not found!******************");
                logger.info("***************"+e+"******************");
                System.out.println("file not found!");
                e.printStackTrace();
            }
        }else {
            System.out.println("file is not a file or file is not existing!");
        }
//        System.out.println("localStrBulider.toString():" + localStrBulider.toString());
        return hashMap;
    }




    /**通过map集合里面的id和企业名称，生产一个txt文件到指定目录
     *
     * @param enterpriseNameFilePath   企业名称文件位置
     * @param filepath                  txt文件生成的位置
     * @param filterPath                过滤文件的位置
     * @return
     * @throws IOException
     */
    public static String makeEnterpriseNameFile(String enterpriseNameFilePath,String filepath,String filterPath) throws IOException {
        HashMap<String,String> hashMap = fileContent(enterpriseNameFilePath,filterPath);
        clearInfoForFile(filepath);
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry<String, String> entry : hashMap.entrySet()) {
            stringBuffer.append(entry.getKey()+"\t"+entry.getValue()+"\r\n");
        }
        try {
            FileWriter fw = null;
            fw = new FileWriter(filepath,true);
            fw.write(stringBuffer.toString());
            fw.close();
        } catch (IOException e) {
            logger.info("***************企业名单生成失败******************");
            logger.info("***************"+e+"******************");
            return "生成文件失败";
        }
        logger.info("***************企业名单生成成功******************");
        return "生成文件成功";

    }

    // 清空已有的文件内容，以便下次重新写入新的内容
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
            logger.info("***************"+e+"******************");
            e.printStackTrace();
        }
    }


    //过滤过滤文件里面的内容
    public static HashMap<Integer,String> filterName(String filepath) throws IOException {
        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream(filepath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
        int number = 0;
        while((str = bufferedReader.readLine()) != null) {
//            System.out.println(str);
            hashMap.put(number,str);
            number++;
        }

        //close
        inputStream.close();
        bufferedReader.close();
        return hashMap;
    }





    ///-------------------------gz-----------------------------

    /**
     * Uncompress the incoming file.
     * @param inFileName Name of the file to be uncompressed
     */
    private static void doUncompressFile(String inFileName) {

        try {

            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {
                System.err.println("File name must have extension of \".gz\"");
                System.exit(1);
            }

            System.out.println("Opening the compressed file.");
            GZIPInputStream in = null;
            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch(FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }

            System.out.println("Open the output file.");
            String outFileName = getFileName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Could not write to file. " + outFileName);
                System.exit(1);
            }

            System.out.println("Transfering bytes from compressed file to the output file.");
            byte[] buf = new byte[1024];
            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            System.out.println("Closing the file and stream");
            in.close();
            out.close();

        } catch (IOException e) {
            logger.info("***************"+e+"******************");
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Used to extract and return the extension of a given file.
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming
     *         file.
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 &&  i < f.length() - 1) {
            ext = f.substring(i+1);
        }
        return ext;
    }

    /**
     * Used to extract the filename without its extension.
     * @param f Incoming file to get the filename
     * @return <code>String</code> representing the filename without its
     *         extension.
     */
    public static String getFileName(String f) {
        String fname = "";
        int i = f.lastIndexOf('.');

        if (i > 0 &&  i < f.length() - 1) {
            fname = f.substring(0,i);
        }
        return fname;
    }

    public static String StringSplitAdd(String ename,String arearike){
        if(!arearike.equals("") && !ename.equals("")) {
            String[] trsareaa = ename.split(";");
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (int i = 0; i == 0 || i < trsareaa.length; i++) {
                if(trsareaa[i].contains("_")) {
                    String[] trsArea = trsareaa[i].split("_");
                    if (trsArea.length >= 0) {
                        hashMap.put(trsArea[1], trsArea[0]);
                    }
                }
            }
            //把企业名称加上去 从原来的  风险_pnum;风险_pnum变成  企业_风险_pnum;企业_风险_pnum
            ArrayList arrayList = new ArrayList();
            String[] trsarearike = arearike.split(";");
            for (int i = 0; i == 0 || i < trsarearike.length; i++) {
                String[] Arearike = trsarearike[i].split("_");
                if (Arearike.length >= 0) {
//                    boolean hasrike = hashMap.containsKey(Arearike[1]);
//                    if (hasrike) {
//                        String pnename = hashMap.get(Arearike[1]);
//                        String arraystr = pnename + "_" + Arearike[0] + "_" + Arearike[1];
//                        arrayList.add(arraystr);
//                    } else {
//                        String arraystr = Arearike[0] + "_" + Arearike[1];
//                        arrayList.add(arraystr);
//                    }
                    if(Arearike[1].contains("#")) {
                        String [] Arearike2 = Arearike[1].split("#");
                        for (int j = 0; j < Arearike2.length; j++) {
                            boolean hasrike = hashMap.containsKey(Arearike2[j]);
                            if (hasrike) {
                                String pnename = hashMap.get(Arearike2[j]);
                                String arraystr = pnename + "_" + Arearike[0] + "_" + Arearike2[j];
                                arrayList.add(arraystr);
                            }
                        }
                    }else {
                        boolean hasrike = hashMap.containsKey(Arearike[1]);
                        if (hasrike) {
                            String pnename = hashMap.get(Arearike[1]);
                            String arraystr = pnename + "_" + Arearike[0] + "_" + Arearike[1];
                            arrayList.add(arraystr);
                        }
                    }
                }
            }

            String finalstr = "";
            for (int i = 0; i < arrayList.size(); i++) {
                finalstr += arrayList.get(i).toString();
                if (i != arrayList.size() - 1) {
                    finalstr = finalstr + ";";
                }
            }
            return finalstr;
        }else{
            return "";
        }
    }

}
