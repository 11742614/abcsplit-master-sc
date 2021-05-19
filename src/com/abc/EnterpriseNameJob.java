package com.abc;

import com.abc.util.EnterpriseNameUtil;

import java.io.IOException;
import java.util.TimerTask;

public class EnterpriseNameJob  extends TimerTask {
    @Override
    public void run() {
        try {
            EnterpriseNameUtil.initMakeCustFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
