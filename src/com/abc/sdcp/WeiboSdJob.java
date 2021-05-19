//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc.sdcp;

import com.abc.DateUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;

public class WeiboSdJob extends TimerTask {
    public WeiboSdJob() {
    }

    public void run() {
        String datestr = DateUtils.dateFormat(new Date(), "yyyyMMdd");
        String CTR_DIR = InitParam.CTR_DIR;
        if (!CTR_DIR.endsWith("\\") && !CTR_DIR.endsWith("/")) {
            CTR_DIR = CTR_DIR + File.separator + datestr;
        } else {
            CTR_DIR = CTR_DIR + datestr;
        }

        File ctrpath = new File(CTR_DIR);
        if (!ctrpath.exists()) {
            ctrpath.mkdir();
        }

        String WB_TO_DIR = InitParam.WB_TO_DIR;
        if (!WB_TO_DIR.endsWith("\\") && !WB_TO_DIR.endsWith("/")) {
            WB_TO_DIR = WB_TO_DIR + File.separator + datestr;
        } else {
            WB_TO_DIR = WB_TO_DIR + datestr;
        }

        File WBspathpath = new File(WB_TO_DIR);
        if (!WBspathpath.exists()) {
            WBspathpath.mkdir();
        }

        ctrpath = new File(CTR_DIR);
        File[] ctrfs = ctrpath.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("wb_");
            }
        });
        int num = 1;
        if (ctrfs != null) {
            num += ctrfs.length;
        }

        String WB_SP_DIR = InitParam.WB_SP_DIR;
        File sppath = new File(WB_SP_DIR);
        File[] spes = sppath.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getAbsolutePath().endsWith(".md5");
            }
        });
        String _num = String.valueOf(num);
        if (_num.length() == 1) {
            _num = "000" + _num;
        }

        if (_num.length() == 2) {
            _num = "00" + _num;
        }

        if (_num.length() == 3) {
            _num = "0" + _num;
        }

        String crtfile = CTR_DIR + File.separator + "wb_" + datestr + "_" + _num + ".fl";
        FileWriterWithEncoding fileWriter = null;
        if (spes != null && spes.length > 0) {
            try {
                fileWriter = new FileWriterWithEncoding(new File(crtfile), "utf-8");
                File[] var17 = spes;
                int var16 = spes.length;

                for(int var15 = 0; var15 < var16; ++var15) {
                    File file = var17[var15];
                    FileUtils.moveFileToDirectory(file, WBspathpath, true);
                    File objFile = new File(file.getAbsolutePath().replace(".md5", ".zip"));
                    FileUtils.moveFileToDirectory(objFile, WBspathpath, true);
                    if (!InitParam.BASE_PATH.endsWith("\\") && !InitParam.BASE_PATH.endsWith("/")) {
                        fileWriter.append(InitParam.BASE_PATH + File.separator + "weibo" + File.separator + datestr + File.separator + file.getName() + "\n");
                        fileWriter.append(InitParam.BASE_PATH + File.separator + "weibo" + File.separator + datestr + File.separator + objFile.getName() + "\n");
                    } else {
                        fileWriter.append(InitParam.BASE_PATH + "weibo" + File.separator + datestr + File.separator + file.getName() + "\n");
                        fileWriter.append(InitParam.BASE_PATH + "weibo" + File.separator + datestr + File.separator + objFile.getName() + "\n");
                    }
                }

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException var19) {
                var19.printStackTrace();
            }
        }

    }
}
