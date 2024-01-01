package com.calcite;

import com.calcite.util.ResultSetUtil;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.avatica.util.Quoting;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.util.SourceStringReader;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlConformanceEnum;

import java.sql.*;
import java.util.Properties;

public class ObjectDemo {

    public static void main(String args[]) throws SQLException {
        ReflectiveSchema  reflectiveSchema=new ReflectiveSchema(new HRSchema());

        Properties info=new Properties();
        info.setProperty("caseSensitive","false");

        Connection connection= DriverManager.getConnection("jdbc:calcite:",info);

        CalciteConnection calciteConnection=connection.unwrap(CalciteConnection.class);

        SchemaPlus rootSchema=calciteConnection.getRootSchema();

        rootSchema.add("hr",reflectiveSchema);

        String sql="select * from hr.emps";
        Statement statement=calciteConnection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);

        System.out.println(ResultSetUtil.resultString(resultSet));
    }
}
