package com.anthonywoodard.keyshed.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int line) throws SQLException {
		UserResultSetExtractor extractor = new UserResultSetExtractor();
		return extractor.extractData(rs);
	}

}  