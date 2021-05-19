package com.abc.test;


import java.io.IOException;

/**
 * ���ܽ��ܹ�����
 */
public class EncryUtil {

    /**
     * ʹ��Ĭ����Կ����DES����
     */
    public static String encrypt(String plainText) {
        try {
            return new DES().encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * ʹ��ָ����Կ����DES����
     */
    public static String encrypt(String plainText, String key) {
        try {
            return new DES(key).encrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * ʹ��Ĭ����Կ����DES����
     */
    public static String decrypt(String plainText) {
        try {
            return new DES().decrypt(plainText);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * ʹ��ָ����Կ����DES����
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
         * ��ѹ�ļ�
         */
//        File zipFile = new File("D:\\EPRO\\projectFile\\TRS\\nonghang\\wendang\\temp\\Ŀ¼.zip");
//        String path = "D:\\EPRO\\projectFile\\TRS\\nonghang\\wendang\\temp\\";
//        unZipFiles(zipFile, path);

        String str = "����";
        String t = "";
        System.out.println("���ܺ�" + (t = EncryUtil.encrypt(str)));
        System.out.println("���ܺ�" + EncryUtil.decrypt(t));

    }
}
