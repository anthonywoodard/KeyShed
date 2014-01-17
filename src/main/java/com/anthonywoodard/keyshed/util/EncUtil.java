package com.anthonywoodard.keyshed.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Properties;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Anthony Woodard
 */

public class EncUtil {
  private static SecretKeySpec secret;
  private static Random rnd = new Random();
  
  public static void generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
    String salt;
    Properties prop = new Properties(); 
    File file = new File("ks.cfg");
    
    if(file.exists()) {
      prop.load(new FileInputStream("ks.cfg"));
    } else {
      prop.setProperty("salt", EncUtil.getRandomNumber(128));
      prop.store(new FileOutputStream("ks.cfg"), null);
    }    
    
    String cfgSalt = prop.getProperty("salt");
    if(cfgSalt.length() < 128) {
      throw new Exception("Invalid salt length.  Must be 128 characters.");
    } else {
      salt = cfgSalt.substring(37, 53);      
    }
    
    secret = new SecretKeySpec(salt.getBytes(), "AES");      
    
    //For testing to see current working directory
    //String current = new java.io.File( "." ).getCanonicalPath();
    //System.out.println("Current dir:"+current);        
  }
  
  public static byte[] encryptMsg(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {        
    if (message == null) {
      return new byte[0];
    }
    /* Encrypt the message. */
    Cipher cipher = null;
    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");    
    cipher.init(Cipher.ENCRYPT_MODE, secret);
    byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));    
    return cipherText;
  }

  public static String decryptMsg(byte[] cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
    if (cipherText == null) {
      return "";
    }
    /* Decrypt the message, given derived encContentValues and initialization vector. */
    Cipher cipher = null;
    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");    
    cipher.init(Cipher.DECRYPT_MODE, secret);
    String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
    return decryptString;
  }
  
  private static String getRandomNumber(int digCount) {
    StringBuilder sb = new StringBuilder(digCount);
    for(int i=0; i < digCount; i++) {
        sb.append((rnd.nextInt(10)));        
    }
    return sb.toString();
  }
}
