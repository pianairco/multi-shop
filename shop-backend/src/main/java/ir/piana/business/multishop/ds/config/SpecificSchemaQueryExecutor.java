package ir.piana.business.multishop.ds.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SpecificSchemaQueryExecutor {
    private HikariDataSource ds;

    public SpecificSchemaQueryExecutor(HikariDataSource ds) {
        this.ds = ds;
    }

    public HikariDataSource getDatasource() {
        return ds;
    }

    public List<String> queryListOfString(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();

            return runner.query(conn, query, new ColumnListHandler<String>());
        } finally {
            ds.evictConnection(conn);
        }
    }

    public <T> List<T> queryList(String query, Class type) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BeanListHandler beanListHandler = new BeanListHandler<>(type);

            List list = (List<Object>)runner.query(conn, query, beanListHandler);
            return list;
        } finally {
            ds.evictConnection(conn);
        }
    }

    public List<Map<String, Object>> queryListOfMap(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            MapListHandler beanListHandler = new MapListHandler();

            return runner.query(conn, query, beanListHandler);
        } finally {
            ds.evictConnection(conn);
        }
    }

    public String queryString(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, query, new ScalarHandler<>());
        } finally {
            ds.evictConnection(conn);
        }
    }

    public int queryInt(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal value = runner.query(conn, query, new ScalarHandler<>());
            return value.intValue();
        } finally {
            ds.evictConnection(conn);
        }
    }

    public long queryLong(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal value = runner.query(conn, query, new ScalarHandler<>());
            return value.longValue();
        } finally {
            ds.evictConnection(conn);
        }
    }

    public double queryDouble(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal value = runner.query(conn, query, new ScalarHandler<>());
            return value.doubleValue();
        } finally {
            ds.evictConnection(conn);
        }
    }

    public float queryFloat(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal value = runner.query(conn, query, new ScalarHandler<>());
            return value.floatValue();
        } finally {
            ds.evictConnection(conn);
        }
    }

    public boolean queryBoolean(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            BigDecimal value = runner.query(conn, query, new ScalarHandler<>());
            return value.longValue() != 0;
        } finally {
            ds.evictConnection(conn);
        }
    }

    public boolean execute(String query) throws SQLException {
        Connection conn = ds.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            int execute = runner.execute(conn, query);
            return execute > 0;
        } finally {
            ds.evictConnection(conn);
        }
    }
}
