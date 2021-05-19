//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class CopyFileToDirs extends TimerTask {
  private static final Logger logger = Logger.getLogger(CopyFileToDirs.class);

  public CopyFileToDirs() {
  }

  public void cp() {
    if (!InitParam.IS_CPSEND) {
      logger.debug("没有启动数据分发任务");
    } else {
      for(int i = 0; i < InitParam.SEND_PATHCFGs.length; ++i) {
        this.cp(InitParam.SEND_PATHCFGs[i]);
      }

    }
  }

  public void cp(String sdpath) {
    String[] sdpaths = sdpath.split(";");

    File ns;
    for(int i = 1; i < sdpaths.length; ++i) {
      ns = new File(sdpaths[i]);
      if (!ns.exists()) {
        ns.mkdirs();
      }

      if (ns.list().length > 100000) {
        return;
      }
    }

    String source = sdpaths[0];
    ns = new File(source);
    int nums = sdpaths.length - 1;
    File[] scfls = ns.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(InitParam.DATA_SUFFIX);
      }
    });
    int j = 0;

    for(int i = 0; i < scfls.length; ++i) {
      String prefix = DateUtils.dateFormat(new Date(), "yyyyMMddHHmmssSSS");
      File cpy = new File(sdpaths[j % nums + 1] + File.separator + prefix + scfls[i].getName());

      try {
        FileUtils.copyFile(scfls[i], cpy);
      } catch (IOException var15) {
        var15.printStackTrace();
      }

      File cffrom = new File(scfls[i].getAbsolutePath().replace("." + InitParam.DATA_SUFFIX, "." + InitParam.CONFIG_SUFFIX));
      File cpyto = new File(sdpaths[j % nums + 1] + File.separator + prefix + cffrom.getName());

      try {
        FileUtils.copyFile(cffrom, cpyto);
      } catch (IOException var14) {
        var14.printStackTrace();
      }

      ++j;
    }

  }

  public void run() {
    this.cp();
  }
}
