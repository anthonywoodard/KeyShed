package com.anthonywoodard.keyshed.dao.mapper;

import com.anthonywoodard.keyshed.domain.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author Anthony Woodard
 */

public class UserResultSetExtractor implements ResultSetExtractor {
	
	public Object extractData(ResultSet rs) throws SQLException {
    User user = new User();
    user.setUserId(rs.getInt("userId"));            
    user.setUsername(rs.getBytes("username"));
    user.setUserPassword(rs.getBytes("userPassword"));
    user.setPrimaryUser(rs.getInt("primaryUser"));
	  return user;
	}
}
