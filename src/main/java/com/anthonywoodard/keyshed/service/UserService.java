package com.anthonywoodard.keyshed.service;

import com.anthonywoodard.keyshed.dao.UserDAO;
import com.anthonywoodard.keyshed.domain.User;
import com.anthonywoodard.keyshed.util.Constants;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anthony Woodard
 */
public class UserService {
    
    private UserDAO userDao;
    
    private User currentUser;
    
    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }
    
    private void setCurrentUser(User user) {
      this.currentUser = user;
    }
    
    public Boolean hasCurrentUser() {
      if(this.currentUser != null){
        return true;
      } else {
        return false;
      }
    }
    
    public User logIn(User user) {
      User validUser = this.validateUser(user);
      if(validUser.getIsInError() == false) {
        this.setCurrentUser(validUser);
      }
      return validUser;      
    }
    
    public User validateUser(User user) {
        List<User> users = userDao.validate(user.getUsername(), user.getUserPassword());
        if(users.size() == 1) {
          user = users.get(0);
          user.setIsInError(Constants.NOT_IN_ERROR);
          user.setStatusCode(Constants.SUCCESS);          
        } else {
          user.setIsInError(Constants.IS_IN_ERROR);
          user.setStatusCode(Constants.INVALID_USER);          
        }        
        return user;
    }
    
    public Boolean canRegister() {
      List<User> allUsers = this.getUsers();
      if (allUsers.isEmpty()) {
        return true;
      } else {
        return false;
      }
    }
    
    public User registerUser(User user) {
      if(this.currentUser == null || this.currentUser.isPrimaryUser()) {
        boolean addUser = false;
        List<User> users = userDao.select();
        if(users.isEmpty()) {
          user.setPrimaryUser(1);
          addUser = true;        
        } else {
          boolean found = false;
          for(int i=0; i < users.size(); i++) {
            if(Arrays.equals(users.get(i).getUsername(), user.getUsername())) {          
              found = true;
            }
          }
          if(!found) {
            addUser = true;
          } else {
            user.setIsInError(Constants.IS_IN_ERROR);
            user.setStatusCode(Constants.USER_EXISTS);        
          }
        }      
        if(addUser) {
          userDao.create(user);
          users = userDao.select(user.getUsername());
          user = users.get(0);
          user.setIsInError(Constants.NOT_IN_ERROR);
          user.setStatusCode(Constants.SUCCESS);        
        }
      }      
      return user;
    }
    
    public User updateUser(User user) {
      if(this.currentUser.isPrimaryUser() || user.getUsername().equals(this.currentUser.getUsername()) ) {
        userDao.update(user);
        List<User> users = userDao.select(user.getUsername());
        if(!users.isEmpty()) {
          user = users.get(0);
          user.setIsInError(Constants.NOT_IN_ERROR);
          user.setStatusCode(Constants.SUCCESS);
        } else {
          user.setIsInError(Constants.IS_IN_ERROR);
          user.setStatusCode(Constants.INVALID_USER);
        }
      } else {
        user.setIsInError(Constants.IS_IN_ERROR);
        user.setStatusCode(Constants.INVALID_PERMISSIONS);
      }
      return user; 
    }
    
    public User deleteUser(User user) {
      if(this.currentUser.isPrimaryUser() && (!Arrays.equals(this.currentUser.getUsername(), user.getUsername()))) {
        List<User> users = userDao.select();      
        if(users.size() > 1) {
          boolean found = false;
          for(int i=0; i < users.size(); i++) {   
            User u = users.get(i);
            if(Arrays.equals(u.getUsername(), user.getUsername())) {                        
              found = true;              
            }
          }
          if(found == true) {
            userDao.delete(user.getUsername());
            user.setIsInError(Constants.NOT_IN_ERROR);
            user.setStatusCode(Constants.SUCCESS); 
          } else {
            user.setIsInError(Constants.IS_IN_ERROR);
            user.setStatusCode(Constants.INVALID_USER);
          }
        } else {
          user.setIsInError(Constants.IS_IN_ERROR);
          user.setStatusCode(Constants.FAILURE);
        }
      } else {
        user.setIsInError(Constants.IS_IN_ERROR);
        user.setStatusCode(Constants.INVALID_PERMISSIONS);
      }
      return user;
    }
    
    public List<User> listUsers() {
      if(this.currentUser.isPrimaryUser()) {
        return this.getUsers();        
      } else {
        return this.getUsers(this.currentUser.getUsername());        
      }      
    }
    
    public List<User> getUsers() {
      return userDao.select();
    }
    
    public List<User> getPrimaryUsers() {
      return userDao.selectPrimaryUsers();
    }
    
    public List<User> getUsers(byte[] username) {
      return userDao.select(username);
    }
    
}
