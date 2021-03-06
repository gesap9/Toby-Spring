import org.junit.Test;
import springbook.sql.SqlType;
import springbook.sql.Sqlmap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.List;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by gesap on 2017-03-03.
 */
public class JaxbTest {
    @Test
    public void readSqlMap() throws JAXBException, IOException{
        String contextPath = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(contextPath);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(
                getClass().getResourceAsStream("sqlmap.xml")
        );
        List<SqlType> sqlList = sqlmap.getSql();
        assertThat(sqlList.size(),is(6));
    }
}
