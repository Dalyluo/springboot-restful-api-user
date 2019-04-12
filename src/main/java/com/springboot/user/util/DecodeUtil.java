package com.springboot.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecodeUtil {

    private final static Logger logger = LoggerFactory.getLogger(DecodeUtil.class);


    /**
     * 3.对字符串进行加密
     * 1）. 数字签名保证信息完整性
     * 2）. 3DES加密保证不可阅读性
     * 3）. BASE64编码
     * Base64( 3DES( MD5( 消息体 ) + 消息体)
     *
     * @param str String
     * @return String
     */
    public static String encrypt(String str, String desSecret) {
        String body = null;

        try {
            DESTools des = DESTools.getInstance(desSecret);
            String md5str = MD5.md5(str) + str;
            byte[] b = des.encrypt(md5str.getBytes("UTF8"));
            body = Base64.encode(b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return body;
    }

    /**
     * 4.对字符串进行解密
     * Base64( 3DES( MD5( 消息体 ) + 消息体) 逆操作
     *
     * @param str String
     * @return String
     * 解密时判断数据签名，如果不匹配则返回null
     */
    public static String decrypt(String str, String desSecret) {
        String body = null;
        try {
            DESTools des = DESTools.getInstance(desSecret);
            byte[] b = Base64.decode(str);
            String md5body = new String(des.decrypt(b), "UTF8");
            if (md5body.length() < 32) {
                logger.info("错误！消息体长度小于数字签名长度!");
                return null;
            }
            String md5Client = md5body.substring(0, 32);
            body = md5body.substring(32);
            String md5Server = MD5.md5(body);
            if (!md5Client.equals(md5Server)) {
                logger.info("错误！数字签名不匹配:");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info("报文解密异常:" + ex.getMessage());
        }
        return body;
    }
    public static void  main(String [] args){
//        String secretCode = DecodeUtil.encrypt("13802448557","C07AFE941CE8C2DE");
//        String result = DecodeUtil.decrypt(secretCode,"C07AFE941CE8C2DE");
//        System.out.println(result);

        System.out.println("13802448557".getBytes());
    }
}