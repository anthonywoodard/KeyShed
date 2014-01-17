package com.anthonywoodard.keyshed.domain;

/**
 *
 * @author Anthony Woodard
 */
public class Key extends Domain {
  
  private int keyId = -1;  
  private byte[] keyTitle = null;
  private byte[] keyUsername = null;
  private byte[] keyPassword = null;  
  private byte[] keyUrl = null;
  private byte[] keyCategory = null;

  public int getKeyId() {
    return this.keyId;
  }

  public byte[] getKeyTitle() {
    return this.keyTitle;
  }
  
  public byte[] getKeyUsername() {
    return this.keyUsername;
  }
  
  public byte[] getKeyPassword() {
    return this.keyPassword;
  }  
  
  public byte[] getKeyUrl() {
    return this.keyUrl;
  }
  
  public byte[] getKeyCategory() {
    return this.keyCategory;
  }
   
  public void setKeyId(int id) {
    this.keyId = id;
  }
  
  public void setKeyTitle(byte[] title) {
    if(title != null && title.length > 0) {
      this.keyTitle = title;
    }
  }
  
  public void setKeyUsername(byte[] uname) {
    if(uname != null && uname.length > 0) {
      this.keyUsername = uname;
    }
  }	
  
  public void setKeyPassword(byte[] pwd) {
    if(pwd != null && pwd.length > 0) {
      this.keyPassword = pwd;
    }
  }
  
  public void setKeyUrl(byte[] url) {
    if(url != null && url.length > 0) {
      this.keyUrl = url;
    }
  }
  
  public void setKeyCategory(byte[] cat) {
    if(cat != null && cat.length > 0) {
      this.keyCategory = cat;
    }
  }
  
}
