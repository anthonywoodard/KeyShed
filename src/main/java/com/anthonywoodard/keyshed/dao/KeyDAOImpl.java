package com.anthonywoodard.keyshed.dao;

import com.anthonywoodard.keyshed.dao.mapper.KeyRowMapper;
import com.anthonywoodard.keyshed.domain.Key;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Anthony Woodard
 */
public class KeyDAOImpl implements KeyDAO {
  private DataSource dataSource;

  @Inject
  public void setDataSource(DataSource ds) {
    dataSource = ds;
  }
  
  public void create(Key key) {
    JdbcTemplate insert = new JdbcTemplate(dataSource);
    insert.update("INSERT INTO tbl_key (keyTitle, keyUsername, keyPassword, keyUrl, keyCategory) VALUES(?,?,?,?,?)",
      new Object[] { key.getKeyTitle(), key.getKeyUsername(), key.getKeyPassword(), key.getKeyUrl(), key.getKeyCategory() });
  }

  public void update(Key key) {
    JdbcTemplate edit = new JdbcTemplate(dataSource);    
    edit.update("UPDATE tbl_key SET keyTitle = ?, keyUsername = ?, keyPassword = ?, keyUrl = ?, keyCategory = ? WHERE keyId = ?",
      new Object[] { key.getKeyTitle(), key.getKeyUsername(), key.getKeyPassword(), key.getKeyUrl(), key.getKeyCategory(), key.getKeyId() });   
  }

  public List<Key> select(byte[] title) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("select * from tbl_key where keyTitle = ?",
          new Object[] { title },
          new KeyRowMapper());
  }
  
  public List<Key> find(int limit, int offset) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("select * from tbl_key limit ? offset ?",
          new Object[] { limit, offset },
          new KeyRowMapper());
  }
  
  public int count() {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    int total =  select.queryForInt("select count(*) from tbl_key");
    return total;
  }

  public List<Key> select(int id) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("select * from tbl_key where keyId = ?",
          new Object[] { id },
          new KeyRowMapper());
  }

  public List<Key> select() {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select.query("select * from tbl_key ORDER BY keyCategory, keyTitle",
    //return select.query("select * from tbl_key ORDER BY keyId",
      new KeyRowMapper());
  }

  public void delete(int id) {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    delete.update("DELETE from tbl_key where keyId = ?",
      new Object[] { id });
  }
  
  public void delete(byte[] title) {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    delete.update("DELETE from tbl_key where keyTitle = ?",
      new Object[] { title });
  }
  
  public void delete() {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    delete.update("DELETE from tbl_key");
  }
}
