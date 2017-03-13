package springbook.sql;

/**
 * Created by gesap on 2017-03-06.
 */
public class DefaultSqlServcie extends BaseSqlService {
    public DefaultSqlServcie() {
        setSqlReader(new JaxbXmlSqlReader());
        setSqlRegistry(new HashMapSqlRegistry());
    }
}
