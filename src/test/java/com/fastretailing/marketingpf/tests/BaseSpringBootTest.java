package com.fastretailing.marketingpf.tests;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.tests.BaseSpringBootTest.MyActiveProfilesResolver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ActiveProfilesResolver;

/**
 * Extend this class if test needs Spring Application
 */
@SpringBootTest
@ActiveProfiles(resolver = MyActiveProfilesResolver.class)
public abstract class BaseSpringBootTest {

    @Autowired
    public Config config;

    // Make test is default profile when no profile is specified
    public static class MyActiveProfilesResolver implements ActiveProfilesResolver {

        @Override
        public String[] resolve(Class<?> testClass) {
            final String activeProfile = System.getProperty("spring.profiles.active");
            if (activeProfile == null) {
                return new String[] { "test" };
            }
            return new String[] { activeProfile };
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public QueryResultAssert assertQueryResult(String query) {
        return new QueryResultAssert(jdbcTemplate.query(query, new ToCommaSeparateStringRowMapper()));
    }

    public static class ToCommaSeparateStringRowMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<String> row = new ArrayList<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));
            }
            return row.stream().collect(Collectors.joining(","));
        }
    }
}
