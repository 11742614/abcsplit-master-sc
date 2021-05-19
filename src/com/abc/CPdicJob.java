//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import com.trs.ckm.soap.CkmSoapException;
import com.trs.ckm.soap.TrsCkmSoapClient;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CPdicJob extends TimerTask {
  private static Logger logger = Logger.getLogger(CPdicJob.class);
  private Map<String, Integer> hashMap = new HashMap();
  private Map<String, Integer> infoMap = new HashMap();

  public CPdicJob() {
  }

  public void run() {
    this.hashMap = new HashMap();
    this.infoMap = new HashMap();
    File file = new File(InitParam.CUST_PATH);
    File[] fs = file.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith("ctl");
      }
    });
    if (fs != null && fs.length != 0) {
      File ctlFile = fs[0];
      File gzcustFile = new File(InitParam.CUST_PATH + "/" + ctlFile.getName().replace(".ctl", ""));
      if (!gzcustFile.exists()) {
        ctlFile.delete();
      } else {
        String custs = GZIPUtil.unCompressFile(gzcustFile.getAbsolutePath());
        File custFile = new File(custs);
        LineIterator lines = null;
        File tmp = new File("tmp");
        if (!tmp.exists()) {
          tmp.mkdirs();
        }

        File hashfile = new File("tmp" + File.separator + InitParam.hashDict);
        File infofile = new File("tmp" + File.separator + InitParam.infoExtDict);
        File localfile = new File("tmp" + File.separator + InitParam.localDict);
        FileWriterWithEncoding hashfileWriter = null;
        FileWriterWithEncoding infofileWriter = null;
        FileWriterWithEncoding localfileWriter = null;

        try {
          hashfileWriter = new FileWriterWithEncoding(hashfile, "GBK");
          infofileWriter = new FileWriterWithEncoding(infofile, "GBK");
          localfileWriter = new FileWriterWithEncoding(localfile, "GBK");
        } catch (IOException var34) {
          var34.printStackTrace();
          logger.error(var34.getMessage());
        }

        try {
          lines = FileUtils.lineIterator(custFile, "utf-8");

          label369:
          while(true) {
            String[] _lines;
            do {
              do {
                do {
                  String line;
                  do {
                    if (!lines.hasNext()) {
                      break label369;
                    }

                    line = lines.nextLine();
                  } while(StringUtils.isBlank(line));

                  _lines = line.split("\\|");

                  for(int i = 0; i < _lines.length; ++i) {
                    _lines[i] = _lines[i].replace("!", "");
                  }
                } while(_lines == null);
              } while(_lines.length < 44);
            } while(StringUtils.isBlank(_lines[27]) && StringUtils.isBlank(_lines[37]));

            String cnname = _lines[27];
            String enname = _lines[37];

            try {
              Iterator var20 = InitParam.filtercusts.iterator();

              String cusname;
              while(var20.hasNext()) {
                cusname = (String)var20.next();
                if (cusname.equals(cnname) || cusname.equals(enname)) {
                  continue label369;
                }
              }

              if (StringUtils.isBlank(enname) && StringUtils.isNotBlank(cnname) && cnname.trim().length() >= 4 && cnname.indexOf("\\") == -1) {
                this.parseHash(hashfileWriter, cnname.trim(), _lines);
                this.parseInfo(infofileWriter, cnname.trim(), _lines);
                this.parseLocal(localfileWriter, cnname.trim(), _lines);
              }

              if (StringUtils.isBlank(cnname) && StringUtils.isNotBlank(enname) && enname.trim().length() >= 4 && enname.indexOf("\\") == -1) {
                this.parseHash(hashfileWriter, enname.trim(), _lines);
                this.parseInfo(infofileWriter, enname.trim(), _lines);
                this.parseLocal(localfileWriter, enname.trim(), _lines);
              }

              if (!StringUtils.isBlank(cnname) && StringUtils.isNotBlank(enname) && (enname.trim().length() >= 4 || cnname.trim().length() >= 4)) {
                cusname = cnname;
                if (enname.indexOf("\\") == -1 || cnname.indexOf("\\") == -1) {
                  if (enname.indexOf("\\") == -1 && cnname.indexOf("\\") != -1) {
                    cusname = enname;
                  }

                  if (cnname.trim().length() > 3) {
                    this.parseHash(hashfileWriter, cusname.trim(), _lines);
                    this.parseInfo(infofileWriter, cusname.trim(), _lines);
                    this.parseLocal(localfileWriter, cusname.trim(), _lines);
                  }
                }
              }
            } catch (Exception var36) {
              var36.printStackTrace();
              logger.error(var36.getMessage());
            }
          }
        } catch (IOException var37) {
          var37.printStackTrace();
        } finally {
          try {
            if (lines != null) {
              lines.close();
            }

            hashfileWriter.flush();
            hashfileWriter.close();
            infofileWriter.flush();
            infofileWriter.close();
            localfileWriter.flush();
            localfileWriter.close();
          } catch (IOException var32) {
            var32.printStackTrace();
            logger.error(var32.getMessage());
          }

        }

        String[] CKM_HOSTS = InitParam.CKM_HOSTS;

        for(int i = 0; i < CKM_HOSTS.length; ++i) {
          TrsCkmSoapClient trsCKMClient = new TrsCkmSoapClient(CKM_HOSTS[i], InitParam.CKM_USER, InitParam.CKM_PASSWD);

          try {
            int ret = trsCKMClient.UpLoadInfoExtDict(infofile.getAbsolutePath(), 256, "");
            if (ret == 0) {
              System.out.println("全简称词典上传成功");
            }

            int ret_ = trsCKMClient.UpLoadHashDict(hashfile.getAbsolutePath(), 256, "");
            if (ret_ == 0) {
              System.out.println("映射词典上传成功");
            }
          } catch (CkmSoapException var35) {
            var35.printStackTrace();
            logger.error(var35.getMessage());
          }
        }

        try {
          FileUtils.copyFile(localfile, new File(CPdicJob.class.getResource("/").getPath() + InitParam.custfilename), true);
          InitParam.initCust();
        } catch (IOException var33) {
          var33.printStackTrace();
          logger.error(var33.getMessage());
        }

        ctlFile.delete();
        hashfile.delete();
        infofile.delete();
        localfile.delete();
        custFile.delete();
      }
    }
  }

  private void parseHash(FileWriterWithEncoding fileWriter, String fullname, String[] lines) throws IOException {
    List<String> lis = this.processBracket(fullname.trim());
    Iterator var6 = lis.iterator();

    while(var6.hasNext()) {
      String str = (String)var6.next();
      if (!this.hashMap.containsKey(str + fullname.trim())) {
        this.hashMap.put(str + fullname.trim(), Integer.valueOf(1));
        fileWriter.append(str + "\t" + fullname.trim() + "\n");
      }
    }

    for(int i = 27; i < lines.length && i < 44; ++i) {
      if (StringUtils.isNotBlank(lines[i]) && lines[i].trim().length() > 3 && lines[i].indexOf("\\") == -1) {
        Iterator var7 = InitParam.filtercusts.iterator();

        String str;
        do {
          if (!var7.hasNext()) {
            if (!this.hashMap.containsKey(lines[i].trim() + fullname.trim())) {
              this.hashMap.put(lines[i].trim() + fullname.trim(), Integer.valueOf(1));
              fileWriter.append(lines[i].trim() + "\t" + fullname + "\n");
            }
            break;
          }

          str = (String)var7.next();
        } while(!str.equals(lines[i].trim()));
      }
    }

  }

  private List<String> processBracket(String chineseFullName) {
    List<String> fullNameList = new ArrayList();
    String pattern = ".*(\\(|（).*(\\)|）).*";
    if (chineseFullName.matches(pattern)) {
      String englishPreHalf = chineseFullName.replaceAll("（", "(");
      String englishFull = englishPreHalf.replaceAll("）", ")");
      fullNameList.add(englishFull);
      String englishChinese = englishPreHalf.replaceAll("\\)", "）");
      fullNameList.add(englishChinese);
      String chinesePreHalf = chineseFullName.replaceAll("\\(", "（");
      String chineseFull = chinesePreHalf.replaceAll("\\)", "）");
      fullNameList.add(chineseFull);
      String chineseEnglish = chinesePreHalf.replaceAll("）", ")");
      fullNameList.add(chineseEnglish);
    } else {
      fullNameList.add(chineseFullName);
    }

    return fullNameList;
  }

  private void parseInfo(FileWriterWithEncoding fileWriter, String fullname, String[] lines) throws IOException {
    List<String> lis = this.processBracket(fullname.trim());
    Iterator var6 = lis.iterator();

    while(var6.hasNext()) {
      String str = (String)var6.next();
      if (!this.infoMap.containsKey(str)) {
        this.infoMap.put(str, Integer.valueOf(1));
        fileWriter.append(str + "\n");
      }
    }

    for(int i = 27; i < lines.length && i < 44; ++i) {
      if (StringUtils.isNotBlank(lines[i]) && lines[i].trim().length() > 3 && lines[i].indexOf("\\") == -1) {
        Iterator var7 = InitParam.filtercusts.iterator();

        String str;
        do {
          if (!var7.hasNext()) {
            if (!this.infoMap.containsKey(lines[i].trim())) {
              this.infoMap.put(lines[i].trim(), Integer.valueOf(1));
              fileWriter.append(lines[i].trim() + "\n");
            }
            break;
          }

          str = (String)var7.next();
        } while(!str.equals(lines[i].trim()));
      }
    }

  }

  private void parseLocal(FileWriterWithEncoding fileWriter, String fullname, String[] lines) throws IOException {
    if (StringUtils.isNotBlank(lines[1]) && lines[1].trim().length() > 3) {
      fileWriter.append(lines[1].trim() + "\t" + fullname.trim() + "\n");
    } else if (StringUtils.isNotBlank(lines[2]) && lines[2].trim().length() > 3) {
      fileWriter.append(lines[2].trim() + "\t" + fullname.trim() + "\n");
    } else if (StringUtils.isNotBlank(lines[3]) && lines[3].trim().length() > 3) {
      fileWriter.append(lines[3].trim() + "\t" + fullname.trim() + "\n");
    } else if (StringUtils.isNotBlank(lines[4]) && lines[4].trim().length() > 3) {
      fileWriter.append(lines[4].trim() + "\t" + fullname.trim() + "\n");
    } else if (StringUtils.isNotBlank(lines[5]) && lines[5].trim().length() > 3) {
      fileWriter.append(lines[5].trim() + "\t" + fullname.trim() + "\n");
    } else if (StringUtils.isNotBlank(lines[5]) && lines[5].trim().length() > 3) {
      fileWriter.append(lines[5].trim() + "\t" + fullname.trim() + "\n");
    }
  }
}
