package com.anthonywoodard.keyshed.terminal;

import com.anthonywoodard.keyshed.domain.Key;
import com.anthonywoodard.keyshed.util.ConsoleStringTable;
import com.anthonywoodard.keyshed.util.Constants;
import com.anthonywoodard.keyshed.util.Ellipsize;
import com.anthonywoodard.keyshed.util.EncUtil;
import com.anthonywoodard.keyshed.util.ReadCSV;
import com.anthonywoodard.keyshed.view.KeyView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anthony Woodard
 */
public class KeyTerminal extends Terminal implements KeyView {
  
  final Logger logger = LoggerFactory.getLogger(KeyTerminal.class);  
  private TextDevice c;
      
  public KeyTerminal() {    
    c = this.getConsole();
    try {
      EncUtil.generateKey();
    } catch (NoSuchAlgorithmException ex) {
      logger.error(ex.toString());      
      System.exit(1);
    } catch (InvalidKeySpecException ex) {
      logger.error(ex.toString());      
      System.exit(1);
    } catch (Exception ex) {
      logger.error(ex.toString());            
      System.exit(1);
    }
  } 

  public Key newKey(List<String> params) {
    HashMap<String, String> hm = this.splitParams(params);
    Key newKey = new Key();
    newKey.setKeyPassword(this.encrypt((String)hm.get("p")));
    newKey.setKeyTitle(this.encrypt((String)hm.get("t")));
    newKey.setKeyUrl(this.encrypt((String)hm.get("w")));
    newKey.setKeyUsername(this.encrypt((String)hm.get("u")));
    newKey.setKeyCategory(this.encrypt((String)hm.get("c")));
    return newKey;
  }
  
  private HashMap<String, String> splitParams(List<String>params) {
    HashMap<String, String> hm = new HashMap<String, String>();
    Iterator<String>  it = params.iterator();
    while(it.hasNext()) {
      String param = (String) it.next();
      String[] kv = param.split(Constants.EQUAL);
      if(kv.length == 2) {
        String key = kv[0].trim().toLowerCase();
        String val = kv[1].trim();
        hm.put(key, val);
      }
    }
    return hm;
  }

  public void showKeys(List<Key> keys, String layout) {
    /*String fmt = "%1$3s %2$10s %3$10s %4$10s %5$10s %6$10s%n";
    c.format(fmt, "Id", "Category", "Title", "Username", "Password", "URL");
    c.format(fmt, "-----", "-----", "-----", "-----", "-----", "-----");
    for(int i=0; i < keys.size(); i++) {
      int id = keys.get(i).getKeyId();
      String category = (keys.get(i).getKeyCategory() != null ? this.decrypt(keys.get(i).getKeyCategory()) : "");            
      String title = (keys.get(i).getKeyTitle() != null ? this.decrypt(keys.get(i).getKeyTitle()) : "");
      String username = (keys.get(i).getKeyUsername() != null ? this.decrypt(keys.get(i).getKeyUsername()) : "");
      //show password for testing only
      String password = (keys.get(i).getKeyPassword() != null ? this.decrypt(keys.get(i).getKeyPassword()) : "");          
      String url = (keys.get(i).getKeyUrl() != null ? this.decrypt(keys.get(i).getKeyUrl()) : "");
      c.format(fmt, category, id, title, username, password, url);
    }*/
    if (layout.equalsIgnoreCase("long")) {
      String category = "";
      for(int i=0; i < keys.size(); i++) {
        String thisCategory = (keys.get(i).getKeyCategory() != null ? this.decrypt(keys.get(i).getKeyCategory()) : "");
        if (!thisCategory.equalsIgnoreCase(category)) {
          c.format("Category: " + thisCategory + "%n");
          c.format("------------------------------%n");
          category = thisCategory;
        }                  
        c.format("Title:    " + (keys.get(i).getKeyTitle() != null ? this.decrypt(keys.get(i).getKeyTitle()) : "") + "%n");  
        c.format("Username: " + (keys.get(i).getKeyUsername() != null ? this.decrypt(keys.get(i).getKeyUsername()) : "") + "%n"); 
        //show password for testing only
        c.format("Password: " + (keys.get(i).getKeyPassword() != null ? this.decrypt(keys.get(i).getKeyPassword()) : "") + "%n");          
        c.format("Url:      " + (keys.get(i).getKeyUrl() != null ? this.decrypt(keys.get(i).getKeyUrl()) : "") + "%n");      
        c.format("Id:       " + keys.get(i).getKeyId() + "%n%n");
      }
    } else if (layout.equalsIgnoreCase("table")) {
      ConsoleStringTable table = new ConsoleStringTable();    
      table.addString(0, 0, "ID");
      table.addString(0, 1, "Category");
      table.addString(0, 2, "Title");
      table.addString(0, 3, "Username");
      table.addString(0, 4, "Password");
      table.addString(0, 5, "URL");
      for(int i=0; i < keys.size(); i++) {
        Key key = keys.get(i);
        List<String>  columns = new ArrayList<String>();
        columns.add("" + key.getKeyId());
        columns.add(key.getKeyCategory() != null ? Ellipsize.ellipsize(this.decrypt(key.getKeyCategory()), 30) : "");            
        columns.add(key.getKeyTitle() != null ? Ellipsize.ellipsize(this.decrypt(key.getKeyTitle()), 30) : "");
        columns.add(key.getKeyUsername() != null ? Ellipsize.ellipsize(this.decrypt(key.getKeyUsername()), 30) : "");
        //show password for testing only
        columns.add(key.getKeyPassword() != null ? Ellipsize.ellipsize(this.decrypt(key.getKeyPassword()), 30) : "");          
        columns.add(key.getKeyUrl() != null ? Ellipsize.ellipsize(this.decrypt(key.getKeyUrl()), 30) : "");
        for (int j=0; j < columns.size(); j++) {
          table.addString(i + 1, j, columns.get(j));
        }
      }
      System.out.println(table.toString());
    }
  }
  
