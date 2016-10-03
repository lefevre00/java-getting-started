package org.friends.app.dao;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.friends.app.util.DateUtil;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(value = { "classpath:context/backend-test.xml" })
@Ignore
public class DbTest {
	protected static final LocalDate timePoint = DateUtil.now();
	protected static final String strToday = DateUtil.dateToString(timePoint);
	protected static final String strTomorrow = DateUtil.dateToString(timePoint.plusDays(1));
	protected static final String strAfterTomorrow = DateUtil.dateToString(timePoint.plusDays(2));
	protected static final String strYesterday = DateUtil.dateToString(timePoint.minusDays(1));

	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void runSql(String sql) {
		jdbcTemplate.execute(sql);
	}
}
