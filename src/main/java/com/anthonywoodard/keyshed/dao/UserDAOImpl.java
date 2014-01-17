package com.anthonywoodard.keyshed.dao;

import com.anthonywoodard.keyshed.dao.mapper.UserRowMapper;
import com.anthonywoodard.keyshed.domain.User;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Anthony Woodard
 */

public class UserDAOImpl implements UserDAO {
		
  private DataSource dataSource;

  @Inject
  public void setDataSource(DataSource ds) {
    dataSource = ds;
  }
	  
  public void createTable(){
    /*JdbcTemplate create = new JdbcTemplate(dataSource);
    create.update("DROP TABLE IF EXISTS tbl_user");
    create.update("CREATE TABLE IF NOT EXISTS tbl_user (userId INTEGER PRIMARY KEY, "                
        + "username TEXT, "
        + "password TEXT)");*/
  }

  public void create(User user) {
    JdbcTemplate insert = new JdbcTemplate(dataSource);
    insert.update("INSERT INTO tbl_user (username, userPassword, primaryUser) VALUES(?,?,?)",
      new Object[] { user.getUsername(), user.getUserPassword(), user.getPrimaryUser() });
  }
  
  public void update(User user) {
    JdbcTemplate edit = new JdbcTemplate(dataSource);    
    edit.update("UPDATE tbl_user SET userPassword = ? WHERE username = ?",
      new Object[] { user.getUserPassword(), user.getUsername() });   
  }

  public List<User> validate(byte[] username, byte[] password) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("SELECT * FROM tbl_user where username = ? AND userPassword = ?",
          new Object[] { username, password },
          new UserRowMapper());
  }

  public List<User> select(byte[] username) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("select * from tbl_user where username = ?",
          new Object[] { username },
          new UserRowMapper());
  }
  
  public List<User> select(int id) {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select
      .query("select * from tbl_user where userId = ?",
          new Object[] { id },
          new UserRowMapper());
  }

  public List<User> select() {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select.query("select * from tbl_user",
      new UserRowMapper());
  }

  public List<User> selectPrimaryUsers() {
    JdbcTemplate select = new JdbcTemplate(dataSource);
    return select.query("select * from tbl_user WHERE primaryUser = 1",
      new UserRowMapper());
  }
  
  public void delete() {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    delete.update("DELETE from tbl_user");
  }

  public void delete(byte[] username) {
    JdbcTemplate delete = new JdbcTemplate(dataSource);
    delete.update("DELETE from tbl_user where username = ?",
      new Object[] { username });
  }

}