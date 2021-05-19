//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TRSParser {
  private static final Logger logger = Logger.getLogger(TRSParser.class);

  public TRSParser() {
  }

  public static void main(String[] args) {
  }

  public static List<Map<String, Object>> readFile(File datafile, String charset, boolean cp, String cppath) {
    List<String> fileNames = new ArrayList();
    if (datafile == null) {
      logger.debug("读取数据文件null");
      return null;
    } else if (!datafile.exists()) {
      logger.debug("读取数据文件不存在");
      return null;
    } else if (datafile.isDirectory()) {
      logger.debug("读取为目录");
      return null;
    } else {
      String prefix = "";
      Pattern pattern = Pattern.compile("^" + prefix.replace("$", "\\$").replace("^", "\\^").replace("*", "\\*").replace("?", "\\?").replace("}", "\\}").replace("!", "\\!").replace("=", "\\=").replace("{", "\\{") + "<[^>=]+>=");
      LineIterator it = null;
      ArrayList outputDatas = new ArrayList();

      label187: {
        try {
          it = FileUtils.lineIterator(datafile, charset);
          int pos = -1;
          String line = null;
          StringBuilder fieldValue = null;
          HashMap outputData = new HashMap();

          while(it.hasNext()) {
            line = it.nextLine();
            if (line.equalsIgnoreCase(prefix + "<REC>")) {
              if (outputData != null && !outputData.isEmpty()) {
                if (pos >= 0) {
                  outputData.put((String)fileNames.get(pos), fieldValue.toString());
                } else {
                  outputData = new HashMap();
                }

                pos = -1;
                fieldValue = null;
                outputDatas.add(outputData);
              }
            } else {
              Matcher matcher = pattern.matcher(line);
              if (matcher.find()) {
                String tmp = matcher.group();
                String fieldName = tmp.substring(tmp.lastIndexOf("<") + 1, tmp.length() - 2);
                if (!fileNames.contains(fieldName)) {
                  fileNames.add(fieldName);
                }

                if (pos >= 0) {
                  outputData.put((String)fileNames.get(pos), fieldValue.toString());
                } else {
                  outputData = new HashMap();
                }

                pos = fileNames.indexOf(fieldName);
                fieldValue = new StringBuilder(line.substring(tmp.length()));
              } else if (fieldValue != null) {
                fieldValue.append("\r\n").append(line);
              } else {
                fieldValue = new StringBuilder();
              }
            }
          }

          if (outputData != null && !outputData.isEmpty()) {
            if (pos >= 0) {
              outputData.put((String)fileNames.get(pos), fieldValue.toString());
            } else {
              outputData = new HashMap();
            }

//            int pos = true;
            fieldValue = null;
            outputDatas.add(outputData);
          }
          break label187;
        } catch (Exception var21) {
          logger.error("读取数据文件失败" + var21.getMessage());
        } finally {
          it.close();
        }

        return null;
      }

      if (cp) {
        String path = cppath + "/" + DateUtils.dateFormat(new Date(), "yyyy-MM-dd");
        File file2 = new File(path);
        if (!file2.exists()) {
          file2.mkdirs();
        }

        File fileto = new File(path + "/" + datafile.getName());
        if (fileto.exists()) {
          fileto.delete();
        }

        datafile.renameTo(new File(path + "/" + datafile.getName()));
        File okfileto = new File(path + "/" + datafile.getName().replace(".trs", ".ok"));
        if (!okfileto.exists()) {
          try {
            okfileto.createNewFile();
          } catch (IOException var20) {
            var20.printStackTrace();
          }
        }
      } else {
//        datafile.delete();
      }

      return outputDatas;
    }
  }

  public static List<String> readFileFs() {
    String templateName = "D:/data/trs_file_164_2017_05_17_10_50_46_-845764580.trs";
    List<String> fileNames = new ArrayList();
    if (StringUtils.isEmpty(templateName)) {
      return fileNames;
    } else {
      File file = new File(templateName);
      String prefix = "";
      Pattern pattern = Pattern.compile("^" + prefix.replace("$", "\\$").replace("^", "\\^").replace("*", "\\*").replace("?", "\\?").replace("}", "\\}").replace("!", "\\!").replace("=", "\\=").replace("{", "\\{") + "<[^>=]+>=");
      LineIterator it = null;

      try {
        it = FileUtils.lineIterator(file, "GBK");
        boolean first = false;
        String line = null;

        while(it.hasNext()) {
          line = it.nextLine();
          if (line.equalsIgnoreCase(prefix + "<REC>")) {
            if (first) {
              break;
            }

            first = true;
          } else {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
              String tmp = matcher.group();
              String fieldName = tmp.substring(tmp.lastIndexOf("<") + 1, tmp.length() - 2);
              if (!fileNames.contains(fieldName)) {
                fileNames.add(fieldName);
              }
            }
          }
        }
      } catch (Exception var11) {
        ;
      }

      return fileNames;
    }
  }

  public static StringBuilder filePathHandling(File trsFile, StringBuilder builder) {
    if (trsFile == null) {
      return builder;
    } else {
      String filePath = builder.toString();
      StringBuilder sb = new StringBuilder();
      String[] array = filePath.split(";");
      String[] var8 = array;
      int var7 = array.length;

      for(int var6 = 0; var6 < var7; ++var6) {
        String path = var8[var6];
        boolean atflag = false;
        if (path.startsWith("@")) {
          path = path.substring(1);
          atflag = true;
        }

        String path2 = path;
        int pos = path.indexOf("^");
        if (pos > 0) {
          path2 = path.substring(0, pos);
        }

        if (!(new File(path2)).exists()) {
          if (path.startsWith("/") || path.startsWith("\\")) {
            path = path.substring(1);
          }

          if ((new File(trsFile.getParent() + "/" + path2)).exists()) {
            path = trsFile.getParent() + "/" + path;
          } else {
            path = path;
          }
        }

        if (atflag) {
          sb.append("@");
        }

        sb.append(path).append('\u007f');
      }

      sb.deleteCharAt(sb.length() - 1);
      return sb;
    }
  }
}
