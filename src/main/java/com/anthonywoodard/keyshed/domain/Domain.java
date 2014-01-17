package com.anthonywoodard.keyshed.domain;

/**
 *
 * @author Anthony Woodard
 */
public class Domain  {
    
  private String statusCode;
  private boolean isInError = false;
  
  public String getStatusCode() {
    return this.statusCode;
  }
  
  public boolean getIsInError() {
    return this.isInError;
  }
  
  public void setStatusCode(String code) {
    this.statusCode = code;
  }
  
  public void setIsInError(boolean err) {
    this.isInError = err;
  }
}
