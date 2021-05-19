//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import net.lingala.zip4j.exception.ZipException;

public class TestZip {
    public TestZip() {
    }

    public static void main(String[] args) throws ZipException {
        FileInputStream in = null;

        try {
            in = new FileInputStream(new File("D:\\test.txt.npy"));
        } catch (FileNotFoundException var8) {
            ;
        }

        GZIPOutputStream out = null;

        try {
            out = new GZIPOutputStream(new FileOutputStream("D:\\test.txt.npy.gz"));
        } catch (IOException var7) {
            ;
        }

        byte[] buf = new byte[10240];
        boolean var4 = false;

        try {
            in.close();
            out.flush();
            out.close();
        } catch (IOException var6) {
            ;
        }

    }
}
