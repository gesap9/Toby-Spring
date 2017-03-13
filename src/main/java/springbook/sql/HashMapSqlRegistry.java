package springbook.sql;

import springbook.exception.SqlNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gesap on 2017-03-06.
 */
public class HashMapSqlRegistry implements SqlRegistry {
    private Map<String, String> sqlMap = new HashMap<>();

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlNotFoundException(key + "를 이용해서 SQL을 찾을 수 없습니다.");
        } else {
            return sql;
        }
    }
}
