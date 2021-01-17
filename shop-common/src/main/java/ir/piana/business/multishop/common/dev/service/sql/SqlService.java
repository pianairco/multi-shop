package ir.piana.business.multishop.common.dev.service.sql;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SqlService {
//    @Autowired
    private JdbcTemplate jdbcTemplate;

    SqlProperties sqlProperties;

    @Autowired
    public SqlService(SqlProperties properties) {
        sqlProperties = properties;
    }

    public long insert(String group, String sequenceName, Object[] sqlParams) {
        Long id = jdbcTemplate.queryForObject("select " + sequenceName + ".nextval from dual", Long.class);
        jdbcTemplate.update(sqlProperties.getGroups().get(group).getInsert(), ArrayUtils.addAll(new Object[] {id}, sqlParams));
        return id == null ? 0 : id;
    }

    public long insertByQueryName(String queryName, String sequenceName, Object[] sqlParams) {
        Long id = jdbcTemplate.queryForObject("select " + sequenceName + ".nextval from dual", Long.class);
        jdbcTemplate.update(sqlProperties.getMap().get(queryName), ArrayUtils.addAll(new Object[] {id}, sqlParams));
        return id == null ? 0 : id;
    }

    public void update(String group, Object[] sqlParams) {
        jdbcTemplate.update(sqlProperties.getGroups().get(group).getUpdate(), sqlParams);
    }

    public void updateByQueryName(String queryName, Object[] sqlParams) {
        jdbcTemplate.update(sqlProperties.getMap().get(queryName), sqlParams);
    }

    public List<Map<String, Object>> list(String group, Object[] sqlParams) {
        return jdbcTemplate.queryForList(sqlProperties.getGroups().get(group).getSelect(), sqlParams);
    }

    public List<Map<String, Object>> listByName(String queryName, Object[] sqlParams) {
        return jdbcTemplate.queryForList(sqlProperties.getMap().get(queryName), sqlParams);
    }

    public Map<String, Object> select(String queryName, Object[] sqlParams) {
        try {
            return jdbcTemplate.queryForMap(sqlProperties.getMap().get(queryName), sqlParams);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Long selectLong(String query, Object[] sqlParams) {
        try {
            return jdbcTemplate.queryForObject(query, sqlParams, Long.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void delete(String group, Object[] sqlParams) {
        jdbcTemplate.update(sqlProperties.getGroups().get(group).getDelete(), sqlParams);
    }

    public void deleteByQueryName(String queryName, Object[] sqlParams) {
        jdbcTemplate.update(sqlProperties.getMap().get(queryName), sqlParams);
    }
}
