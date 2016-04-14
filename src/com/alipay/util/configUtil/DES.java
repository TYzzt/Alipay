package com.alipay.util.configUtil;

import com.alipay.config.AlipayConfig;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Copyright 2007 GuangZhou Cotel Co. Ltd. 
 * All right reserved.     
 * DES加密解密类.      
 * @author <a href="mailto:xiexingxing1121@126.com" mce_href="mailto:xiexingxing1121@126.com">AmigoXie</a> 
 * @version 1.0
 * Creation date: 2007-7-31 - 上午11:59:28 
 */
public class DES {
    /** 加密、解密key. */
    /** 加密算法,可用 DES,DESede,Blowfish. */
    private final static String ALGORITHM = "DES";
    //测试
//    public static void main(String[] args) throws Exception {
//        String md5Password = "202cb962ac59075b964b07152d234b70";
//        String str = DES.encrypt(md5Password);
//        System.out.println("str: " + str);
//        str = DES.decrypt(str);
//        System.out.println("str: " + str);
//    }

    /**
     * 对数据进行DES加密. 
     * @param data 待进行DES加密的数据 
     * @return 返回经过DES加密后的数据
     * @throws Exception
     * Creation date: 2007-7-31 - 下午12:06:24
     */
    public final static String decrypt(String data) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()),
                AlipayConfig.PASSWORD_CRYPT_KEY.getBytes()));
    }
    /**
     * 对用DES加密过的数据进行解密. 
     * @param data DES加密数据 
     * @return 返回解密后的数据
     * @throws Exception
     * Creation date: 2007-7-31 - 下午12:07:54
     */
    public final static String encrypt(String data) throws Exception  {
        return byte2hex(encrypt(data.getBytes(), AlipayConfig.PASSWORD_CRYPT_KEY
                .getBytes()));
    }

    /**
     * 用指定的key对数据进行DES加密. 
     * @param data 待加密的数据 
     * @param key DES加密的key 
     * @return 返回DES加密后的数据
     * @throws Exception
     * Creation date: 2007-7-31 - 下午12:09:03
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密  
        // 正式执行加密操作  
        return cipher.doFinal(data);
    }
    /**
     * 用指定的key对数据进行DES解密. 
     * @param data 待解密的数据 
     * @param key DES解密的key 
     * @return 返回DES解密后的数据
     * @throws Exception
     * Creation date: 2007-7-31 - 下午12:10:34
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源  
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象  
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成  
        // 一个SecretKey对象  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密  
        // 正式执行解密操作  
        return cipher.doFinal(data);
    }
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    /**
     * 加密
     * @param data
     * @param mixKey
     * @return jiamidata
     * @throws Exception
     */
    public final static String jiami(String data,String mixKey) throws Exception{
        String key = praseKey(mixKey);
        return byte2hex(encrypt(data.getBytes(), key
                .getBytes()));
    }
    /**
     * 解密
     * @param jiamidata
     * @param mixKey
     * @return data
     * @throws Exception
     */
    public final static String jiemi(String data,String mixKey) throws Exception{
        String key = praseKey(mixKey);
        return new String(decrypt(hex2byte(data.getBytes()),
                key.getBytes()),"UTF-8");
    }

    /**
     * 生成64位随机串，包含8位key
     * @return
     */
    public final static String createKey(){
        StringBuffer key = new StringBuffer();
        StringBuffer mixKey = new StringBuffer();
        String str="abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
        Random random=new Random();
        for(int i=0;i<8;i++){		//生成8位随机串
            int number=random.nextInt(59);
            key.append(str.charAt(number));
        }
        for(int i=0;i<64;i++){	 //生成64位随机串,插入8位key
            int number=random.nextInt(59);
            switch (i) {
                case 1:
                    mixKey.append(key.charAt(0));
                    break;
                case 3:
                    mixKey.append(key.charAt(1));
                    break;
                case 5:
                    mixKey.append(key.charAt(2));
                    break;
                case 11:
                    mixKey.append(key.charAt(3));
                    break;
                case 13:
                    mixKey.append(key.charAt(4));
                    break;
                case 19:
                    mixKey.append(key.charAt(5));
                    break;
                case 23:
                    mixKey.append(key.charAt(6));
                    break;
                case 29:
                    mixKey.append(key.charAt(7));
                    break;
                default:
                    mixKey.append(str.charAt(number));
                    break;
            }

        }
        return mixKey.toString();
    }

    /**
     * 从mixKey中解析key值
     * @param mixKey
     * @return
     */
    public static String praseKey(String mixKey){
        StringBuffer key = new StringBuffer();
        for(int i=0;i<64;i++){
            switch (i) {
                case 1:
                    key.append(mixKey.charAt(1));
                    break;
                case 3:
                    key.append(mixKey.charAt(3));
                    break;
                case 5:
                    key.append(mixKey.charAt(5));
                    break;
                case 11:
                    key.append(mixKey.charAt(11));
                    break;
                case 13:
                    key.append(mixKey.charAt(13));
                    break;
                case 19:
                    key.append(mixKey.charAt(19));
                    break;
                case 23:
                    key.append(mixKey.charAt(23));
                    break;
                case 29:
                    key.append(mixKey.charAt(29));
                    break;
            }
        }
        return key.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DES.jiemi("C571573119722729324251CF5F45FE1D012A435E64C01F7D63058F0D226CDC73A785D75365CCD98F79B7724DEA315A55", "7rsl1wnBrX7sPFjGAmYgJAMaRUsYcfSgpLZwCn7heXcUVk2tsMZ55JJ6DVuKUrtN"));
    }

}  