  public Key findKey(List<String> params) {
    HashMap<String, String> hm = this.splitParams(params);
    Key newKey = new Key();    
    newKey.setKeyId(hm.containsKey("i") ? Integer.parseInt(hm.get("i").toString()) : -1);
    newKey.setKeyPassword(hm.containsKey("p") ? this.encrypt((String)hm.get("p")) : null);
    newKey.setKeyTitle(hm.containsKey("t") ? this.encrypt((String)hm.get("t")) : null);
    newKey.setKeyUrl(hm.containsKey("w") ? this.encrypt((String)hm.get("w")) : null);
    newKey.setKeyUsername(hm.containsKey("u") ? this.encrypt((String)hm.get("u")) : null);
    newKey.setKeyCategory(hm.containsKey("c") ? this.encrypt((String)hm.get("c")) : null);
    return newKey;
  }

  public Key deleteKey() {
    String title = c.readLine("Enter key title: ");
    Key key = new Key();
    key.setKeyTitle(this.encrypt(title));
    return key;
  }  

  public Key updateKey(List<String> params) {
    HashMap<String, String> hm = this.splitParams(params);
    Key newKey = new Key();
    newKey.setKeyId(hm.containsKey("i") ? Integer.parseInt((String)hm.get("i")) : -1);
    newKey.setKeyPassword(hm.containsKey("p") ? this.encrypt((String)hm.get("p")) : null);
    newKey.setKeyTitle(hm.containsKey("t") ? this.encrypt((String)hm.get("t")) : null);
    newKey.setKeyUrl(hm.containsKey("w") ? this.encrypt((String)hm.get("w")) : null);
    newKey.setKeyUsername(hm.containsKey("u") ? this.encrypt((String)hm.get("u")) : null);
    newKey.setKeyCategory(hm.containsKey("c") ? this.encrypt((String)hm.get("c")) : null);
    return newKey;
  }  

