//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.TimerTask;
import org.apache.commons.io.FileUtils;

public class ListenJob extends TimerTask {
  public ListenJob() {
  }

  public void run() {
    try {
      String pid = FileUtils.readFileToString(new File("abc.pid"), "GBK");
      boolean exist = this.pidExist(pid.trim());
      if (!exist) {
        System.out.println("start.bat start  " + DateUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
        this.runBat("start.bat");
      }
    } catch (IOException var3) {
      var3.printStackTrace();
    }

  }

  public boolean pidExist(String pid) {
    try {
      InputStream in = Runtime.getRuntime().exec("jps").getInputStream();
      BufferedReader bfd = new BufferedReader(new InputStreamReader(in));
      String line = null;

      while((line = bfd.readLine()) != null) {
        if (line.startsWith(pid)) {
          return true;
        }
      }
    } catch (IOException var5) {
      var5.printStackTrace();
    }

    return false;
  }

  public void runBat(String path) {
    Runtime run = Runtime.getRuntime();

    try {
      run.exec("cmd.exe /k start " + path);
    } catch (IOException var4) {
      var4.printStackTrace();
    }

  }
}
