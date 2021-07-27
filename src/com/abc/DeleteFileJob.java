package com.abc;

import com.abc.util.ZipUtil;

import java.text.ParseException;
import java.util.TimerTask;


public class DeleteFileJob extends TimerTask {
    @Override
    public void run() {
        try {
            ZipUtil.TimeDelDir();
            ZipUtil.TimeDellog();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
