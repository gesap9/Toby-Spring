import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.sql.SqlType;
import springbook.sql.Sqlmap;

import org.springframework.oxm.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by gesap on 2017-03-06.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OxmTest {
    @Autowired
    Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws XmlMappingException, IOException{
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);
        List<SqlType> sqlList = sqlmap.getSql();
        assertThat(sqlList.size(),is(6));
    }
}
