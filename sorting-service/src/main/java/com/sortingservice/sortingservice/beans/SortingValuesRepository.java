package com.sortingservice.sortingservice.beans;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SortingValuesRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class SortingValuesMapper implements RowMapper<SortedValuesBean>{

		@Override
		public SortedValuesBean mapRow(ResultSet rs, int rowNum) throws SQLException {
			SortedValuesBean bean = new SortedValuesBean();
			bean.setId(rs.getInt("id"));
			bean.setOriginalstring(rs.getString("originalstring"));
			bean.setSortedstring(rs.getString("sortedstring"));
			bean.setCreateddate(rs.getDate("createddate"));
			return bean;
		}
		
	}
	
	public int insertSortedData(SortedValuesBean sortedValues) {
		return jdbcTemplate.update("insert into sortedvalues(originalstring, sortedstring, createddate) values(?,?,?)",
				new Object[] {sortedValues.getOriginalstring(), sortedValues.getSortedstring(), sortedValues.getCreateddate()});
	}
	
	public SortedValuesBean getLastSortedValues() {
		return jdbcTemplate.queryForObject("select top 1 * from sortedvalues order by createddate desc ", new SortingValuesMapper());
	}
}
