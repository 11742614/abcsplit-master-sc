//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.util.Timer;

public class StartListen {
  public StartListen() {
  }

  public static void main(String[] args) {
    Timer timer = new Timer();
    timer.schedule(new ListenJob(), 0L, 60000L);
  }
}
