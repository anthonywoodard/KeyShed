package com.anthonywoodard.keyshed.service;

import com.anthonywoodard.keyshed.dao.KeyDAO;
import com.anthonywoodard.keyshed.domain.Key;
import com.anthonywoodard.keyshed.terminal.Terminal;
import com.anthonywoodard.keyshed.util.Constants;
import com.anthonywoodard.keyshed.util.EncUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anthony Woodard
 */
public class KeyService {
  final Logger logger = LoggerFactory.getLogger(Terminal.class);
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
      List<Key> keys = new ArrayList();
      if((key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        keys = keyDao.select(key.getKeyTitle());
      } else if(key.getKeyId() > -1) {
        keys = keyDao.select(key.getKeyId());
      }
      return keys;
    }
    
    public List<Key> findKey(Key key) {
      List<Key> keys = new ArrayList();
      if((key.getKeyTitle() != null && key.getKeyTitle().length > 0)) {
        keys = this.findPattern(this.decrypt(key.getKeyTitle()), "title");
      } else if((key.getKeyCategory() != null && key.getKeyCategory().length > 0)) {
        keys = this.findPattern(this.decrypt(key.getKeyCategory()), "category");
      } else if(key.getKeyId() > -1) {
        keys = keyDao.select(key.getKeyId());
      }
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
        this.copyToClipboard(this.decrypt(key.getKeyPassword()));
      }
      return key;            
    }
    
    public Key deleteKey(Key key) {
      List<Key> keys = new ArrayList();
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
      List<Key> goodKeys = new ArrayList();
      List<Key> badKeys = new ArrayList();      
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
          if(!key.getStatusCode().equals(Constants.KEY_EXISTS)) {
            this.updateKey(this.getKey(key).get(0));
          }
        }
      }
      return hm;
    }
        
    private String decrypt(byte[] ctext) {
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
    
    private List<Key> findPattern(String pattern, String dataParam) {
      List<Key> keys = new ArrayList();
      int totalRows = keyDao.count();        
      int limit = 10;
      int offset = 0;              
      while (offset < totalRows) {
        List<Key> searchKeys = keyDao.find(limit, offset);
        for(int i=0; i < searchKeys.size(); i++) {
          Key k = searchKeys.get(i);
          String data = "";
          if (dataParam.equals("title")) {
            data = this.decrypt(k.getKeyTitle()).toLowerCase();
          } else if (dataParam.equals("category")) {
            data = this.decrypt(k.getKeyCategory()).toLowerCase();
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
