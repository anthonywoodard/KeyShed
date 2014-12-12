package com.anthonywoodard.keyshed.service;

import com.anthonywoodard.keyshed.dao.KeyDAO;
import com.anthonywoodard.keyshed.domain.Key;
import com.anthonywoodard.keyshed.util.Constants;
import com.anthonywoodard.keyshed.util.EncUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anthony Woodard
 */
public class KeyService {
  final Logger logger = LoggerFactory.getLogger(KeyService.class);
  private KeyDAO keyDao;
    
    public void setKeyDao(KeyDAO keyDao) {
        this.keyDao = keyDao;
    }
    
    public Key createKey(Key key){
      if((key.getKeyUsername() != null && key.getKeyUsername().length > 0) && 
         (key.getKeyPassword() != null && key.getKeyPassword().length > 0) && 
         (key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        List<Key> keys = keyDao.select(key.getKeyTitle());
        if(keys.isEmpty()) {          
          keyDao.create(key);
          keys = keyDao.select(key.getKeyTitle());
          key = keys.get(0);
          key.setIsInError(Constants.NOT_IN_ERROR);
          key.setStatusCode(Constants.SUCCESS);        
        } else {
          key.setKeyId(keys.get(0).getKeyId());
          key.setIsInError(Constants.IS_IN_ERROR);
          key.setStatusCode(Constants.KEY_EXISTS);
        }
      } else {        
        key.setIsInError(Constants.IS_IN_ERROR);
        key.setStatusCode("Required Parameters: t, u, p");                
      }
      return key;
    }
    
    public List<Key> getKeys() {
      return keyDao.select();
    }
    
    public List<Key> getKey(Key key) {
      List<Key> keys = new ArrayList<Key>();
      if((key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        keys = keyDao.select(key.getKeyTitle());
      } else if(key.getKeyId() > -1) {
        keys = keyDao.select(key.getKeyId());
      }
      return keys;
    }
    
    public List<Key> listKeys(List<String> orderBy) {
    	List<Key> keys = new ArrayList<Key>();
    	keys = keyDao.select();
    	if (!orderBy.contains("id")) {    		    		
    		ComparatorChain<Key> chain = new ComparatorChain<Key>();    		
    		for (int i = 0; i < orderBy.size(); i++) {
    			if (orderBy.get(i).equalsIgnoreCase("title")) {
	    			chain.addComparator(Key.KeyTitleComparator);
	    		}
    			if (orderBy.get(i).equalsIgnoreCase("category")) {	    		
	    			chain.addComparator(Key.KeyCategoryComparator);
	    		}
    			if (orderBy.get(i).equalsIgnoreCase("username")) {	    		
	    			chain.addComparator(Key.KeyUsernameComparator);
	    		}
    			if (orderBy.get(i).equalsIgnoreCase("password")) {	    		
	    			chain.addComparator(Key.KeyPasswordComparator);
	    		}
    			if (orderBy.get(i).equalsIgnoreCase("url")) {	    		
	    			chain.addComparator(Key.KeyUrlComparator);
	    		}
    		}
    		if (chain.size() > 0) {
    			Collections.sort(keys, chain);
    		} else {
    			Collections.sort(keys, Key.KeyDefaultComparator);
    		}
    	}    	
    	return keys;    		
    }
    
    public List<Key> findKey(Key key) {
      List<Key> keys = new ArrayList<Key>();
      if((key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        keys = this.findPattern(EncUtil.decrypt(key.getKeyTitle()), "title");
      } else if((key.getKeyCategory() != null && key.getKeyCategory().length > 0)) {
        keys = this.findPattern(EncUtil.decrypt(key.getKeyCategory()), "category");
      } else if(key.getKeyId() > -1) {
        keys = keyDao.select(key.getKeyId());
      }
      Collections.sort(keys, Key.KeyDefaultComparator);
      return keys;
    }
    
    public Key copyKey(Key key) {
      List<Key> keys = this.findKey(key);
      if(keys.size() > 1) {
        key.setIsInError(Constants.IS_IN_ERROR);
        key.setStatusCode(Constants.INVALID_KEY);
      } else {
        key = keys.get(0);
        key.setIsInError(Constants.NOT_IN_ERROR);
        key.setStatusCode(Constants.SUCCESS);
        this.copyToClipboard(EncUtil.decrypt(key.getKeyPassword()));
      }
      return key;            
    }
    
    public Key deleteKey(Key key) {
      List<Key> keys = new ArrayList<Key>();
      if((key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        keys = keyDao.select(key.getKeyTitle());
      } else if(key.getKeyId() > -1) {
        keys = keyDao.select(key.getKeyId());
      }
      if(keys.size() > 1 || keys.isEmpty()) {
        key.setIsInError(Constants.IS_IN_ERROR);
        key.setStatusCode(Constants.FAILURE);
      } else {
        key = keys.get(0);
        keyDao.delete(key.getKeyId());
        key.setIsInError(Constants.NOT_IN_ERROR);
        key.setStatusCode(Constants.SUCCESS);
      }
      return key;            
    }
    
    public Key updateKey(Key key) {
      if(key.getKeyId() > -1) {
        List<Key> keys = keyDao.select(key.getKeyId());
        if(keys.isEmpty()) {          
          key.setIsInError(Constants.IS_IN_ERROR);
          key.setStatusCode(Constants.INVALID_KEY);        
        } else {
          Key selectedKey = keys.get(0);
          if(key.getKeyTitle() == null || key.getKeyTitle().length == 0) {
            key.setKeyTitle(selectedKey.getKeyTitle());
          }
          if(key.getKeyUsername() == null || key.getKeyUsername().length == 0) {
            key.setKeyUsername(selectedKey.getKeyUsername());
          }
          if(key.getKeyPassword() == null || key.getKeyPassword().length == 0) {
            key.setKeyPassword(selectedKey.getKeyPassword());
          }
          if(key.getKeyUrl() == null || key.getKeyUrl().length == 0) {
            key.setKeyUrl(selectedKey.getKeyUrl());
          }
          if(key.getKeyCategory() == null || key.getKeyCategory().length == 0) {
            key.setKeyCategory(selectedKey.getKeyCategory());
          }
          keyDao.update(key);          
          key.setIsInError(Constants.NOT_IN_ERROR);
          key.setStatusCode(Constants.SUCCESS);
        }
      } else {        
        key.setIsInError(Constants.IS_IN_ERROR);
        key.setStatusCode("\"Required Parameter: i\"");                
      }
      return key;
    }
    
    public HashMap<String, List<Key>> importKeys(List<Key> keys) { 
      List<Key> goodKeys = new ArrayList<Key>();
      List<Key> badKeys = new ArrayList<Key>();      
      for(int i=0; i < keys.size(); i++) {
        Key key = keys.get(i);
        if((key.getKeyUsername() != null && key.getKeyUsername().length > 0) && 
         (key.getKeyPassword() != null && key.getKeyPassword().length > 0) && 
         (key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
          goodKeys.add(key);
        } else {
          badKeys.add(key);
        }
      }
      HashMap<String, List<Key>> hm = new HashMap<String, List<Key>>();
      hm.put("good", goodKeys);
      if (badKeys.size() > 0) {
        hm.put("bad", badKeys);
      } else {
        for(int i=0; i < goodKeys.size(); i++) {           
          Key key = this.createKey(goodKeys.get(i));
          if(key.getStatusCode().equals(Constants.KEY_EXISTS)) {
            this.updateKey(key);
          }
        }
      }
      return hm;
    }
    
    private List<Key> findPattern(String pattern, String dataParam) {
      List<Key> keys = new ArrayList<Key>();
      int totalRows = keyDao.count();        
      int limit = 10;
      int offset = 0;              
      while (offset < totalRows) {
        List<Key> searchKeys = keyDao.find(limit, offset);
        for(int i=0; i < searchKeys.size(); i++) {
          Key k = searchKeys.get(i);
          String data = "";
          if (dataParam.equals("title")) {
            data = EncUtil.decrypt(k.getKeyTitle()).toLowerCase();
          } else if (dataParam.equals("category")) {
            data = EncUtil.decrypt(k.getKeyCategory()).toLowerCase();
          }
          if (data.contains(pattern.toLowerCase())) {
            keys.add(k);
          }
        }
        offset = offset + limit;
      }
      return keys;
    }
    
    private void copyToClipboard(String content) {
      StringSelection stringSelection = new StringSelection(content);
      Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
      clpbrd.setContents(stringSelection, null);
    }
}
