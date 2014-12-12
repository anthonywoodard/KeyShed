package com.anthonywoodard.keyshed.util;

/**
 *
 * @author Anthony Woodard
 */
public final class Constants {
  
  public static final String SUCCESS = "Success";
  public static final String FAILURE = "Failure";
  
  public static final boolean PASS = true;
  public static final boolean FAIL = false;
  
  public static final boolean IS_IN_ERROR = true;
  public static final boolean NOT_IN_ERROR = false;
  
  public static final String INVALID_USER = "Invalid User";
  public static final String USER_EXISTS = "User Exists";
  
  public static final String INVALID_KEY = "Invalid Key";
  public static final String KEY_EXISTS = "Key Exists";
  
  public static final String INVALID_PERMISSIONS = "Invalid Permissions";
  public static final String INVALID_COMMAND = "Invalid command. Type help to see options.";
  
  public static final int NOT_FOUND = -1;
  
  public static final String NEW_LINE = System.getProperty("line.separator");  
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");  
  public static final String PATH_SEPARATOR = System.getProperty("path.separator");
  
  public static final String EMPTY_STRING = "";
  public static final String SPACE = " ";
  public static final String TAB = "\t";
  public static final String SINGLE_QUOTE = "'";
  public static final String PERIOD = ".";
  public static final String DOUBLE_QUOTE = "\"";
  public static final String PARAMETER_PREFIX = "-";
  public static final String EQUAL = "=";
  public static final String COMMA = ",";
  
  
  private Constants() {}
}
