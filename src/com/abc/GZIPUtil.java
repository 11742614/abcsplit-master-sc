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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.log4j.Logger;

public class GZIPUtil {
  private static final Logger logger = Logger.getLogger(GZIPUtil.class);

  public GZIPUtil() {
  }

  public static String compressFile(String inFileName, String outFileName) {
    FileInputStream in = null;

    try {
      in = new FileInputStream(new File(inFileName));
    } catch (FileNotFoundException var8) {
      logger.error("Could not find the inFile..." + inFileName);
    }

    GZIPOutputStream out = null;

    try {
      out = new GZIPOutputStream(new FileOutputStream(outFileName));
    } catch (IOException var7) {
      logger.error("Could not find the outFile..." + outFileName);
    }

    byte[] buf = new byte[10240];
    boolean var5 = false;

    try {
      while(in.available() > 10240 && in.read(buf) > 0) {
        out.write(buf);
      }

      int len = in.available();
      in.read(buf, 0, len);
      out.write(buf, 0, len);
      in.close();
//      logger.info("Completing the GZIP file..." + outFileName);
      out.flush();
      out.close();
    } catch (IOException var9) {
      logger.error("Completing the GZIP file" + var9.getMessage());
    }

    return outFileName;
  }

  public static String unCompressFile(String sourceFile) {
    String ouputfile = null;

    try {
      FileInputStream fin = new FileInputStream(sourceFile);
      GZIPInputStream gzin = new GZIPInputStream(fin);
      ouputfile = sourceFile.substring(0, sourceFile.lastIndexOf(46));
      FileOutputStream fout = new FileOutputStream(ouputfile);
      byte[] buf = new byte[1024];

      int num;
      while((num = gzin.read(buf, 0, buf.length)) != -1) {
        fout.write(buf, 0, num);
      }

      gzin.close();
      fout.close();
      fin.close();
    } catch (Exception var8) {
      logger.error("unzip :" + var8.toString());
    }

    return ouputfile;
  }
}
