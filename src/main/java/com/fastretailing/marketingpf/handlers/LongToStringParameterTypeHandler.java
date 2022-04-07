package com.fastretailing.marketingpf.handlers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LongTypeHandler;

public class LongToStringParameterTypeHandler extends LongTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, '"' + Long.toString(parameter) + '"');
    }
}
