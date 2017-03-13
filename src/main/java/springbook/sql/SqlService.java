package springbook.sql;

import springbook.exception.SqlRetrievalFailureException;

/**
 * Created by gesap on 2017-03-02.
 */
public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
