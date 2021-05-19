//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.lang.management.ManagementFactory;

public class Start2 {
  public Start2() {
  }

  public static void main(String[] args) {
    InitParam.init();
    (new Thread(new NewsParserJob())).start();
    (new Thread(new WeixinParserJob())).start();
    (new Thread(new WeiboParserJob())).start();
    (new Thread(new CPdicJob())).start();
    String name = ManagementFactory.getRuntimeMXBean().getName();
    String pid = name.split("@")[0];
    System.out.println(pid);
  }
}
