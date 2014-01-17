package com.anthonywoodard.keyshed.controller;

import com.anthonywoodard.keyshed.domain.User;
import com.anthonywoodard.keyshed.service.UserService;
import com.anthonywoodard.keyshed.view.UserView;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Anthony Woodard
 */
public class UserController {    
    private UserView userView;
    private UserService userService;    
    
    public void setUserView(UserView userView) {
        this.userView = userView;
    }
    
    public void setUserService(UserService userService) {
      this.userService = userService;
    }       
    
    public String doCommand(String cmd) {
      if(cmd.equals("login")) {
        return this.logIn();
      }
      if(cmd.equals("register")) {
        return this.register();
      }
      if(cmd.equals("list")) {
        return this.listUsers();
      }
      if(cmd.equals("password")) {
        return this.changePassword();
      }
      if(cmd.equals("new")) {
        return this.newUser();
      }
      if(cmd.equals("del")) {
        return this.deleteUser();
      }
      if(cmd.equals("init")) {        
        return this.init();
      }
      return this.userView.doPrompt();
    }
    
    public String logIn() {
      if (this.userService.hasCurrentUser()) {
        this.userView.showStatusCode("You are already logged in");
        return userView.doPrompt();
      } else {
        User user = this.userService.logIn(this.userView.showLogIn());    
        if(user.getIsInError() == false) {        
          return userView.doPrompt();
        } else {
          userView.showStatusCode(user.getStatusCode());          
          return this.init();
        }
      }
    }
    
    public String register() {
      if (this.userService.canRegister()) {        
        User user = this.userView.showRegistrar(false);
        User newUser = this.userService.registerUser(user);
        if(newUser.getIsInError() == false) {
          this.userView.showStatusCode(newUser.getStatusCode());
          this.userView.echo("Now log in...");
          return this.logIn();
        } else {
          List<User> users = Arrays.asList(user);
          this.userView.showUsers(users);
          this.userView.showStatusCode(user.getStatusCode());      
          return this.userView.initOption(true);
        }
      } else {
        this.userView.showStatusCode("Cannot register when a user is already registered");
        if (this.userService.hasCurrentUser()) {
          return userView.doPrompt();
        } else {
          return this.userView.initOption(false);
        }
      }
    }        
    
    public String changePassword() {
      User user = this.userView.showUsername();
      user = userService.updateUser(this.userView.changePassword(user));        
      List<User> users = Arrays.asList(user);
      this.userView.showUsers(users);
      this.userView.showStatusCode(user.getStatusCode());      
      return userView.doPrompt();        
    }
    
    public String listUsers() {      
      this.userView.showUsers(userService.listUsers());
      return userView.doPrompt();
    }
    
    public String newUser() {      
      User user = this.userView.showRegistrar(true);
      User newUser = this.userService.registerUser(user);
      if(newUser.getIsInError() == false) {
        this.userView.showStatusCode(newUser.getStatusCode());          
      } else {
        List<User> users = Arrays.asList(user);
        this.userView.showUsers(users);
        this.userView.showStatusCode(user.getStatusCode());                
      }      
      return userView.doPrompt();
    }
    
    public String deleteUser() {      
      User user = this.userView.deleteUser();
      user = this.userService.deleteUser(user);
      if(user.getIsInError() == false) {
        this.userView.showStatusCode(user.getStatusCode());        
        this.userView.showUsers(userService.getUsers());
      } else {
        this.userView.showStatusCode(user.getStatusCode());  
      }      
      return userView.doPrompt();
    }
    
    public String init() {                  
      return this.userView.initOption(this.userService.canRegister());
    }
}
