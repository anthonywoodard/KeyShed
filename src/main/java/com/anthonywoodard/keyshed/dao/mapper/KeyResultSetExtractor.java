package com.anthonywoodard.keyshed.dao.mapper;

import com.anthonywoodard.keyshed.domain.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Anthony Woodard
 */
public class KeyResultSetExtractor implements ResultSetExtractor {
  public Object extractData(ResultSet rs) throws SQLException {
    Key key = new Key();
    key.setKeyId(rs.getInt("keyId"));            
    key.setKeyTitle(rs.getBytes("keyTitle"));
    key.setKeyUsername(rs.getBytes("keyUsername"));
    key.setKeyPassword(rs.getBytes("keyPassword"));
    key.setKeyUrl(rs.getBytes("keyUrl"));
    key.setKeyCategory(rs.getBytes("keyCategory"));
	  return key;
	}
}
