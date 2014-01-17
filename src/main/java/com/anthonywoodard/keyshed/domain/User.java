package com.anthonywoodard.keyshed.domain;

/**
 *
 * @author Anthony Woodard
 */
public class User extends Domain {
  private int userId;  
  private byte[] username = null;
  private byte[] userPassword = null;  
  private int primaryUser = 0;

  public int getUserId() {
    return this.userId;
  }

  public byte[] getUsername() {
    return this.username;
  }
  
  public byte[] getUserPassword() {
    return this.userPassword;
  }  
  
  public int getPrimaryUser() {
    return this.primaryUser;
  }
  
  public boolean isPrimaryUser() {
    if(this.primaryUser == 1) {
      return true;
    }
    return false;
  }
   
  public void setUserId(int id) {
    this.userId = id;
  }
  
  public void setUsername(byte[] username) {
    if(username != null && username.length > 0) {
      this.username = username;
    }
  }	
  
  public void setUserPassword(byte[] pwd) {
    if(pwd != null && pwd.length > 0) {
      this.userPassword = pwd;
    }
  }
  
  public void setPrimaryUser(int pu) {
    this.primaryUser = pu;
  }
  
}
