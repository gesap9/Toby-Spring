package springbook;

import java.util.Map;

/**
 * Created by gesap on 2017-03-06.
 */
public interface UpdatableSqlRegistry extends SqlRegistry {
    void updateSql(String key, String sql) throws SqlUpdateFailureException;
    void updateSql(Map<String, String> sqlmap) throws  SqlUpdateFailureException;

}
