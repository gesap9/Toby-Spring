import springbook.ConcurrentHashMapSqlRegistry;
import springbook.UpdatableSqlRegistry;

/**
 * Created by gesap on 2017-03-07.
 */
public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        return new ConcurrentHashMapSqlRegistry();
    }
}
