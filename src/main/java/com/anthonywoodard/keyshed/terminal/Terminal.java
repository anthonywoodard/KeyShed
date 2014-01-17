package com.anthonywoodard.keyshed.terminal;

import com.anthonywoodard.keyshed.util.EncUtil;
import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anthony Woodard
 */
public class Terminal {
    
  final Logger logger = LoggerFactory.getLogger(Terminal.class);
    private Console c;        
    
    public Terminal() {
      c = System.console();
      if (c == null) {
        System.err.println("No console.");
        System.exit(1);
      }      
    }
    
    public String initOption(Boolean canRegister) {        
        String optPrompt = "Enter r to register or q to quit: ";
        if (canRegister == false) {
          optPrompt = "Enter i to login or q to quit: ";
        }
        String opt = c.readLine(optPrompt);                         
        opt = opt.trim();
        StringBuilder cmd = new StringBuilder("user ");         
        if(opt.equalsIgnoreCase("i")) {
          cmd.append("-login");
        } else if(opt.equalsIgnoreCase("r")) {
          cmd.append("-register");
        } else if(opt.equalsIgnoreCase("q")) {
          this.quit();
        } else {
          cmd.append("-init");
        }        
        return cmd.toString();
    }        
    
    public String doPrompt() {
        String promptValue = c.readLine("ks> ");
        return promptValue;
    }
    
    public Console getConsole() {
        return this.c;
    }
    
    public void quit() {
      System.exit(1);
    }
    
    public void showStatusCode(String msg) {
      System.err.println(msg);
    }
    
    public void echo(String msg) {
      System.out.println(msg);
    }
    
    public void showHelp() {
    c.format("\nFormat: command -[parameter]\n\n");
    c.format("User Commands:\n" +
      "     user -del\n" +
      "     user -list\n" +
      "     user -login\n" +
      "     user -new\n" +
      "     user -password\n" +
      "     user -register\n\n");
    c.format("Key Shed CRUD Parameters:  (c-category i-id p-password t-title u-username w-url)\n\n");      
    c.format("Key Shed CRUD Commands: \n" +
      "     copy -i=[] || -t=[] (title must be unique. will copy password to clipboard)\n" +
      "     del -i=[] || -t=[] (title must be unique)\n" +
      "     find -i=[] || -c=[] || -t=[]\n" +      
      "     list (table view)\n" +
      "     llist (list view)\n" +
      "     new (Required: -t=[] && -u=[] && -p=[]) (Optional: -c=[] || -w=[])\n" +
      "     update (Required: -i=[]) (Optional: -c=[] || -p=[] || -u=[] || -w=[] || -t=[])\n\n");
    c.format("Key Shed Export Parameter: (f-file to export to)\n\n");
    c.format("Key Shed Export Command: \n" +
      "     export (Required: -f=[])\n\n");
    c.format("Key Shed Import Parameters: ([f-csv file to import] [r-first row contains column headers])\n\n");
    c.format("Key Shed Import Command: \n" +
      "     import (Required: -f=[] && -r=[true || false])\n\n");
    c.format("General Commands:\n" +
      "     help\n" +
      "     quit\n" +
      "     exit\n\n");
  }
    
  public void showSplash() {
    System.out.println("KeyShed Baby!!!");
  }
    
  public byte[] encrypt(String ctext) {  
    byte[] etext = null;
    try {                
      etext = EncUtil.encryptMsg(ctext);
    } catch (NoSuchAlgorithmException ex) {
      logger.error(ex.toString()); 
    } catch (NoSuchPaddingException ex) {
      logger.error(ex.toString()); 
    } catch (InvalidKeyException ex) {
      logger.error(ex.toString()); 
    } catch (InvalidParameterSpecException ex) {
      logger.error(ex.toString()); 
    } catch (IllegalBlockSizeException ex) {
      logger.error(ex.toString()); 
    } catch (BadPaddingException ex) {
      logger.error(ex.toString()); 
    } catch (UnsupportedEncodingException ex) {
      logger.error(ex.toString()); 
    }

    return etext;
  }

  public String decrypt(byte[] ctext) {
    String dtext = null;
    try {
      dtext = EncUtil.decryptMsg(ctext);
    } catch (NoSuchPaddingException ex) {
      logger.error(ex.toString()); 
    } catch (NoSuchAlgorithmException ex) {
      logger.error(ex.toString()); 
    } catch (InvalidParameterSpecException ex) {
      logger.error(ex.toString()); 
    } catch (InvalidAlgorithmParameterException ex) {
      logger.error(ex.toString()); 
    } catch (InvalidKeyException ex) {
      logger.error(ex.toString()); 
    } catch (BadPaddingException ex) {
      logger.error(ex.toString()); 
    } catch (IllegalBlockSizeException ex) {
      logger.error(ex.toString()); 
    } catch (UnsupportedEncodingException ex) {
      logger.error(ex.toString()); 
    }
    return dtext;

  }
}
