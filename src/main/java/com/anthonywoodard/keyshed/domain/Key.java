package com.anthonywoodard.keyshed.domain;

import java.util.Comparator;
import com.anthonywoodard.keyshed.util.EncUtil;

/**
 *
 * @author Anthony Woodard
 */
public class Key extends Domain implements Comparable<Key> {
  
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

  public int compareTo(Key compareKey) {
	  int compareKeyId = ((Key) compareKey).getKeyId();
	  
	  //ascending order
	  return this.keyId - compareKeyId;
	  
	  //descending order
	  //return compareKeyId - this.keyId;
  }
  
  public static Comparator<Key> KeyDefaultComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String category1 = EncUtil.decrypt(key1.getKeyCategory()).toUpperCase();
		  String category2 = EncUtil.decrypt(key2.getKeyCategory()).toUpperCase();
		  
		  //ascending order
		  //sub sort by title
		  int result = category1.compareTo(category2);
		  if (result == 0) {
			  String title1 = EncUtil.decrypt(key1.getKeyTitle()).toUpperCase();
			  String title2 = EncUtil.decrypt(key2.getKeyTitle()).toUpperCase();
			  result = title1.compareTo(title2);
		  }
		  return result;		  		  
	  }
  };
  
  public static Comparator<Key> KeyCategoryComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String category1 = EncUtil.decrypt(key1.getKeyCategory()).toUpperCase();
		  String category2 = EncUtil.decrypt(key2.getKeyCategory()).toUpperCase();
		  
		  //ascending order
		  return category1.compareTo(category2);		  
		  
		  //descending order
		  //return category2.compareTo(category1);
	  }
  };
  
  public static Comparator<Key> KeyTitleComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String title1 = EncUtil.decrypt(key1.getKeyTitle()).toUpperCase();
		  String title2 = EncUtil.decrypt(key2.getKeyTitle()).toUpperCase();
		  
		  //ascending order
		  return title1.compareTo(title2);
	  }
  };
  
  public static Comparator<Key> KeyUrlComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String url1 = EncUtil.decrypt(key1.getKeyUrl()).toUpperCase();
		  String url2 = EncUtil.decrypt(key2.getKeyUrl()).toUpperCase();
		  
		  //ascending order
		  return url1.compareTo(url2);
	  }
  };
  
  public static Comparator<Key> KeyUsernameComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String username1 = EncUtil.decrypt(key1.getKeyUsername()).toUpperCase();
		  String username2 = EncUtil.decrypt(key2.getKeyUsername()).toUpperCase();
		  
		  //ascending order
		  return username1.compareTo(username2);
	  }
  };
  
  public static Comparator<Key> KeyPasswordComparator = new Comparator<Key>() {
	  public int compare(Key key1, Key key2) {
		  String password1 = EncUtil.decrypt(key1.getKeyPassword()).toUpperCase();
		  String password2 = EncUtil.decrypt(key2.getKeyPassword()).toUpperCase();
		  
		  //ascending order
		  return password1.compareTo(password2);
	  }
  };
  
}
