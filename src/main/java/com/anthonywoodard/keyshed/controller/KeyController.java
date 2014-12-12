package com.anthonywoodard.keyshed.controller;

import com.anthonywoodard.keyshed.domain.Key;
import com.anthonywoodard.keyshed.service.KeyService;
import com.anthonywoodard.keyshed.util.Constants;
import com.anthonywoodard.keyshed.view.KeyView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Anthony Woodard
 */
public class KeyController {
  
  final Logger logger = LoggerFactory.getLogger(KeyController.class);
  private KeyView keyView;
  private KeyService keyService;
  private UserController userController;
  
  public void setKeyView(KeyView keyView) {
      this.keyView = keyView;
  }

  public void setKeyService(KeyService keyService) {
      this.keyService = keyService;
  } 
  
  public void setUserController(UserController userController) {
    this.userController = userController;
  }
  
  public void init() {
    keyView.showSplash();
    keyView.showHelp();
    this.doCommand(userController.init());        
  }       
  
  private void doCommand(String cmdStr) {
    //Find commands in Terminal.java showHelp method
    String[] cmds = cmdStr.split(Constants.PARAMETER_PREFIX);
    String command = cmds[0].trim().toLowerCase(); //first node should be command         

    if(command.equals("user")) {                  
      String parameter = "";      
      try {
        parameter = cmds[1].trim().toLowerCase(); //second node should be parameter  
      } catch (ArrayIndexOutOfBoundsException e) {}
      this.doCommand(userController.doCommand(parameter));
    } else if(command.equals("help")) {
      this.doCommand(showHelp());
    } else if(!command.equals("quit") && !command.equals("exit")) {       
      ArrayList<String> params = new ArrayList<String>();      
      try {
        for(int i=1; i < cmds.length; i++) {
          params.add(cmds[i]);
        }
      } catch (ArrayIndexOutOfBoundsException e) {}           
      if(command.equals("new")) {
        this.doCommand(newKey(params));
      } else if(command.equals("list")) {
        this.doCommand(listKey("table", params));
      } else if(command.equals("llist")) {
        this.doCommand(listKey("long", params));
      } else if(command.equals("find")) {
        this.doCommand(findKey(params));
      } else if(command.equals("del")) {
        this.doCommand(deleteKey(params));
      } else if(command.equals("update")) {
        this.doCommand(updateKey(params));
      } else if(command.equals("import")) {
        this.doCommand(importKeys(params));
      } else if(command.equals("export")) {
        this.doCommand(exportKeys(params));
      } else if(command.equals("copy")) {
        this.doCommand(copyKey(params));
      } else {
        keyView.showStatusCode(Constants.INVALID_COMMAND);
        this.doCommand(keyView.doPrompt());
      } 
    } else if(command.equals("quit") || command.equals("exit")) { 
      keyView.quit();
    }               
  }
  
  public String newKey(List<String> params) {
    Key key = keyService.createKey(keyView.newKey(params));
    keyView.showStatusCode(key.getStatusCode());           
    return keyView.doPrompt();
  }
  
  public String listKey(String layout, List<String> params) {
    List<Key> keys = keyService.listKeys(keyView.listKeys(params));
    this.keyView.showKeys(keys, layout);
    return keyView.doPrompt();
  }
  
  public String findKey(List<String> params) {
    List<Key> keys = keyService.findKey(keyView.findKey(params));
    this.keyView.showKeys(keys, "long");
    return keyView.doPrompt();
  }
  
  public String copyKey(List<String> params) {
    Key key = keyService.copyKey(keyView.findKey(params));
    keyView.showStatusCode(key.getStatusCode());          
    return keyView.doPrompt();
  }
  
  public String deleteKey(List<String> params) {
    Key key = keyService.deleteKey(keyView.deleteKey());
    keyView.showStatusCode(key.getStatusCode());          
    return keyView.doPrompt();
  }
  
  public String updateKey(List<String> params) {
    Key key = keyService.updateKey(keyView.updateKey(params));
    keyView.showStatusCode(key.getStatusCode());          
    return keyView.doPrompt();
  }
  
  public String importKeys(List<String> params) {
    HashMap<String, List<Key>> hm = keyService.importKeys(keyView.importKeys(params));        
    if (hm.containsKey("bad")) {      
      keyView.showStatusCode(hm.get("bad").size() + " Bad Keys:");
      this.keyView.showKeys(hm.get("bad"), "table");    
    } else {
      keyView.showStatusCode(hm.get("good").size() + " Good Keys:");
      //this.keyView.showKeys(hm.get("good"));
      keyView.showStatusCode("Import Complete.");
    }    
    return keyView.doPrompt();
  }
  
  public String exportKeys(List<String> params) {
    List<Key> keys = keyService.getKeys();
    this.keyView.exportKeys(params, keys);
    return keyView.doPrompt();
  }
  
  public String showHelp() {
    keyView.showHelp();
    return keyView.doPrompt();
  }
}
