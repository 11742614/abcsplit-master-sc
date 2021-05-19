package com.abc.test;


import java.io.IOException;

/**
 * 加密解密工具类
 */
public class EncryUtil {

    /**
     * 使用默认密钥进行DES加密
     */
    public static String encrypt(String plainText) {
        try {
            return new DES().encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String encrypt(String plainText, String key) {
        try {
            return new DES(key).encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用默认密钥进行DES解密
     */
    public static String decrypt(String plainText) {
        try {
            return new DES().decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String decrypt(String plainText, String key) {
        try {
            return new DES(key).decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }



    public static void main(String[] args) throws IOException {
        /**
         * 解压文件
         */
//        File zipFile = new File("D:\\EPRO\\projectFile\\TRS\\nonghang\\wendang\\temp\\目录.zip");
//        String path = "D:\\EPRO\\projectFile\\TRS\\nonghang\\wendang\\temp\\";
//        unZipFiles(zipFile, path);

        String str = "中文";
        String t = "";
        System.out.println("加密后：" + (t = EncryUtil.encrypt(str)));
        System.out.println("解密后：" + EncryUtil.decrypt(t));

    }
}
