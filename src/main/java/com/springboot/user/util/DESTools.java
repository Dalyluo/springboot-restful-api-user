package com.springboot.user.util;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DESTools
{
  private Key key;
  private Cipher cipher;
  
  public static DESTools getInstance(String key)
    throws NoSuchPaddingException, NoSuchAlgorithmException
  {
    return getInstance(getKeyByStr(key));
  }
  
  public static DESTools getInstance(byte[] key)
    throws NoSuchPaddingException, NoSuchAlgorithmException
  {
    DESTools des = new DESTools();
    if (des.key == null)
    {
      SecretKeySpec spec = new SecretKeySpec(key, "DES");
      des.key = spec;
    }
    des.cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    return des;
  }
  
  public byte[] encrypt(byte[] b)
    throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    byte[] byteFina = null;
    this.cipher.init(1, this.key);
    byteFina = this.cipher.doFinal(b);
    return byteFina;
  }
  
  public byte[] decrypt(byte[] b)
    throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IllegalStateException
  {
    byte[] byteFina = null;
    this.cipher.init(2, this.key);
    byteFina = this.cipher.doFinal(b);
    return byteFina;
  }
  
  public static byte[] getKeyByStr(String str)
  {
    byte[] bRet = new byte[str.length() / 2];
    for (int i = 0; i < str.length() / 2; i++)
    {
      Integer itg = new Integer(16 * getChrInt(str.charAt(2 * i)) + getChrInt(str.charAt(2 * i + 1)));
      
      bRet[i] = itg.byteValue();
    }
    return bRet;
  }
  
  private static int getChrInt(char chr)
  {
    int iRet = 0;
    if (chr == "0".charAt(0)) {
      iRet = 0;
    }
    if (chr == "1".charAt(0)) {
      iRet = 1;
    }
    if (chr == "2".charAt(0)) {
      iRet = 2;
    }
    if (chr == "3".charAt(0)) {
      iRet = 3;
    }
    if (chr == "4".charAt(0)) {
      iRet = 4;
    }
    if (chr == "5".charAt(0)) {
      iRet = 5;
    }
    if (chr == "6".charAt(0)) {
      iRet = 6;
    }
    if (chr == "7".charAt(0)) {
      iRet = 7;
    }
    if (chr == "8".charAt(0)) {
      iRet = 8;
    }
    if (chr == "9".charAt(0)) {
      iRet = 9;
    }
    if (chr == "A".charAt(0)) {
      iRet = 10;
    }
    if (chr == "B".charAt(0)) {
      iRet = 11;
    }
    if (chr == "C".charAt(0)) {
      iRet = 12;
    }
    if (chr == "D".charAt(0)) {
      iRet = 13;
    }
    if (chr == "E".charAt(0)) {
      iRet = 14;
    }
    if (chr == "F".charAt(0)) {
      iRet = 15;
    }
    return iRet;
  }

  public static void main(String [] args){
     byte [] bytes = getKeyByStr("C07AFE941CE8C2DE");

    String result  = null;
    try {
      result = new String(bytes);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(bytes.toString());
  }
}