  public List<Key> importKeys(List<String> params) {
    List<Key> keys = new ArrayList<Key>();
    HashMap<String, String> hm = this.splitParams(params);
    if(hm.containsKey("f")) {
      String csvFile = (String)hm.get("f");
      Boolean firstRow = false;
      if(hm.containsKey("r")) {
        String r = (String)hm.get("r");
        if(r.equalsIgnoreCase("true")) {
          firstRow = true;
        }
      }                    
      ReadCSV csv = new ReadCSV();
      List<String[]> rows = csv.run(csvFile);
      int idx = 0;
      int[] indexArray = new int[5];
      indexArray[0] = 1; //category
      indexArray[1] = 2; //title
      indexArray[2] = 3; //username
      indexArray[3] = 4; //password
      indexArray[4] = 5; //url
      
      if(firstRow == true) {            
        idx = 1;
        String[] row = rows.get(0);
        int j = 0;
        while(j < row.length) {
          String cell = row[j];
          if(cell.equalsIgnoreCase("category")) {
            indexArray[0] = j;
          }
          if(cell.equalsIgnoreCase("title")) {
            indexArray[1] = j;
          }
          if(cell.equalsIgnoreCase("username")) {
            indexArray[2] = j;
          }
          if(cell.equalsIgnoreCase("password")) {
            indexArray[3] = j;
          }
          if(cell.equalsIgnoreCase("url")) {
            indexArray[4] = j;
          }
          j++;
        }
      }
      
      for( int i = idx; i < rows.size(); i++) {
        Key newKey = new Key();
        String[] row = rows.get(i);
        int j = 0;
        while(j < row.length) {            
          /*System.out.println((String)row[indexArray[0]]);
          System.out.println((String)row[indexArray[1]]);
          System.out.println((String)row[indexArray[2]]);
          System.out.println((String)row[indexArray[3]]);
          System.out.println((String)row[indexArray[4]]);*/
          byte[] emptyArray = new byte[0];
          try {
            newKey.setKeyCategory(this.encrypt((String)row[indexArray[0]]));
          } catch (ArrayIndexOutOfBoundsException ex) {
            newKey.setKeyCategory(emptyArray);
          }
          try {
            newKey.setKeyTitle(this.encrypt((String)row[indexArray[1]]));
          } catch (ArrayIndexOutOfBoundsException ex) {
            newKey.setKeyTitle(emptyArray);
          }
          try {
            newKey.setKeyUsername(this.encrypt((String)row[indexArray[2]]));
          } catch (ArrayIndexOutOfBoundsException ex) {
            newKey.setKeyUsername(emptyArray);
          }
          try {
            newKey.setKeyPassword(this.encrypt((String)row[indexArray[3]]));
          } catch (ArrayIndexOutOfBoundsException ex) {
            newKey.setKeyPassword(emptyArray);
          }
          try {
            newKey.setKeyUrl(this.encrypt((String)row[indexArray[4]]));
          } catch (ArrayIndexOutOfBoundsException ex) {
            newKey.setKeyUrl(emptyArray);
          }                                  
          j++;
        }    
        keys.add(newKey);
      }
    }
    return keys; 
  }
  
  public void exportKeys(List<String> params, List<Key> keys) {    
    HashMap<String, String> hm = this.splitParams(params);
    if(hm.containsKey("f")) {
      try {
        File file = new File((String)hm.get("f"));
        if (!file.exists()) {
          file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);        
        ConsoleStringTable table = new ConsoleStringTable();    
        table.addString(0, 0, "ID");
        table.addString(0, 1, "Category");
        table.addString(0, 2, "Title");
        table.addString(0, 3, "Username");
        table.addString(0, 4, "Password");
        table.addString(0, 5, "URL");
        for(int i=0; i < keys.size(); i++) {
          Key key = keys.get(i);
          List<String>  columns = new ArrayList<String>();
          columns.add("" + key.getKeyId());
          columns.add(key.getKeyCategory() != null ? this.decrypt(key.getKeyCategory()) : "");            
          columns.add(key.getKeyTitle() != null ? this.decrypt(key.getKeyTitle()) : "");
          columns.add(key.getKeyUsername() != null ? this.decrypt(key.getKeyUsername()) : "");
          //show password for testing only
          columns.add(key.getKeyPassword() != null ? this.decrypt(key.getKeyPassword()) : "");          
          columns.add(key.getKeyUrl() != null ? this.decrypt(key.getKeyUrl()) : "");
          for (int j=0; j < columns.size(); j++) {
            table.addString(i + 1, j, columns.get(j));
          }          
        }
        bw.write(table.toString());
        bw.close();
      } catch (IOException ex) {
        logger.error(ex.toString());         
      }
    }
  }
  
  public List<String> listKeys(List<String> params) {
	  HashMap<String, String> hm = this.splitParams(params);
	  ArrayList<String> orderBy = new ArrayList<String>();
	  if (hm.containsKey("o")) {
		  String v = hm.get("o");
		  String[] kv = v.split(Constants.COMMA);
		  for (int i = 0; i < kv.length; i++) {
			  if (kv[i].equalsIgnoreCase("i")) {
				  orderBy.add("id");
			  }
			  if (kv[i].equalsIgnoreCase("t")) {
				  orderBy.add("title");
			  }
			  if (kv[i].equalsIgnoreCase("c")) {
				  orderBy.add("category");
			  }
			  if (kv[i].equalsIgnoreCase("u")) {
				  orderBy.add("username");
			  }
			  if (kv[i].equalsIgnoreCase("p")) {
				  orderBy.add("password");
			  }
			  if (kv[i].equalsIgnoreCase("w")) {
				  orderBy.add("url");
			  }
		  }
	  }
	  return orderBy; 
  }
}
