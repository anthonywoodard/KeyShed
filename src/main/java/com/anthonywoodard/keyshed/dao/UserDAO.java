package com.anthonywoodard.keyshed.dao;

import com.anthonywoodard.keyshed.domain.User;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Anthony Woodard
 */
public interface UserDAO {
  
  void setDataSource(DataSource ds);

  void create(User user);
  
  void update(User user);

  List<User> validate(byte[] username, byte[] password);
  
  List<User> select(byte[] username);

  List<User> select(int id);
  
  List<User> selectPrimaryUsers();
  
  List<User> select();

  void delete();

  void delete(byte[] username);
  
}