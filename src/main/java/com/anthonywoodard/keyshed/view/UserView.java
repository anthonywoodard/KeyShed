package com.anthonywoodard.keyshed.view;

import com.anthonywoodard.keyshed.domain.User;
import java.util.List;

/**
 *
 * @author Anthony Woodard
 */
public interface UserView extends View {
  public User showLogIn();
  public User showRegistrar(Boolean addUser);
  public User changePassword(User user);
  public User deleteUser();
  public void showUsers(List<User> users);  
  public User showUsername();
}
