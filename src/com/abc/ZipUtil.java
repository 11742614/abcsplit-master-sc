//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ZipUtil {
  private static final Logger logger = Logger.getLogger(ZipUtil.class);

  public ZipUtil() {
  }

  public static void compressGZipFile(String inFileName, String outFileName) {
    try {
      byte[] buf = new byte[1024];
      FileOutputStream fout = null;
      File file = new File(inFileName);
      fout = new FileOutputStream(outFileName);
      ZipOutputStream gzout = new ZipOutputStream(fout);
      FileInputStream fin = new FileInputStream(inFileName);
      gzout.putNextEntry(new ZipEntry(file.getName()));

      int number;
      while((number = fin.read(buf)) != -1) {
        gzout.write(buf, 0, number);
      }

      gzout.closeEntry();
      fin.close();
      gzout.flush();
      gzout.close();
      fout.flush();
      fout.close();
      logger.info(outFileName);
    } catch (IOException var8) {
      logger.info("***************"+var8+"******************");
      logger.info(outFileName + var8);
    }

  }

  public static File[] unzip(String zip, String dest, String passwd) throws ZipException {
    File zipFile = new File(zip);
    return unzip(zipFile, dest, passwd);
  }

  public static File[] unzip(String zip, String passwd) throws ZipException {
    File zipFile = new File(zip);
    File parentDir = zipFile.getParentFile();
    return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
  }

  public static File[] unzip(File zipFile, String dest, String passwd) throws ZipException {
    ZipFile zFile = new ZipFile(zipFile);
    zFile.setFileNameCharset("GBK");
    File destDir = new File(dest);
    if (destDir.isDirectory() && !destDir.exists()) {
      destDir.mkdir();
    }

    if (zFile.isEncrypted()) {
      zFile.setPassword(passwd.toCharArray());
    }

    zFile.extractAll(dest);
    List<FileHeader> headerList = zFile.getFileHeaders();
    List<File> extractedFileList = new ArrayList();
    Iterator var8 = headerList.iterator();

    while(var8.hasNext()) {
      FileHeader fileHeader = (FileHeader)var8.next();
      if (!fileHeader.isDirectory()) {
        extractedFileList.add(new File(destDir, fileHeader.getFileName()));
      }
    }

    File[] extractedFiles = new File[extractedFileList.size()];
    extractedFileList.toArray(extractedFiles);
    return extractedFiles;
  }

  public static String zip(String src) {
    return zip(src, (String)null);
  }

  public static String zip(String src, String passwd) {
    return zip(src, (String)null, passwd);
  }

  public static String zip(String src, String dest, String passwd) {
    return zip(src, dest, true, passwd);
  }

  public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
    File srcFile = new File(src);
    dest = buildDestinationZipFilePath(srcFile, dest);
    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(8);
    parameters.setCompressionLevel(5);
    if (!StringUtils.isEmpty(passwd)) {
      parameters.setEncryptFiles(true);
      parameters.setEncryptionMethod(0);
      parameters.setPassword(passwd.toCharArray());
    }

    try {
      ZipFile zipFile = new ZipFile(dest);
      if (srcFile.isDirectory()) {
        if (!isCreateDir) {
          File[] subFiles = srcFile.listFiles();
          ArrayList<File> temp = new ArrayList();
          Collections.addAll(temp, subFiles);
          zipFile.addFiles(temp, parameters);
          return dest;
        }

        zipFile.addFolder(srcFile, parameters);
      } else {
        zipFile.addFile(srcFile, parameters);
      }

      return dest;
    } catch (ZipException var9) {
      logger.info("***************"+var9+"******************");
      var9.printStackTrace();
      return null;
    }
  }

  private static String buildDestinationZipFilePath(File srcFile, String destParam) {
    String fileName;
    if (StringUtils.isEmpty(destParam)) {
      if (srcFile.isDirectory()) {
        destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
      } else {
        fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
        destParam = srcFile.getParent() + File.separator + fileName + ".zip";
      }
    } else {
      createDestDirectoryIfNecessary(destParam);
      if (destParam.endsWith(File.separator)) {
        fileName = "";
        if (srcFile.isDirectory()) {
          fileName = srcFile.getName();
        } else {
          fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
        }

        destParam = destParam + fileName + ".zip";
      }
    }

    return destParam;
  }

  private static void createDestDirectoryIfNecessary(String destParam) {
    File destDir = null;
    if (destParam.endsWith(File.separator)) {
      destDir = new File(destParam);
    } else {
      destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
    }

    if (!destDir.exists()) {
      destDir.mkdirs();
    }

  }

  public static void main(String[] args) throws FileNotFoundException, IOException {
    String strykFlpth = "d:\\TipsFilefile.zip";
    String fileNm = "c:" + File.separator + "TipsFile.ini";
    compressGZipFile(fileNm, strykFlpth);
  }
}
