import org.junit.After;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import springbook.EmbeddedDbSqlRegistry;
import springbook.SqlUpdateFailureException;
import springbook.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by gesap on 2017-03-07.
 */
public class EmbeddedDbSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {
    EmbeddedDatabase db;
    @Override
    protected UpdatableSqlRegistry createUpdatableSqlRegistry() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("classpath:schema.sql")
                .build();

        EmbeddedDbSqlRegistry embeddedDbSqlRegistry = new EmbeddedDbSqlRegistry();
        embeddedDbSqlRegistry.setDataSource(db);

        return embeddedDbSqlRegistry;
    }

    @Test
    public void transactionUpdate(){
        checkFindResult("SQL1", "SQL2", "SQL3");
        Map<String, String> sqlmap= new HashMap<>();
        sqlmap.put("KEY1","Modified1");
        sqlmap.put("KEY2323122", "Modifiedd33");

        try{
            sqlRegistry.updateSql(sqlmap);
            fail();
        }catch (SqlUpdateFailureException e){}

        checkFindResult("SQL1", "SQL2", "SQL3");



    }

    @After
    public void tearDown(){
        db.shutdown();
    }
}
