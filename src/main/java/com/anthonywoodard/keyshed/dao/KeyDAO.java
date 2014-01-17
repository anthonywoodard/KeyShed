package com.anthonywoodard.keyshed.dao;

import com.anthonywoodard.keyshed.domain.Key;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Anthony Woodard
 */
public interface KeyDAO {
  void setDataSource(DataSource ds);

  void create(Key key);
  
  void update(Key key);

  List<Key> select(byte[] title);

  List<Key> select(int id);
    
  List<Key> select();
  
  List<Key> find(int limit, int offset);
  
  int count();

  void delete();

  void delete(int id);
  
  void delete(byte[] title);
  
}
