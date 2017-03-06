package springbook;

/**
 * Created by gesap on 2017-03-03.
 */
public interface SqlRegistry {
    void registerSql(String key, String sql);
    String findSql(String key) throws SqlNotFoundException;
}
