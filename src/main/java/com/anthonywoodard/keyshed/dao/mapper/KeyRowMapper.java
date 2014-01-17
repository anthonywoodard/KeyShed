package com.anthonywoodard.keyshed.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Anthony Woodard
 */
public class KeyRowMapper implements RowMapper {
  
  public Object mapRow(ResultSet rs, int line) throws SQLException {
    KeyResultSetExtractor extractor = new KeyResultSetExtractor();
    return extractor.extractData(rs);
  }
}
