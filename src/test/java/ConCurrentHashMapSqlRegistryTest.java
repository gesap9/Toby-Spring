
/**
 * Created by gesap on 2017-03-06.
 */


import org.junit.Before;
import org.junit.Test;
import springbook.sql.ConcurrentHashMapSqlRegistry;
import springbook.exception.SqlNotFoundException;
import springbook.exception.SqlUpdateFailureException;
import springbook.sql.UpdatableSqlRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ConCurrentHashMapSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @Before
    public void setUp(){
        sqlRegistry = new ConcurrentHashMapSqlRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }




    @Test
    public void find(){
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    private void checkFindResult(String expected1, String expected2, String expected3){
        assertThat(sqlRegistry.findSql("KEY1"), is(expected1));
        assertThat(sqlRegistry.findSql("KEY2"), is(expected2));
        assertThat(sqlRegistry.findSql("KEY3"), is(expected3));

    }

    @Test(expected = SqlNotFoundException.class)
    public void unknownKey(){
        sqlRegistry.findSql("SQL99991!@#");
    }

    @Test
    public void updateSingle(){
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti(){
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");


    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithNotExistingKey(){
        sqlRegistry.updateSql("SQL99#$#","Modified2");
    }



}
