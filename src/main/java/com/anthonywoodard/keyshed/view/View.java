package com.anthonywoodard.keyshed.view;

/**
 *
 * @author Anthony Woodard
 */
public interface View {
  public String initOption(Boolean canRegister);    
  public String doPrompt();
  public void showStatusCode(String msg);
  public void echo(String msg);
  public void showSplash();
  public void quit();
  public byte[] encrypt(String ctext);
  public String decrypt(byte[] ctext);  
}
