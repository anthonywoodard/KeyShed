package com.anthonywoodard.keyshed.terminal;

import com.anthonywoodard.keyshed.domain.User;
import com.anthonywoodard.keyshed.view.UserView;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anthony Woodard
 */
public class UserTerminal extends Terminal implements UserView {
  
  private TextDevice  c;
      
  public UserTerminal() {    
    c = this.getConsole();
  }    
  
  public User showLogIn() {
    String username = c.readLine("Enter your username: ");
    char [] password = c.readPassword("Enter your password: ");
    String pword = new String(password);
    User user = new User();
    user.setUsername(this.encrypt(username));
    user.setUserPassword(this.encrypt(pword));
    return user;        
  }
  
  public User showRegistrar(Boolean addUser){
    String username = c.readLine("Enter username: ");
    String password = "";
    boolean noMatch;
    do {
        char [] newPassword1 = c.readPassword("Enter password: ");
        char [] newPassword2 = c.readPassword("Enter password again: ");
        noMatch = ! Arrays.equals(newPassword1, newPassword2);
        if (noMatch) {
            c.format("Passwords don't match. Try again.\n");
        } else {
            password = new String(newPassword1);
        }
        Arrays.fill(newPassword1, ' ');
        Arrays.fill(newPassword2, ' ');
    } while (noMatch);        
    User user = new User();
    user.setUsername(this.encrypt(username));
    user.setUserPassword(this.encrypt(password));    
    if(addUser) {
      String isPrimary = c.readLine("Primary User? (Y or N) ");
      if  (isPrimary.equalsIgnoreCase("y")) {
        user.setPrimaryUser(1);
      } else {
        user.setPrimaryUser(0);
      }
    }
    return user;
  }
  
  public User changePassword(User user) {
    boolean noMatch;
    char [] newPassword1;        
    do {
        newPassword1 = c.readPassword("Enter new password: ");
        char [] newPassword2 = c.readPassword("Enter new password again: ");
        noMatch = ! Arrays.equals(newPassword1, newPassword2);
        if (noMatch) {
            c.format("Passwords don't match. Try again.\n");
        } else {
            user.setUserPassword(this.encrypt(new String(newPassword1)));
        }
        Arrays.fill(newPassword1, ' ');
        Arrays.fill(newPassword2, ' ');
    } while (noMatch);
    return user;
  }
  
  public void showUsers(List<User> users) {    
    for(int i=0; i < users.size(); i++) {      
      c.format("User Id: " + users.get(i).getUserId() + "%n");          
      c.format("Username: " + this.decrypt(users.get(i).getUsername()) + "%n");      
      //show password for testing only
      c.format("Password: " + this.decrypt(users.get(i).getUserPassword()) + "%n");          
      c.format("Primary User: " + users.get(i).getPrimaryUser() + "\n\n");
    }
  }
  
  public User deleteUser() {
    String username = c.readLine("Enter username: ");
    User user = new User();
    user.setUsername(this.encrypt(username));
    return user;
  }

  public User showUsername() {
    String username = c.readLine("Enter username: ");
    User user = new User();
    user.setUsername(this.encrypt(username));    
    return user; 
  }
      
}
