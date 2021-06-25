package com.abc;

import com.abc.util.TRSFileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\datatemp\\CRUV\\OUT\\TRS\\abc_cust\\custname.txt");
        Map<String,String> map = TRSFileUtil.getTxtMap(file);
        FileWriter fw = new FileWriter("D:\\datatemp\\CRUV\\OUT\\TRS\\abc_cust\\custnamebig.txt");
        for (int i = 0; i < 6; i++) {

        for(String key : map.keySet()){
            String value = map.get(key);
            fw.append(value+"\t"+key+i+"\r\n");
        }

        }
        fw.flush();
        fw.close();
    }
}
