import javafx.scene.chart.PieChart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by gesap on 2017-01-19.
 */
public class UserDaoTest {
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    private ApplicationContext context;

    @Before
    public void setUp() {
        dao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/testdb",
                "com",
                "com01",
                true

        );
        dao.setDataSource(dataSource);

        user1 = new User("park", "박성철", "park123");
        user2 = new User("lee", "이노옴", "lee123");
        user3 = new User("kim", "김하가", "kim123");
    }

    @Test
    public void count() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));

    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(user2.getName(), is(user2.getName()));
        assertThat(user2.getPassword(), is(user2.getPassword()));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown id");
    }


}

